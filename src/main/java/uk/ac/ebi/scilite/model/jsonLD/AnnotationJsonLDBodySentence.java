package uk.ac.ebi.scilite.model.jsonLD;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationApi;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationItemApi;
import uk.ac.ebi.scilite.model.AnnotationSection;

@JsonInclude(Include.NON_NULL)
@ApiModel(description="Descriptor of the sentence containing the annotation")
public class AnnotationJsonLDBodySentence {
	
	/**
	"body": {
    "id": "http://europepmc.org/abstract/MED/15892892#geneRif_356408_P36159", # ann_link
    "type": "TextualBody",
    "value": "In this work, we show that the yeast homolog of ELAC2, encoded by TRZ1 (tRNase Z 1), is involved genetically in RNA processing.", # exact

    "source": "http://europepmc.org/articles/MED/15892892", # ext_id
    "isPartOf": "Abstract"  #section
    }
  */
	@ApiModelProperty(value="Link back to the specific sentence of the article that contains the annotation")
	private String id;
	
	@ApiModelProperty(value="Url of the article containing the annotation")
	private String source;
	
	@ApiModelProperty(value="Section of the article containing the annotation")
	private String isPartOf;
	
	@ApiModelProperty(value="Type of the selector")
	private String type;
	
	@ApiModelProperty(value="Text of the sentence inside the article containing the annotation")
	private String value;
	
	public AnnotationJsonLDBodySentence(AnnotationItemApi input, AnnotationApi articleData){
		this.setId(input.getAnn_link());
		this.setSource(AnnotationJsonLD.getUrlPage(input, articleData));
	    AnnotationSection section = AnnotationSection.getSectionByDbValue(input.getSection());
		this.setIsPartOf(section.getJsonLdDescription());
		this.setType("TextualBody");
		this.setValue(input.getExact());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getIsPartOf() {
		return isPartOf;
	}

	public void setIsPartOf(String isPartOf) {
		this.isPartOf = isPartOf;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	


}
