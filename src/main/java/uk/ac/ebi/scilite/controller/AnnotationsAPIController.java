package uk.ac.ebi.scilite.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.mongojack.DBQuery;
import org.mongojack.DBQuery.Query;
import org.mongojack.DBSort;
import org.mongojack.DBSort.SortBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import uk.ac.ebi.literature.mongodb.dao.ICrudDAO;
import uk.ac.ebi.literature.mongodb.model.annotations.AnnotationMultiple;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationApi;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationItemApi;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationKeywordApi;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationKeywordApi.KW_TYPE;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationProviderApi;
import uk.ac.ebi.literature.mongodb.util.Results;
import uk.ac.ebi.scilite.common.AnnotationsLambda;
import uk.ac.ebi.scilite.common.Utility;
import uk.ac.ebi.scilite.model.AnnotationProvider;
import uk.ac.ebi.scilite.model.AnnotationSection;
import uk.ac.ebi.scilite.model.AnnotationType;
import uk.ac.ebi.scilite.model.ArticleResult;
import uk.ac.ebi.scilite.model.OutputFormat;
import uk.ac.ebi.scilite.model.Result;
import uk.ac.ebi.scilite.model.ResultArticleIdDoc;
import uk.ac.ebi.scilite.model.ResultDoc;
import uk.ac.ebi.scilite.model.europepmc.ResultEuropePMC;
import uk.ac.ebi.scilite.model.jsonLD.AnnotationJsonLD;

@RestController
@Api(value="Europe PMC Annotations API", description="Europe PMC Annotations API")
public class AnnotationsAPIController {
	
	private final static String DEFAULT_PAGE_SIZE="4";
	
	private final static String DEFAULT_FILTER="1";
	
	private final static String DEFAULT_FORMAT="JSON";
	
	private final static String DEFAULT_CURSOR_MARK="0.0";
	
	private final static String DEFAULT_FILTER_PARAM_VALUE="UNSPECIFIED";
	
	private final static Logger logger = LogManager.getLogger(AnnotationsAPIController.class);
	
	@Autowired
	private ICrudDAO dao;
	
	@Autowired
	private int maxPageSize;
	
	@Autowired
	private String collectionName;
	
	@Autowired
	private String versionNumber;
	
