package uk.ac.ebi.scilite.model.jsonLD;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@ApiModel(description="It holds a single tag tagged by a sentence based annotations")
public class AnnotationJsonLDTag {
	
	@ApiModelProperty(value="URI of the tag")
	private String id;
	
	@ApiModelProperty(value="Name of the tag")
	private String label;
	
	/**
	 * "items": [ { "id": "http://purl.uniprot.org/uniprot/Q14790", "label": "caspase 8" }, { "id": "http://www.ebi.ac.uk/efo/EFO_0001668", "label": "HPV" } ] # tags
	 * @param uri
	 * @param name
	 */
	public AnnotationJsonLDTag(String uri, String name) {
		this.setId(uri);
		this.setLabel(name);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
