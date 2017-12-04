package uk.ac.ebi.scilite.model.jsonLD;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationApi;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationItemApi;
import uk.ac.ebi.scilite.model.AnnotationSection;

@ApiModel(description="It describes the part of the article that it is a target of the annotation")
@JsonInclude(Include.NON_NULL)
public class AnnotationJsonLDTarget {
	
	/**
	"target": {
    "id": "http://europepmc.org/articles/MED/21494379#europepmc_1-1", # ann_link
    "source": "http://europepmc.org/articles/MED/21494379", # ext_id
    "isPartOf": "Title", # section
    "selector": {
      "type": "TextQuoteSelector",
      "exact": "Fluoride",
      "prefix": "",
      "suffix": " concentration of some brands of ferment"
    }
  }
  */
	@ApiModelProperty(value="Link back to the specific sentence of the article that contains the annotation")
	private String id;
	
	@ApiModelProperty(value="Url of the article containing the annotation")
	private String source;
	
	@ApiModelProperty(value="Section of the article containing the annotation")
	private String isPartOf;
	
	@ApiModelProperty(value="Selector of the portion of the article containing the annotation")
	private AnnotationJsonLDTextSelector selector;
	
	public AnnotationJsonLDTarget(AnnotationItemApi input, AnnotationApi articleData){
		this.setId(input.getAnn_link());
		this.setSource(AnnotationJsonLD.getUrlPage(input, articleData));
	    AnnotationSection section = AnnotationSection.getSectionByDbValue(input.getSection());
		this.setIsPartOf(section.getJsonLdDescription());
		this.setSelector(new AnnotationJsonLDTextSelector(input));
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

	public AnnotationJsonLDTextSelector getSelector() {
		return selector;
	}

	public void setSelector(AnnotationJsonLDTextSelector selector) {
		this.selector = selector;
	}
	


}
