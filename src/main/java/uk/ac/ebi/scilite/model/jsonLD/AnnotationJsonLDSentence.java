package uk.ac.ebi.scilite.model.jsonLD;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationApi;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationItemApi;

@JsonInclude(Include.NON_NULL)
@ApiModel(description="Common class to hold the data of sentence based annotations belonging to a specific article in JSON LD format.")
public class AnnotationJsonLDSentence extends AnnotationJsonLD{

	
	/**
	 *  {
  "@context": "http://europepmc.org/europepmc-annotation-api-vocab.json", # context file
  "id": "http://rdf.ebi.ac.uk/resource/europepmc/annotations/MED/15892892#356408", # ann
  "type": "Annotation",
  "creator": "HES-SO / SIB Text Mining for Elixir", # provider
  "target": "http://purl.uniprot.org/uniprot/P36159", # tag-uri
  "body": {
    "id": "http://europepmc.org/abstract/MED/15892892#geneRif_356408_P36159", # ann_link
    "type": "TextualBody",
    "value": "In this work, we show that the yeast homolog of ELAC2, encoded by TRZ1 (tRNase Z 1), is involved genetically in RNA processing.", # exact

    "source": "http://europepmc.org/articles/MED/15892892", # ext_id
    "isPartOf": "Abstract"  #section
    }
 }
	 */
	@ApiModelProperty(value="Descriptor of the sentence containing the annotation")
	private AnnotationJsonLDBodySentence body; 
	
	public AnnotationJsonLDSentence(AnnotationItemApi input, AnnotationApi articleData) {
		super(input);
		
		this.setBody(new AnnotationJsonLDBodySentence (input, articleData));
	}

	public AnnotationJsonLDBodySentence getBody() {
		return body;
	}

	public void setBody(AnnotationJsonLDBodySentence body) {
		this.body = body;
	}
	
}
