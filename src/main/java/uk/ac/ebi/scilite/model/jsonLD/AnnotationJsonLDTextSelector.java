package uk.ac.ebi.scilite.model.jsonLD;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationItemApi;

@JsonInclude(Include.NON_NULL)
@ApiModel(description="It describes the selector of the portion of the article containing the annotation")
public class AnnotationJsonLDTextSelector{

	
	/**
	 *  
    "selector": {
      "type": "TextQuoteSelector",
      "exact": "Fluoride",
      "prefix": "",
      "suffix": " concentration of some brands of ferment"
    }
	 */
	
	@ApiModelProperty(value="Type of the selector")
	private String type;
	
	@ApiModelProperty(value="Text of the annotation inside the article")
	private String exact;
	
	@ApiModelProperty(value="Prefix of the annotation inside the article")
	private String prefix;
	
	@ApiModelProperty(value="Suffix of the annotation inside the article")
	private String suffix;
	
	public AnnotationJsonLDTextSelector(AnnotationItemApi input) {
		this.setType("TextQuoteSelector");
		this.setExact(input.getExact()!=null ? input.getExact() : "");
		this.setPrefix(input.getPrefix()!=null ? input.getPrefix() : "");
		this.setSuffix(input.getPostfix()!=null ? input.getPostfix() : "");
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExact() {
		return exact;
	}

	public void setExact(String exact) {
		this.exact = exact;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	
}
