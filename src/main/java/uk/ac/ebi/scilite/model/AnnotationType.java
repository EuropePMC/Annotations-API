package uk.ac.ebi.scilite.model;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationTypeApi;

public enum AnnotationType {

	GENES_PROTEINS("Gene_Proteins",AnnotationTypeApi.GENES_PROTEINS.getLabel(), "Gene_Proteins"), 
	ORGANISMS("Organisms",AnnotationTypeApi.ORGANISMS.getLabel(), "Organisms"), 
	CHEMICALS("Chemicals", AnnotationTypeApi.CHEMICALS.getLabel(), "Chemicals"), 
	GO_TERMS("Gene Ontology", AnnotationTypeApi.GO_TERMS.getLabel(), "Gene Ontology"), 
	DISEASE("Diseases", AnnotationTypeApi.DISEASE.getLabel(), "Diseases"), 
	//EFO("Efo", AnnotationTypeApi.EFO.getLabel(), "Efo"), 
	ACCESSION_NUMBERS("Accession Numbers", AnnotationTypeApi.ACCESSION_NUMBERS.getLabel(), "Accession Numbers"), 
	GENERIF("Gene Function", AnnotationTypeApi.GENERIF.getLabel(), "Gene Function"),
	GENE_DISEASE("Gene Disease", AnnotationTypeApi.GENE_DISEASE.getLabel(), "Gene Disease Relationship"),
	PPI("Protein Interaction", AnnotationTypeApi.PPI.getLabel(), "Protein Interaction"),
	BIOLOGICAL_EVENT("Biological Event", AnnotationTypeApi.BIOLOGICAL_EVENT.getLabel(), "Biological Event"),
	UNSPECIFIED("UNSPECIFIED", "", "");
	
	private String inputParameter;
	private String dbValue;
	private String description;
	
	private AnnotationType(String inputParameter, String dbValue, String description){
		this.inputParameter=inputParameter;
		this.dbValue=dbValue;
		this.description=description;
	}

	public String getInputParameter() {
		return this.inputParameter;
	}

	public String getDbValue() {
		return dbValue;
	}
	
	public String getDescription() {
		return description;
	}

	public static AnnotationType getByInputParameter(String text){
		AnnotationType ret = AnnotationType.UNSPECIFIED;
		for (AnnotationType val : AnnotationType.values()){
			if (val.getInputParameter().equalsIgnoreCase(text)){
				ret=val;
				break;
			}
		}
		return ret;
	}
	
	public static AnnotationType getByDbValue(String dbValue){
		AnnotationType ret = AnnotationType.UNSPECIFIED;
		for (AnnotationType val : AnnotationType.values()){
			if (val.getDbValue().equalsIgnoreCase(dbValue)){
				ret=val;
				break;
			}
		}
		return ret;
	}
	
	public static String getValidationMessage() {
		List<String> validTypes = new ArrayList<String>();
		for (AnnotationType val : AnnotationType.values()){
			if (val!= AnnotationType.UNSPECIFIED){
				validTypes.add(val.getInputParameter());
			}
		}
		
		String ret = "Type not valid. It must have on of the following values: "+validTypes.toString()+". ";
		
		return ret;
	}
	
}