	@RequestMapping(value = "/annotationsByArticleIds", method = RequestMethod.GET)
	@ApiOperation(value = "Get the annotations contained in the list of articles specified", /**produces="application/json, application/xml",*/ response=ResultArticleIdDoc.class)
	/**@ApiResponses({
		@ApiResponse(code=200, responseContainer="List", message = "Annotations Articles retrieved succesfully", reference="Ciao")
		<ul><li>MED: PubMed MEDLINE (NLM)</li></ul>
	})*/
	public List<Object> getAnnotationsArticlesByIds(@ApiParam(value="Article Id lists contain between 1 and 8 values. Each value must follow the format SOURCE:EXTERNAL_ID. Allowed values for the field SOURCE are <i>MED</i>: PubMed MEDLINE, <i>PMC</i>: PubMedCentral not in (PubMed),"+
	                                                                " <i>PAT</i>: Patents, <i>AGR</i>: Agricola (USDA/NAL), <i>CBA</i>: Chinese biological abstracts, <i>HIR</i>: NHS Evidence (UK HIR), <i>CTX</i>: CiteXplore submission, <i>ETH</i>: EThOS theses (BL), <i>CIT</i>: CiteSeer (PSU), <i>PPR</i>: Preprints, <i>NBK</i>: NLM Books (not in PubMed)."+
	                                 	                            " EXTERNAL_ID must be the relevant identifier for the specified source (i.e. if SOURCE=MED it must be the PubMed ID or if SOURCE=PMC it must be a number representing the pmcid). Possible examples are MED:14670548 or PMC:19992", required=true) @RequestParam(value="articleIds") List<String> artIds, 
			                                     @ApiParam(value="Type of the annotations that the user is interested in. If this parameter is specified only annotations of the specified type will be retrieved", allowableValues="Gene_Proteins,Organisms,Chemicals, Gene Ontology,Diseases,Accession Numbers,Gene Function,Gene Disease,Protein Interaction,Biological Event" ,required=false) @RequestParam(value="type", defaultValue=DEFAULT_FILTER_PARAM_VALUE) AnnotationType type, 
			                                     @ApiParam(value="Section of the annotations that the user is interested in. If this parameter is specified only annotations belonging to the specified article section will be retrieved", allowableValues="Title,Abstract,Introduction,Methods,Results,Discussion,Acknowledgments,References,Article,Table,Figure,Case study,Supplementary material,Conclusion,Abbreviations,Competing Interests,Author Contributions", required=false) @RequestParam(value="section", defaultValue=DEFAULT_FILTER_PARAM_VALUE) AnnotationSection section,
			                                     @ApiParam(value="Provider of the annotations that the user is interested in. If this parameter is specified only annotations provided from the specified provider will be retrieved", allowableValues="Europe PMC,HES-SO_SIB,OpenTargets,NaCTeM,IntAct,DisGeNET", required=false) @RequestParam(value="provider", defaultValue=DEFAULT_FILTER_PARAM_VALUE) AnnotationProvider provider, 
			                                     @ApiParam(value="Output format of the response: <ul><li>JSON will produce a JSON representation of the articles and relative annotations</li><li>XML will produce a XML representation of the articles and relative annotations</li><li>JSON-LD will produce a JSON linked Data representation of the annotations. To see details about JSON-LD go to http://europepmc.org/AnnotationsApi#jsonLD</li></ul>", required=false, allowableValues="JSON, JSON-LD, XML")  @RequestParam(value="format", defaultValue=DEFAULT_FORMAT) OutputFormat format ) throws Exception {
		
		validateArticleIds(artIds, this.maxPageSize);
		
		List<Object> ret = new ArrayList<Object>();
		
		List<ArticleResult> articles=new ArrayList<ArticleResult>();
		
		List<AnnotationJsonLD> annotations= new ArrayList<AnnotationJsonLD>();
		
		List<Query> queries = artIds.stream().map(AnnotationsLambda.mapArticleIdToQuery()).collect(Collectors.<Query>toList());
		
		Query query = DBQuery.or(queries.toArray(new Query[0]));
		
		long start = System.currentTimeMillis();
		
		logger.info("Connected to "+dao.getDbUrl());
		
		try (Results<AnnotationApi> articlesData = dao.findEntries(collectionName, AnnotationApi.class, query);){
			
			if (format ==OutputFormat.JSON_LD){
				annotations = articlesData.stream().map(AnnotationsLambda.transformAnnotationsJsonLDFilteringByTypeAndSectionAndProvider(type.getDbValue(), section.getDbValue(), provider.getDbValue(), format)).reduce(new ArrayList<AnnotationJsonLD>(), (a,b) -> {a.addAll(b); return a;});
				ret.addAll(annotations);
			}else{
				articles = articlesData.stream().map(AnnotationsLambda.transformAnnotationsFilteringByTypeAndSectionAndProvider(type.getDbValue(), section.getDbValue(), provider.getDbValue(), format)).collect(Collectors.<ArticleResult>toList());
			    ret.addAll(articles);
			}
			
		}
		
		long end = System.currentTimeMillis();
		logger.info( "Retrieved data for articles (src1:ext_id1,src2:ext_id2,...)  ("+artIds+") in "+ (end - start)+ " ms");
		
		return ret;
	}
	
