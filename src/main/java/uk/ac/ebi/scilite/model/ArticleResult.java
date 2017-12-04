package uk.ac.ebi.scilite.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationApi;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationItemApi;

@ApiModel(description="It holds the annotations corresponding to a specific article")
@JsonInclude(Include.NON_NULL)
@XmlType(propOrder = {"source", "extId", "pmcid", "annotationsCount","annotations"})
@JsonPropertyOrder({"source", "extId", "pmcid", "annotationsCount","annotations"})
public class ArticleResult {
	
	@ApiModelProperty(value="Source of the article", allowableValues="MED,PMC,PAT,AGR,CBA,HIR,CTX,ETH,CIT,PPR,NBK")
	private String source;
	
	@ApiModelProperty(value="Identifier of the article connected with the domain specified by the source field (i.e. if SOURCE=MED it will be the PubMed ID or if SOURCE=PMC it will be a number representing the pmcid)")
	private String extId;
	
	@ApiModelProperty(value="pmcid of the article if available")
	private Long pmcid;
	
	//@ApiModelProperty(value="Number of annotations contained in the response for this article")
	//private int annotationsCount;

	@ApiModelProperty(value="Annotations contained in this article")
	@JacksonXmlElementWrapper(localName = "annotations")
	@JacksonXmlProperty(localName="annotation")
	private List<Annotation> annotations;
	
	public ArticleResult(AnnotationApi articleData, OutputFormat format){
		List<Annotation> annotationsData = null;
		
		//List<AnnotationItemApi> filteredAnnotations = AnnotationsLambda.filterAnnotations(AnnotationsLambda.filterAnnotationsByLegalCriteria(articleData), articleData.getAnns());
		List<AnnotationItemApi> filteredAnnotations = articleData.getAnns();
		if (format == OutputFormat.JSON || format == OutputFormat.XML){ 
			annotationsData = filteredAnnotations.stream().map(annotation -> new Annotation(annotation)).collect(Collectors.toList());
		}
		
		this.setAnnotations(annotationsData);
		this.setSource(articleData.getSrc());
		this.setExtId(articleData.getExt_id());
		this.setPmcid(articleData.getPmcid());
	}
	
	public ArticleResult(AnnotationApi abstractData){
		this(abstractData, OutputFormat.JSON);
	}
	
	public ArticleResult(String source, String extId, Long pmcid){
		this.setAnnotations(new ArrayList<Annotation>());
		this.setSource(source);
		this.setExtId(extId);
		this.setPmcid(pmcid);
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getExtId() {
		return extId;
	}

	public void setExtId(String extId) {
		this.extId = extId;
	}

	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;	
	}

	public Long getPmcid() {
		return pmcid;
	}

	public void setPmcid(Long pmcid) {
		this.pmcid = pmcid;
	}

}
