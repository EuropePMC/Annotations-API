package uk.ac.ebi.scilite.model.jsonLD;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationItemApi;

@JsonInclude(Include.NON_NULL)
@ApiModel(description="It holds the multiple tags tagged by a sentence based annotations")
public class AnnotationJsonLDTagList {

	/**
	 * "target": {
    "type": "List",
    "items": [ { "id": "http://purl.uniprot.org/uniprot/Q14790", "label": "caspase 8" }, { "id": "http://www.ebi.ac.uk/efo/EFO_0001668", "label": "HPV" } ] # tags
    },
	 * @param input
	 */
	
	@ApiModelProperty(value="Type of tags container")
	private String type;
	
	@ApiModelProperty(value="List of tags tagged by a sentence based annotations")
	private List<AnnotationJsonLDTag> items;
	
	public AnnotationJsonLDTagList(AnnotationItemApi input) {
		this.setType("List");
		List<AnnotationJsonLDTag> tagList = input.getTags().stream().map(tag -> new AnnotationJsonLDTag(tag.getUri(), tag.getName())).collect(Collectors.<AnnotationJsonLDTag>toList());
		this.setItems(tagList);
	}

	public List<AnnotationJsonLDTag> getItems() {
		return items;
	}

	public void setItems(List<AnnotationJsonLDTag> items) {
		this.items = items;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
