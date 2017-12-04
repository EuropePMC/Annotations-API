package uk.ac.ebi.scilite.model;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationProviderApi;
import uk.ac.ebi.scilite.model.jsonLD.AnnotationJsonLD;
import uk.ac.ebi.scilite.model.jsonLD.AnnotationJsonLDNamedEntity;
import uk.ac.ebi.scilite.model.jsonLD.AnnotationJsonLDSentenceNoRelationship;
import uk.ac.ebi.scilite.model.jsonLD.AnnotationJsonLDSentenceRelationship;

public enum AnnotationProvider {

	EUROPEPMC("Europe PMC", AnnotationProviderApi.EUROPEPMC.getLabel(), "Europe PMC", AnnotationJsonLDNamedEntity.class), 
	GENERIF("HES-SO_SIB", AnnotationProviderApi.GENERIF.getLabel(), "HES-SO / SIB Text Mining for Elixir", AnnotationJsonLDSentenceNoRelationship.class), 
	OPEN_TARGET("OpenTargets", AnnotationProviderApi.OPEN_TARGET.getLabel(), "Open Targets Platform", AnnotationJsonLDSentenceRelationship.class), 
	NACTEM("NaCTeM", AnnotationProviderApi.NACTEM.getLabel(), "National Centre for Text Mining (NaCTeM)", AnnotationJsonLDSentenceNoRelationship.class),
	INTACT("IntAct", AnnotationProviderApi.INTACT.getLabel(), "IntAct", AnnotationJsonLDSentenceNoRelationship.class), 
	DISGENET("DisGeNET", AnnotationProviderApi.DISGENET.getLabel(), "DisGeNET", AnnotationJsonLDSentenceRelationship.class), 
	UNSPECIFIED("UNSPECIFIED", "", "", AnnotationJsonLDNamedEntity.class);

	
	private String inputParameter;
	private String dbValue;
	private String description;
	private Class<? extends AnnotationJsonLD> jsonLdClass;
	
	private AnnotationProvider(String inputParameter, String dbValue, String description, Class<? extends AnnotationJsonLD> jsonLdClass){
		this.inputParameter = inputParameter;
		this.dbValue = dbValue;
		this.description = description;
		this.jsonLdClass = jsonLdClass;
	}

	public String getInputParameter() {
		return this.inputParameter;
	}
	
	public String getDbValue() {
		return this.dbValue;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public static AnnotationProvider getProviderByInputParameter (String inputParameter){
		for (AnnotationProvider val : AnnotationProvider.values()){
			if (val.getInputParameter().equalsIgnoreCase(inputParameter)){
				return val;
			}
		}
		
		return UNSPECIFIED;
	}
	
	public static AnnotationProvider getProviderByDbValue (String dbValue){
		for (AnnotationProvider val : AnnotationProvider.values()){
			if (val.getDbValue().equalsIgnoreCase(dbValue)){
				return val;
			}
		}
		
		return UNSPECIFIED;
	}

	public static String getValidationMessage() {
		List<String> validProviders = new ArrayList<String>();
		for (AnnotationProvider val : AnnotationProvider.values()){
			if (val!= AnnotationProvider.UNSPECIFIED){
				validProviders.add(val.getInputParameter());
			}
		}
		
		String ret = "Provider not valid. It must have on of the following values: "+validProviders.toString()+". ";
		
		return ret;
	}

	public Class<? extends AnnotationJsonLD> getJsonLdClass() {
		return jsonLdClass;
	}
	
}
