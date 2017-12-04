package uk.ac.ebi.scilite.model.jsonLD;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationApi;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationItemApi;
import uk.ac.ebi.scilite.model.AnnotationProvider;

/**"@context": "http://europepmc.org/europepmc-annotation-api-vocab.json", # context file
"id": "http://rdf.ebi.ac.uk/resource/europepmc/annotations/MED/21494379#1-1", # ann
"type": "Annotation",
"creator": "europepmc", # provider*/

@JsonInclude(Include.NON_NULL)
@ApiModel(description="It holds the annotation data in JSON-LD format. To see details about JSON-LD go to http://europepmc.org/AnnotationsApi#jsonLD", discriminator="creator", subTypes={AnnotationJsonLDNamedEntity.class, AnnotationJsonLDSentenceRelationship.class, AnnotationJsonLDSentenceNoRelationship.class})
/**@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "creator")
@JsonSubTypes({
    @Type(value = AnnotationJsonLDNamedEntity.class, name = "Europe PMC"),
})*/
@JacksonXmlRootElement(localName = "annotation")
public class AnnotationJsonLD implements Serializable{
	
	@ApiModelProperty(value="JSON-LD context file")
	@JsonProperty(value="@context")
	private String context;
	
	@ApiModelProperty(value="Link back to the specific sentence of the article that contains the annotation")
	private String id;
	
	@ApiModelProperty(value="Type of the resource")
	private String type;
	
	@ApiModelProperty(value="Provider of the annotation")
	private String creator;
	

	public AnnotationJsonLD (AnnotationItemApi input){
		this.setContext("http://europepmc.org/docs/europepmc-annotation-api-vocab.json");
		this.setType("Annotation");
		this.setId(input.getAnn_link());
		
		AnnotationProvider provider = AnnotationProvider.getProviderByDbValue(input.getProvider());
		this.setCreator(provider.getDescription());
	}


	public String getContext() {
		return context;
	}


	public void setContext(String context) {
		this.context = context;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getCreator() {
		return creator;
	}


	public void setCreator(String creator) {
		this.creator = creator;
	}


	public static String getUrlPage(AnnotationItemApi input, AnnotationApi articleData) {
		String url="http://europepmc.org/";
		if (articleData.getPmcid()!=null && (input.isAbstractSection()==false)){
			url+="articles/PMC"+articleData.getPmcid();
		}else{
			if ("NBK".equalsIgnoreCase(articleData.getSrc())){
				url+="books/"+articleData.getExt_id();
			}else if ("DOI".equalsIgnoreCase(articleData.getSrc())){
				url+="abstract/MED/"+articleData.getExt_id();
			}else if ("ETH".equalsIgnoreCase(articleData.getSrc())){
				url+="theses/ETH/"+articleData.getExt_id();
			}else if ("PAT".equalsIgnoreCase(articleData.getSrc())){
				url+="patents/PAT/"+articleData.getExt_id();
			}else if ("HIR".equalsIgnoreCase(articleData.getSrc())){
				url+="guidelines/HIR/"+articleData.getExt_id();
			}else {
				url+="abstract/"+articleData.getSrc().toUpperCase()+"/"+articleData.getExt_id();
			}
		}
		
		return url;
	}
	
	public static AnnotationJsonLD getAnnotationJsonLD (AnnotationItemApi input, AnnotationApi articleData){
		AnnotationJsonLD ret;
		AnnotationProvider provider = AnnotationProvider.getProviderByDbValue(input.getProvider());
		
		if (AnnotationJsonLDNamedEntity.class.equals(provider.getJsonLdClass())){
			ret = new AnnotationJsonLDNamedEntity(input, articleData);
		}else if (AnnotationJsonLDSentenceRelationship.class.equals(provider.getJsonLdClass())){
			ret = new AnnotationJsonLDSentenceRelationship(input, articleData);
		}else{
			ret = new AnnotationJsonLDSentenceNoRelationship(input, articleData);
		}
		
		return ret;
	}

}
