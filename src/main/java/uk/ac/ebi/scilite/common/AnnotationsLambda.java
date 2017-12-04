package uk.ac.ebi.scilite.common;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.mongojack.DBQuery;
import org.mongojack.DBQuery.Query;

import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationApi;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationItemApi;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationKeywordApi;
import uk.ac.ebi.scilite.model.ArticleResult;
import uk.ac.ebi.scilite.model.OutputFormat;
import uk.ac.ebi.scilite.model.jsonLD.AnnotationJsonLD;

public class AnnotationsLambda {
	
	private static Predicate<AnnotationItemApi> filterAnnotationsByTypeAndSectionAndProvider(String type, String section, String provider){
		return annotation -> {
			boolean ret=true;
			if (Utility.isNotEmpty(type)){
				ret= ret && type.equalsIgnoreCase(annotation.getType());
			}
			
			if (Utility.isNotEmpty(section)){
				ret= ret && section.equalsIgnoreCase(annotation.getSection());
			}
			
			if (Utility.isNotEmpty(provider)){
				ret= ret && provider.equalsIgnoreCase(annotation.getProvider());
			}
			
			return ret;
		};
	}
	
	private static Predicate<AnnotationItemApi> filterAnnotationsByEntity(String entity) {
		return annotation -> annotation.getTags().stream().anyMatch(tag -> entity.equalsIgnoreCase(tag.getName()));
	}
	
	private static Predicate<AnnotationItemApi> filterAnnotationsByRelationship(List<String> entities) {
		return  annotation ->  entities.stream().map(entity -> annotation.getTags().stream().anyMatch(tag -> entity.equalsIgnoreCase(tag.getName()))).reduce(true, (a, b) -> a && b);
	}
	
	public static Predicate<AnnotationItemApi> filterAnnotationsByLegalCriteria(AnnotationApi articleData){
		return annotation -> {
			return AnnotationApi.isAnnotationAccountable(annotation, articleData);
			
		};
	}
	
	public static List<AnnotationItemApi> filterAnnotations(Predicate<AnnotationItemApi> filter, List<AnnotationItemApi> inputData){
		return inputData.stream().filter(filter).collect(Collectors.<AnnotationItemApi>toList());
	}
	
	
	public static Function<String,Query> mapArticleIdToQuery(){
		return artId ->{
			Query ret;
			String[] info = artId.split(":");
			String src = info[0].toUpperCase();
			String extId = info[1];
			
			if ("PMC".equalsIgnoreCase(src)){
				ret = DBQuery.is("pmcid", Long.parseLong(extId.replaceAll("PMC", "")));
			}else{
				ret = DBQuery.and(DBQuery.is("src", src), DBQuery.is("ext_id", extId));
			}
			return ret;
			
		};
	}
	
	public static Function<AnnotationApi,ArticleResult> transformAnnotationsFilteringByTypeAndSectionAndProvider(String type, String section, String provider, OutputFormat format){
		return articleData -> {
			List<AnnotationItemApi> filteredAnnotationsByTypeAndProvider = filterAnnotations(filterAnnotationsByTypeAndSectionAndProvider(type, section ,provider), articleData.getAnns());  
			articleData.setAnns(filteredAnnotationsByTypeAndProvider);
			return new ArticleResult(articleData, format);
		};
	}
	
	public static Function<AnnotationApi,List<AnnotationJsonLD>> transformAnnotationsJsonLDFilteringByTypeAndSectionAndProvider(String type, String section, String provider, OutputFormat format){
		return articleData -> {
			List<AnnotationItemApi> filteredAnnotationsByTypeAndProvider = filterAnnotations(filterAnnotationsByTypeAndSectionAndProvider(type, section, provider), articleData.getAnns());  
			return filteredAnnotationsByTypeAndProvider.stream().map(annotation -> AnnotationJsonLD.getAnnotationJsonLD(annotation, articleData)).collect(Collectors.<AnnotationJsonLD>toList());
		};
	}
	
