package uk.ac.ebi.scilite.model.jsonLD;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationItemApi;

@ApiModel(description="It holds the annotation data in JSON-LD format. To see details about JSON-LD go to http://europepmc.org/AnnotationsApi#jsonLD")
public class AnnotationJsonLDDoc extends AnnotationJsonLD {
	
	public AnnotationJsonLDDoc(AnnotationItemApi input) {
		super(input);
		// TODO Auto-generated constructor stub
	}

	@ApiModelProperty(value="For named entity annotations (i.e. provided by Europe PMC) it represents the URI of the database resource the annotation is referring to."+
	"<br/> For sentence based annotations (i.e. provided by HES-SO / SIB Text Mining for Elixir, NaCTeM, IntAct) and annotations containing Gene Disease relationships (i.e. provided by Open Targets Platform and DisGeNET) it represents the portion of the article where the annotation is contained:"+ 
	"<br/>\"body\": {"+
	"<br/>&nbsp;&nbsp;&nbsp;\"id\": \"http://europepmc.org/abstract/MED/15892892#geneRif_356408_P36159\", # ann_link "+
	"<br/>&nbsp;&nbsp;&nbsp;\"type\": \"TextualBody\","+
	"<br/>&nbsp;&nbsp;&nbsp;\"value\": \"In this work, we show that the yeast homolog of ELAC2, encoded by TRZ1 (tRNase Z 1), is involved genetically in RNA processing.\", # exact "+
	"<br/>&nbsp;&nbsp;&nbsp;\"source\": \"http://europepmc.org/articles/MED/15892892\", # ext_id "+
	"<br/>&nbsp;&nbsp;&nbsp;\"isPartOf\": \"Abstract\"  #section "+
    "<br/>} ")
	private String body;
	
	@ApiModelProperty(value="For named entity annotations (i.e. provided by Europe PMC) it represents the portion of the article where the annotation is contained:"+
	"<br/>\"target\": {"+
	"<br/>&nbsp;&nbsp;&nbsp;\"id\": \"http://europepmc.org/articles/MED/21494379#europepmc_1-1\", # ann_link"+
	"<br/>&nbsp;&nbsp;&nbsp;\"source\": \"http://europepmc.org/articles/MED/21494379\", # ext_id"+
	"<br/>&nbsp;&nbsp;&nbsp;\"isPartOf\": \"Title\", # section"+
	"<br/>&nbsp;&nbsp;&nbsp;\"selector\": {"+
	"<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"type\": \"TextQuoteSelector\","+
	"<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"exact\": \"Fluoride\","+
	"<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"prefix\": \"\","+
	"<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"suffix\": \" concentration of some brands of ferment\""+
	"<br/>&nbsp;&nbsp;&nbsp;}"+
    "<br/>}."+
	"<br/>For sentence based annotations (i.e. provided by HES-SO / SIB Text Mining for Elixir, NaCTeM, IntAct) it represents the URI of the database resource the annotation is referring to."+
    "<br/>For annotations containing Gene Disease relationships (i.e. provided by Open Targets Platform and DisGeNET) represents the URI of both gene and disease the annotation is referring to : "+
	"<br/>\"target\": {"+
		"<br/>&nbsp;&nbsp;&nbsp;\"type\": \"List\","+
		"<br/>&nbsp;&nbsp;&nbsp;\"items\": [ { \"id\": \"http://purl.uniprot.org/uniprot/Q14790\", \"label\": \"caspase 8\" }, { \"id\": \"http://www.ebi.ac.uk/efo/EFO_0001668\", \"label\": \"HPV\" } ] # tags"+
	"<br/>}.")
	private String target;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
