package uk.ac.ebi.scilite.model.jsonLD;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationApi;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationItemApi;

@ApiModel(description="It holds the named entity annotations corresponding to a specific article in JSON LD format.", parent=AnnotationJsonLD.class)
@JsonInclude(Include.NON_NULL)
public class AnnotationJsonLDNamedEntity extends AnnotationJsonLD{

	
	/**
	 * "@context": "http://europepmc.org/europepmc-annotation-api-vocab.json", # context file
"id": "http://rdf.ebi.ac.uk/resource/europepmc/annotations/MED/21494379#1-1", # ann
"type": "Annotation",
"creator": "europepmc", # provider
	  "body": "http://purl.obolibrary.org/obo/CHEBI_29228", # tag-uri
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
	 * @param input
	 */
	
	@ApiModelProperty(value="Named Entity URI")
	private String body;
	
	@ApiModelProperty(value="Descriptor of the article section that is the target of the annotation")
	private AnnotationJsonLDTarget target; 
	
	public AnnotationJsonLDNamedEntity(AnnotationItemApi input, AnnotationApi articleData) {
		super(input);
		
		this.setBody(input.getTags().get(0).getUri());
		
		this.setTarget(new AnnotationJsonLDTarget (input, articleData));
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public AnnotationJsonLDTarget getTarget() {
		return target;
	}

	public void setTarget(AnnotationJsonLDTarget target) {
		this.target = target;
	}

	
}
