package uk.ac.ebi.scilite.model.jsonLD;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationApi;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationItemApi;

@JsonInclude(Include.NON_NULL)
@ApiModel(description="It holds the multiple tags sentence based annotations belonging to a specific article in JSON LD format.", parent=AnnotationJsonLD.class)
public class AnnotationJsonLDSentenceRelationship extends AnnotationJsonLDSentence {

	/**
	 *  {
  "@context": "http://europepmc.org/europepmc-annotation-api-vocab.json", # context file
  "id": "http://rdf.ebi.ac.uk/resource/europepmc/annotations/MED/15892892#356408", # ann
  "type": "Annotation",
  "creator": "HES-SO / SIB Text Mining for Elixir", # provider
   "target": {
    "type": "List",
    "items": [ { "id": "http://purl.uniprot.org/uniprot/Q14790", "label": "caspase 8" }, { "id": "http://www.ebi.ac.uk/efo/EFO_0001668", "label": "HPV" } ] # tags
    },
  "body": {
    "id": "http://europepmc.org/abstract/MED/15892892#geneRif_356408_P36159", # ann_link
    "type": "TextualBody",
    "value": "In this work, we show that the yeast homolog of ELAC2, encoded by TRZ1 (tRNase Z 1), is involved genetically in RNA processing.", # exact

    "source": "http://europepmc.org/articles/MED/15892892", # ext_id
    "isPartOf": "Abstract"  #section
    }
 }*/
	@ApiModelProperty(value="List of all the tags tagged by the annotation sentence")
	private AnnotationJsonLDTagList target;
	
	public AnnotationJsonLDSentenceRelationship(AnnotationItemApi input, AnnotationApi articleData) {
		super(input, articleData);
		
		this.setTarget(new AnnotationJsonLDTagList(input));
	}

	public AnnotationJsonLDTagList getTarget() {
		return target;
	}

	public void setTarget(AnnotationJsonLDTagList target) {
		this.target = target;
	}

}