	public static Function<AnnotationApi,ArticleResult> transformAnnotationsFilteringByEntity(String entity, OutputFormat format){
		return articleData -> {
			List<AnnotationItemApi> filteredAnnotationsByEntity = filterAnnotations(filterAnnotationsByEntity(entity), articleData.getAnns());  
			articleData.setAnns(filteredAnnotationsByEntity);
			return new ArticleResult(articleData, format);
		};
	}
	
	public static Function<AnnotationApi,List<AnnotationJsonLD>> transformAnnotationsJsonLDFilteringByEntity(String entity, OutputFormat format){
		return articleData -> {
			List<AnnotationItemApi> filteredAnnotationsByEntity = filterAnnotations(filterAnnotationsByEntity(entity), articleData.getAnns());  
			return filteredAnnotationsByEntity.stream().map(annotation -> AnnotationJsonLD.getAnnotationJsonLD(annotation, articleData)).collect(Collectors.<AnnotationJsonLD>toList());
		};
	}
	
	
	public static Function<AnnotationApi,ArticleResult> transformAnnotationsFilteringByRelationship(List<String> entities, OutputFormat format) {
		return articleData -> {
			List<AnnotationItemApi> filteredAnnotationsByRelationship = filterAnnotations(filterAnnotationsByRelationship(entities), articleData.getAnns());  
			articleData.setAnns(filteredAnnotationsByRelationship);
			return new ArticleResult(articleData, format);
		};
	}
	
	public static Function<AnnotationApi,List<AnnotationJsonLD>> transformAnnotationsJsonLDFilteringByRelationship(List<String> entities, OutputFormat format) {
		return articleData -> {
			List<AnnotationItemApi> filteredAnnotationsByRelationship = filterAnnotations(filterAnnotationsByRelationship(entities), articleData.getAnns());  
			return filteredAnnotationsByRelationship.stream().map(annotation -> AnnotationJsonLD.getAnnotationJsonLD(annotation, articleData)).collect(Collectors.<AnnotationJsonLD>toList());
		};
	}

	public static Function<AnnotationApi,ArticleResult> transformAnnotations(OutputFormat format) {
		return articleData -> {
			return new ArticleResult(articleData, format);
		};
	}
	
	public static Function<AnnotationApi,List<AnnotationJsonLD>> transformJsonLDAnnotations(OutputFormat format) {
		return articleData -> {
			return articleData.getAnns().stream().map(annotation -> AnnotationJsonLD.getAnnotationJsonLD(annotation, articleData)).collect(Collectors.<AnnotationJsonLD>toList());
		};
	}
	
	public static AnnotationKeywordApi findKeyword(AnnotationApi annotationData, String kwName, String kwVal){
		AnnotationKeywordApi ret = null;
		List<AnnotationKeywordApi> filteredKeywords = annotationData.getKw().stream().filter(filterKeywordsByNameAndVal(kwName, kwVal)).collect(Collectors.<AnnotationKeywordApi>toList());
		if (filteredKeywords.size()==1){
			ret = filteredKeywords.get(0);
		}
		
		return ret;
	}
	
	private static Predicate<AnnotationKeywordApi> filterKeywordsByNameAndVal(String kwName, String kwVal){
		return keyword -> kwVal.equalsIgnoreCase(keyword.getVal()) && kwName.equalsIgnoreCase(keyword.getNm());
	}

	public static Predicate<AnnotationItemApi> filterAnnotationsEuropePMC(boolean abstractOnly, String provider) {
		return annotation -> {
			boolean ret = provider.equalsIgnoreCase(annotation.getProvider());
			if (abstractOnly){
				ret= ret && annotation.isAbstractSection();
			}
			return ret;
		};
	}
	
	public static Predicate<AnnotationItemApi> filterAnnotationsEuropePMCLinkBack(boolean abstractOnly, String provider, String annotationLink) {
		return annotation -> {
			boolean ret = provider.equalsIgnoreCase(annotation.getProvider());
			ret= ret && annotationLink.equalsIgnoreCase(annotation.getAnn_link());
			if (abstractOnly){
				ret= ret && annotation.isAbstractSection();
			}
			return ret;
		};
	}

	
}