	@RequestMapping(value = "/annotationsByProvider", method = RequestMethod.GET)
	@ApiOperation(value = "Get the annotations of the articles which have at least one annotation provided by the specified provider", /**produces="application/json, application/xml",*/ response=ResultDoc.class)
	public Result getAnnotationsArticlesByProvider(	@ApiParam(value="Provider of the annotations that the user is interested in.", allowableValues="Europe PMC,HES-SO_SIB,OpenTargets,NaCTeM,IntAct,DisGeNET", required=true) @RequestParam(value="provider") AnnotationProvider provider, 
												   	@ApiParam(value="If the parameter is equal to 1, for each article only annotations of the specific provider will be retrieved. If the parameter is equal to 0, all the annotations will be retrieved for articles which also contain annotations of the specific provider. For example, if you search for annotations of the provider 'Europe PMC', you would get an overview of all annotations for each article, together with the annotations of the provider 'Europe PMC'", allowableValues="0,1", required=false) @RequestParam(value="filter", defaultValue=DEFAULT_FILTER) int filter, 
												   	@ApiParam(value="Output format of the response: <ul><li>JSON will produce a JSON representation of the articles and relative annotations</li><li>XML will produce a XML representation of the articles and relative annotations</li><li>JSON-LD will produce a JSON linked Data representation of the annotations. To see details about JSON-LD go to http://europepmc.org/AnnotationsApi#jsonLD</li><li>ID_LIST will produce a list of articles identifiers including pmcid if available</li></ul>", required=false, allowableValues="JSON, JSON-LD, XML,ID_LIST") @RequestParam(value="format", defaultValue=DEFAULT_FORMAT) OutputFormat format,
												   	@ApiParam(value="CursorMark for pagination of the result list. For the first request you can omit the parameter or use the default value 0.0. For every following page use the value of the returned nextCursorMark element", required=false) @RequestParam(value="cursorMark", defaultValue=DEFAULT_CURSOR_MARK) Double cursorMark, 
					                                @ApiParam(value="Number of articles the user wishes to retrieve in each page. The value must be between 1 and 8", allowableValues="range[1, 8]", required=false) @RequestParam(value="pageSize", defaultValue=DEFAULT_PAGE_SIZE) int pageSize) throws Exception {
			
		if (provider==AnnotationProvider.UNSPECIFIED){
			throw new AnnotationsAPIParameterException(AnnotationProvider.getValidationMessage());
		}
		
        Result ret = getAnnotationsByCriteria(KW_TYPE.ANN_PROVIDER, provider.getDbValue(), format, filter, pageSize, cursorMark);
		return ret;
	}
	
	@RequestMapping(value = "/annotationsBySectionAndOrType", method = RequestMethod.GET)
	@ApiOperation(value = "Get the annotations of the articles which have at least one annotation of a type (if specified) inside an article section (if specified). At least one value between section and type must be specified.", /**produces="application/json, application/xml",*/ response=ResultDoc.class)
	public Result getAnnotationsArticlesBySectionAndOrType(@ApiParam(value="Type of the annotations that the user is interested in", allowableValues="Gene_Proteins,Organisms,Chemicals, Gene Ontology,Diseases,Accession Numbers,Gene Function,Gene Disease,Protein Interaction,Biological Event" ) @RequestParam(value="type" , defaultValue=DEFAULT_FILTER_PARAM_VALUE) AnnotationType type, 
														@ApiParam(value="Section of the annotations that the user is interested in", allowableValues="Title,Abstract,Introduction,Methods,Results,Discussion,Acknowledgments,References,Article,Table,Figure,Case study,Supplementary material,Conclusion,Abbreviations,Competing Interests,Author Contributions") @RequestParam(value="section", defaultValue=DEFAULT_FILTER_PARAM_VALUE) AnnotationSection section, 
			                                            @ApiParam(value="If the parameter is equal to 1, for each article only annotations of the specified type belonging to the specified article section will be retrieved. If the parameter is equal to 0, all the annotations will be retrieved for articles which also contain annotations of the specified type belonging to the specified article section. For example, if you search for annotations of the type 'Gene_Proteins' belonging to the section 'Introduction', you would get an overview of all annotations for each article, together with the annotations of the type 'Gene_Proteins' belonging to the section 'Introduction'", allowableValues="0,1", required=false) @RequestParam(value="filter", defaultValue=DEFAULT_FILTER) int filter, 
			                                            @ApiParam(value="Output format of the response: <ul><li>JSON will produce a JSON representation of the articles and relative annotations</li><li>XML will produce a XML representation of the articles and relative annotations</li><li>JSON-LD will produce a JSON linked Data representation of the annotations. To see details about JSON-LD go to http://europepmc.org/AnnotationsApi#jsonLD</li><li>ID_LIST will produce a list of articles identifiers including pmcid if available</li></ul>", required=false, allowableValues="JSON, JSON-LD, XML,ID_LIST") @RequestParam(value="format", defaultValue=DEFAULT_FORMAT) OutputFormat format, 
			                                            @ApiParam(value="CursorMark for pagination of the result list. For the first request you can omit the parameter or use the default value 0.0. For every following page use the value of the returned nextCursorMark element", required=false) @RequestParam(value="cursorMark", defaultValue=DEFAULT_CURSOR_MARK) Double cursorMark, 
						                                @ApiParam(value="Number of articles the user wishes to retrieve in each page. The value must be between 1 and 8", allowableValues="range[1, 8]", required=false) @RequestParam(value="pageSize", defaultValue=DEFAULT_PAGE_SIZE) int pageSize) throws Exception {
		
		String exceptionMessage = "";
		boolean validInput=true;
	
		
		if (type==AnnotationType.UNSPECIFIED && section==AnnotationSection.UNSPECIFIED){
			exceptionMessage = "At least one value between section and type must be specified. " +AnnotationType.getValidationMessage()+" "+  AnnotationSection.getValidationMessage();
			validInput=false;
		}
		
		if (validInput==false){
			throw new AnnotationsAPIParameterException(exceptionMessage);
		}
		
		KW_TYPE criteria=null;
		String fieldValue="";
		
		if (type==AnnotationType.UNSPECIFIED){
			fieldValue = section.getDbValue();
			criteria= KW_TYPE.ANN_SECTION;
		}else if (section==AnnotationSection.UNSPECIFIED){
			fieldValue = type.getDbValue();
			criteria= KW_TYPE.ANN_TYPE;
		}else {
			fieldValue =AnnotationItemApi.getTypeSectionKwLabel(type.getDbValue(), section.getDbValue());
			criteria = KW_TYPE.ANN_SECTION_TYPE;
		}

		Result ret = getAnnotationsByCriteria(criteria, fieldValue, format, filter, pageSize, cursorMark);

		return ret;
	}
	
