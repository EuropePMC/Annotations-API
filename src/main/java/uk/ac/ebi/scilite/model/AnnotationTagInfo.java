package uk.ac.ebi.scilite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.literature.mongodb.model.annotations.AnnotationTag;

@ApiModel(description="It holds the data describing an external database resource tagged by an annotation")
@JsonIgnoreProperties(ignoreUnknown=true)
@org.codehaus.jackson.annotate.JsonIgnoreProperties(ignoreUnknown=true)
@JacksonXmlRootElement(localName = "tag")
public class AnnotationTagInfo implements java.io.Serializable {
	
	@ApiModelProperty(value="Name of the external database resource")
	private String name;
	
	@ApiModelProperty(value="URI of the external database resource")
	private String uri;
	
	public AnnotationTagInfo(AnnotationTag tag){
		this.setName(tag.getName());
		this.setUri(tag.getUri());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	
}