	@RequestMapping(value = "/annotationsByEntity", method = RequestMethod.GET)
	@ApiOperation(value = "Get the annotations of the articles which have at least one annotation tagging the specified entity", /**produces="application/json, application/xml",*/ response=ResultDoc.class)
	public Result getAnnotationsArticlesByEntity(@ApiParam(value="Entity that the user is interested in", example="cancer",required=true) @RequestParam(value="entity") String entity, 
												@ApiParam(value="If the parameter is equal to 1, for each article only annotations tagging the specified entity will be retrieved. If the parameter is equal to 0, all the annotations will be retrieved for articles which also contain the specific tagged entity. For example, if you search for annotations with the entity 'P53', you would get an overview of all annotations for each article, together with the annotations tagging 'P53'", allowableValues="0,1", required=false) @RequestParam(value="filter", defaultValue=DEFAULT_FILTER) int filter,  
												@ApiParam(value="Output format of the response: <ul><li>JSON will produce a JSON representation of the articles and relative annotations</li><li>XML will produce a XML representation of the articles and relative annotations</li><li>JSON-LD will produce a JSON linked Data representation of the annotations. To see details about JSON-LD go to http://europepmc.org/AnnotationsApi#jsonLD</li><li>ID_LIST will produce a list of articles identifiers including pmcid if available</li></ul>", required=false, allowableValues="JSON, JSON-LD, XML,ID_LIST") @RequestParam(value="format", defaultValue=DEFAULT_FORMAT) OutputFormat format,
												@ApiParam(value="CursorMark for pagination of the result list. For the first request you can omit the parameter or use the default value 0.0. For every following page use the value of the returned nextCursorMark element", required=false) @RequestParam(value="cursorMark", defaultValue=DEFAULT_CURSOR_MARK) Double cursorMark, 
				                                @ApiParam(value="Number of articles the user wishes to retrieve in each page. The value must be between 1 and 8", allowableValues="range[1, 8]", required=false) @RequestParam(value="pageSize", defaultValue=DEFAULT_PAGE_SIZE) int pageSize) throws Exception{
		
		if (Utility.isNotEmpty(entity)==false){
			throw new AnnotationsAPIParameterException("The entity parameter must have a value. ");
		}
		
		Result ret = getAnnotationsByCriteria(KW_TYPE.ANN_ENTITY, entity.toLowerCase(), format, filter, pageSize, cursorMark);

		return ret;
	}
	
	@RequestMapping(value = "/annotationsByRelationship", method = RequestMethod.GET)
	@ApiOperation(value = "Get the annotations of the articles which have at least one annotation tagging both the specified entities (i.e. Gene-Disease relationship)", /**produces="application/json, application/xml",*/ response=ResultDoc.class)
	public Result getAnnotationsArticlesByRelationship(@ApiParam(value="First entity that the user is interested in", required=true) @RequestParam(value="firstEntity") String firstEntity, 
														@ApiParam(value="Second entity that the user is interested in", required=true) @RequestParam(value="secondEntity") String secondEntity, 
			                                      		@ApiParam(value="If the parameter is equal to 1, for each article only annotations tagging both the specified entities will be retrieved. If the parameter is equal to 0, all the annotations will be retrieved for articles which also contain both specific tagged entities. For example, if you search for annotations with both entities 'cancer' and 'MMP-9', you would get an overview of all annotations for each article, together with the annotations tagging both 'cancer' and 'MMP-9'", allowableValues="0,1", required=false) @RequestParam(value="filter", defaultValue=DEFAULT_FILTER) int filter,  
			                                      		@ApiParam(value="Output format of the response: <ul><li>JSON will produce a JSON representation of the articles and relative annotations</li><li>XML will produce a XML representation of the articles and relative annotations</li><li>JSON-LD will produce a JSON linked Data representation of the annotations. To see details about JSON-LD go to http://europepmc.org/AnnotationsApi#jsonLD</li><li>ID_LIST will produce a list of articles identifiers including pmcid if available</li></ul>", required=false, allowableValues="JSON, JSON-LD, XML,ID_LIST") @RequestParam(value="format", defaultValue=DEFAULT_FORMAT) OutputFormat format, 
			                                      		@ApiParam(value="CursorMark for pagination of the result list. For the first request you can omit the parameter or use the default value 0.0. For every following page use the value of the returned nextCursorMark element", required=false) @RequestParam(value="cursorMark", defaultValue=DEFAULT_CURSOR_MARK) Double cursorMark, 
						                                @ApiParam(value="Number of articles the user wishes to retrieve in each page. The value must be between 1 and 8", allowableValues="range[1, 8]", required=false) @RequestParam(value="pageSize", defaultValue=DEFAULT_PAGE_SIZE) int pageSize) throws Exception{
		
		String exceptionMessage = "";
		boolean validInput=true;
		if (Utility.isNotEmpty(firstEntity)==false){
			exceptionMessage += "The firstEntity parameter must have a value. ";
			validInput=false;
		}
		
		if (Utility.isNotEmpty(secondEntity)==false){
			exceptionMessage += "The secondEntity parameter must have a value. ";
			validInput=false;
		}
		
		if (validInput==false){
			throw new AnnotationsAPIParameterException(exceptionMessage);
		}
		
		
		List<String> entities = new ArrayList<String>();
		entities.add(firstEntity);
		entities.add(secondEntity);
		String relationLabel = AnnotationApi.getRelationshipLabel(entities);
		Result ret = getAnnotationsByCriteria(KW_TYPE.ANN_RELATION, relationLabel, format, filter, pageSize, cursorMark);
		return ret;
	}
	
	
	
	@RequestMapping(value = "/healthcheck")
	public String healthcheck(@RequestParam(value="format", defaultValue="HTML") OutputFormat format) {
		
		long start = System.currentTimeMillis();
		
		String returnHealthcheck = "<b>Healthcheck status: OK</b>";
		
		boolean success=true;
		String errorMessage="";
		
		try{
			returnHealthcheck= returnHealthcheck+"<p>Connected to database "+dao.getConnectionInfo()+"</p>";
			returnHealthcheck= returnHealthcheck+ "<p>Version: " +this.versionNumber+"</p>";
			
			long countArticles = dao.countEntries(collectionName);
			
			if (countArticles>0){
				returnHealthcheck= returnHealthcheck+"<p>Number of Articles with annotations inside the collection "+this.collectionName+ " : "+countArticles+"</p>";
			}else{
				success=false;
				errorMessage="No Articles into the collection "+collectionName+" inside database "+dao.getConnectionInfo();
			}

		}catch(Exception e){
			errorMessage = e.getMessage();
			success=false;
		}
		

		if (success==false){
			returnHealthcheck ="<b>Healthcheck status: FAIL :"+errorMessage+"</b><br/><br/>";
		}
		
		long end = System.currentTimeMillis();
		
		returnHealthcheck+= "Completed in "+(end - start)+" ms";
		
		return returnHealthcheck;
	}
	
	private Result getAnnotationsByCriteria (KW_TYPE kwType, String value, OutputFormat format, int filter, int pageSize, Double cursorMark) throws Exception{
		
		validateFilterAndPageSize(filter, pageSize);
		
		Result ret=new Result(format);
		
		long start = System.currentTimeMillis();
		
		logger.info("Connected to "+dao.getDbUrl());
		
		Query query=null;
		
		boolean makeQuery=true;
		
		if (cursorMark > Double.valueOf(0.0)){
			query = DBQuery.elemMatch("kw", DBQuery.and(DBQuery.is("val",value), DBQuery.is("nm", kwType.name()), DBQuery.lessThan("uniqueCount", cursorMark)));
		}else if ( Double.valueOf(0.0).equals(cursorMark)){
			query = DBQuery.elemMatch("kw", DBQuery.and(DBQuery.is("val",value), DBQuery.is("nm", kwType.name())));
		}else{
			makeQuery=false;
		}
		
		List<ArticleResult> articlesList=new ArrayList<ArticleResult>();
		
		List<AnnotationJsonLD> annotationsList=new ArrayList<AnnotationJsonLD>();
		
		List<AnnotationApi> articlesData=new ArrayList<AnnotationApi>();
		
		if (makeQuery){
			SortBuilder sortCriteria =  DBSort.desc("kw.uniqueCount");
			
			List<String> fieldNames=null;
			
			if (format ==OutputFormat.ID_LIST){
				fieldNames=Arrays.asList("src","ext_id", "pmcid", "kw");
			}else{
				fieldNames=Arrays.asList("src","ext_id", "pmcid","license","oa","anns", "kw");
			}
			
			long startDb= System.currentTimeMillis();
			long endDb;
			
			try (Results<AnnotationApi> articlesDataResult = dao.findEntries(collectionName, AnnotationApi.class, fieldNames, query, sortCriteria, pageSize)){
				
				endDb = System.currentTimeMillis();
				logger.info("DB FECTH TIME "+(endDb - startDb));
				
				startDb= System.currentTimeMillis();
				
				articlesData= articlesDataResult.toList();
				
				endDb = System.currentTimeMillis();
				logger.info("CURSOR TIME "+(endDb - startDb));

				if ((filter==1) && (format !=OutputFormat.ID_LIST)){
					switch (kwType){
						case ANN_PROVIDER:
							if (format ==OutputFormat.JSON_LD){
								annotationsList = articlesData.stream().map(AnnotationsLambda.transformAnnotationsJsonLDFilteringByTypeAndSectionAndProvider(null, null, value, format)).reduce(new ArrayList<AnnotationJsonLD>(), (a,b) -> {a.addAll(b); return a;});
							}else{
								articlesList = articlesData.stream().map(AnnotationsLambda.transformAnnotationsFilteringByTypeAndSectionAndProvider(null, null, value, format)).collect(Collectors.<ArticleResult>toList());
							}
							break;
						case ANN_TYPE:
							if (format ==OutputFormat.JSON_LD){
								annotationsList = articlesData.stream().map(AnnotationsLambda.transformAnnotationsJsonLDFilteringByTypeAndSectionAndProvider(value, null, null, format)).reduce(new ArrayList<AnnotationJsonLD>(), (a,b) -> {a.addAll(b); return a;});
							}else{
								articlesList = articlesData.stream().map(AnnotationsLambda.transformAnnotationsFilteringByTypeAndSectionAndProvider(value, null, null, format)).collect(Collectors.<ArticleResult>toList());
							}
							break;
						case ANN_SECTION_TYPE:
							String type = AnnotationItemApi.getTypeFromTypeSectionKwLabel(value);
							String section = AnnotationItemApi.getSectionFromTypeSectionKwLabel(value);
							if (format ==OutputFormat.JSON_LD){
								annotationsList = articlesData.stream().map(AnnotationsLambda.transformAnnotationsJsonLDFilteringByTypeAndSectionAndProvider(type, section, null, format)).reduce(new ArrayList<AnnotationJsonLD>(), (a,b) -> {a.addAll(b); return a;});
							}else{
								articlesList = articlesData.stream().map(AnnotationsLambda.transformAnnotationsFilteringByTypeAndSectionAndProvider(type, section, null, format)).collect(Collectors.<ArticleResult>toList());
							}
							break;
						case ANN_ENTITY:
							if (format ==OutputFormat.JSON_LD){
								annotationsList = articlesData.stream().map(AnnotationsLambda.transformAnnotationsJsonLDFilteringByEntity(value, format)).reduce(new ArrayList<AnnotationJsonLD>(), (a,b) -> {a.addAll(b); return a;});
							}else{
								articlesList = articlesData.stream().map(AnnotationsLambda.transformAnnotationsFilteringByEntity(value, format)).collect(Collectors.<ArticleResult>toList());
							}
							break;
						case ANN_SECTION:
							if (format ==OutputFormat.JSON_LD){
								annotationsList = articlesData.stream().map(AnnotationsLambda.transformAnnotationsJsonLDFilteringByTypeAndSectionAndProvider(null, value, null, format)).reduce(new ArrayList<AnnotationJsonLD>(), (a,b) -> {a.addAll(b); return a;});
							}else{
								articlesList = articlesData.stream().map(AnnotationsLambda.transformAnnotationsFilteringByTypeAndSectionAndProvider(null, value, null, format)).collect(Collectors.<ArticleResult>toList());
							}
							break;
						case ANN_RELATION:
							List<String> entities = AnnotationApi.getEntitiesFromRelationshipLabel(value);
							
							if (format ==OutputFormat.JSON_LD){
								annotationsList = articlesData.stream().map(AnnotationsLambda.transformAnnotationsJsonLDFilteringByRelationship(entities, format)).reduce(new ArrayList<AnnotationJsonLD>(), (a,b) -> {a.addAll(b); return a;});
							}else{
								articlesList = articlesData.stream().map(AnnotationsLambda.transformAnnotationsFilteringByRelationship(entities, format)).collect(Collectors.<ArticleResult>toList());
							}
							break;
					}
					
				}else{
					
					if (format ==OutputFormat.JSON_LD){
						annotationsList = articlesData.stream().map(AnnotationsLambda.transformJsonLDAnnotations(format)).reduce(new ArrayList<AnnotationJsonLD>(), (a,b) -> {a.addAll(b); return a;});
					}else{
						articlesList = articlesData.stream().map(AnnotationsLambda.transformAnnotations(format)).collect(Collectors.<ArticleResult>toList());
					}
					
					
				}
			}
			
			
			if (format ==OutputFormat.JSON_LD){
				ret.setAnnotations(annotationsList);
			}else{
				ret.setArticles(articlesList);
			}
			
			ret.setNextCursorMark(getNextCursorMark(articlesData, pageSize, kwType.name(), value));
			ret.setCursorMark(cursorMark);
		}
		
		
		long end = System.currentTimeMillis();
		if (format==OutputFormat.JSON_LD){
			logger.info( "Retrieved data for the "+kwType.name()+" "+value+" ("+ret.getAnnotations().size()+" annotations) with format "+format.name()+", pageSize "+pageSize+", filter "+filter+", cursorMark "+cursorMark +" in "+ (end - start)+ " ms");
		}else{
			logger.info( "Retrieved data for the "+kwType.name()+" "+value+" ("+ret.getArticles().size()+" articles) with format "+format.name()+", pageSize "+pageSize+", filter "+filter+", cursorMark "+cursorMark +" in "+ (end - start)+ " ms");
		}
		

		return ret;
	}
	
	private void validateFilterAndPageSize(int filter, int pageSize) throws AnnotationsAPIParameterException {
		
		boolean validData=true;
		String errorMessage="";
		if (filter!=0 && filter!=1){
			errorMessage+="The filter parameter has to be either 0 or 1. ";
			validData=false;
		}
		
		if (pageSize<=0 || pageSize>this.maxPageSize){
			errorMessage+="The pageSize parameter must have a value between 1 and "+maxPageSize+". ";
			validData=false;
		}
		
		if (validData==false){
			throw new AnnotationsAPIParameterException(errorMessage); 
		}
	}

	private Double getNextCursorMark(List<AnnotationApi> results, int pageSize, String kwName, String kwVal){
		Double nextCursorMark = -1.0;
		
		if (results.size()==pageSize){
			Query query = DBQuery.and(DBQuery.is("src", results.get(pageSize - 1).getSrc()), DBQuery.is("ext_id", results.get(pageSize - 1).getExt_id()));
			AnnotationApi lastResult = dao.findEntry(collectionName, AnnotationApi.class, query);
			AnnotationKeywordApi kw = AnnotationsLambda.findKeyword(lastResult, kwName, kwVal);
			if (kw!=null){
				nextCursorMark = kw.getUniqueCount();
			}
		}
		
		return nextCursorMark;
	}
	
	private void validateArticleIds(List<String> articleIds, int maxNumber) throws AnnotationsAPIParameterException {
		
		boolean validData=true;
		String errorMessage="";
		if (Utility.isNotEmpty(articleIds)==false || articleIds.size()> maxNumber){
			errorMessage+="The articleIds list parameter must contain between 1 and "+maxNumber+" values. ";
			validData=false;
		}
		
		if (validData){
			String[] artIdInfo;
			String source, ext_id;
			boolean validArtIdsList=true;
			String problematicId="";
			for (String articleId : articleIds){
				artIdInfo= articleId.split(":");
				if (artIdInfo.length!=2){
					validArtIdsList=false;
					problematicId=articleId;
					break;
				}
				
				source = artIdInfo[0];
				
				final List<String> validSources = Arrays.asList("PMC", "MED", "PAT", "AGR", "CBA", "HIR", "CTX", "ETH", "CIT", "PPR", "NBK");
				
				if (validSources.contains(source.toUpperCase())==false){
					validArtIdsList=false;
					problematicId=articleId;
					break;
				}
				
				if ("PMC".equalsIgnoreCase(source.toUpperCase())){
					ext_id=source = artIdInfo[1];
					ext_id=ext_id.replaceAll("PMC", "");
					try{
						Long.parseLong(ext_id);
					}catch(NumberFormatException e){
						validArtIdsList=false;
						problematicId=articleId;
						break;
					}
				}
			}
			
			if (validArtIdsList==false){
				validData=false;
				errorMessage+="The articleIds list parameter contains one or more wrongly formatted values (i.e. "+problematicId+"). It must contain values with format SOURCE:EXTERNAL_ID where SOURCE must have one of the following values [PMC, MED, PAT, AGR, CBA, HIR, CTX, ETH, CIT, PPR, NBK] and EXTERNAL_ID must be a number when SOURCE=PMC.";
			}
		}
		
		if (validData==false){
			throw new AnnotationsAPIParameterException(errorMessage); 
		}
	}
	
	@InitBinder 
	public void initBinder(final WebDataBinder webdataBinder) { 
		webdataBinder.registerCustomEditor(OutputFormat.class, new OutputFormatConverter()); 
		webdataBinder.registerCustomEditor(AnnotationProvider.class, new AnnotationProviderConverter());
		webdataBinder.registerCustomEditor(AnnotationType.class, new AnnotationTypeConverter());
		webdataBinder.registerCustomEditor(AnnotationSection.class, new AnnotationSectionConverter());
	}
}
