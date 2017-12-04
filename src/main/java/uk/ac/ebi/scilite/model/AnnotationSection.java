package uk.ac.ebi.scilite.model;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationSectionApi;


public enum AnnotationSection {

	TITLE("Title", AnnotationSectionApi.TITLE.getLabel(),"Title (http://purl.org/dc/elements/1.1/title)", "Title"), 
	ABSTRACT("Abstract", AnnotationSectionApi.ABSTRACT.getLabel(),"Abstract (http://purl.org/dc/terms/abstract)", "Abstract"), 
	INTRODUCTION("Introduction", AnnotationSectionApi.INTRODUCTION.getLabel(),"Introduction (http://purl.org/orb/Introduction)", "Introduction"), 
	METHODS("Methods",  AnnotationSectionApi.METHODS.getLabel(),"Methods (http://purl.org/orb/Methods)", "Methods"), 
	RESULTS("Results",  AnnotationSectionApi.RESULTS.getLabel(), "Results (http://purl.org/orb/Results)", "Results"), 
	DISCUSSION("Discussion",  AnnotationSectionApi.DISCUSSION.getLabel(), "Discussion (http://purl.org/orb/Discussion)", "Discussion"), 
	ACKNOWLEDGEMENTS("Acknowledgments",  AnnotationSectionApi.ACKNOWLEDGEMENTS.getLabel(), "Acknowledgments (http://purl.org/orb/Acknowledgments)", "Acknowledgments"), 
	REFERENCES("References",  AnnotationSectionApi.REFERENCES.getLabel(), "References (http://purl.org/orb/References)", "References"), 
	ARTICLE("Article",  AnnotationSectionApi.ARTICLE.getLabel(), "Article (http://semanticscience.org/resource/SIO_001029)", "Article"), 
	TABLE("Table",  AnnotationSectionApi.TABLE.getLabel(), "Table (http://semanticscience.org/resource/SIO_000419)", "Table"), 
	FIGURE("Figure",  AnnotationSectionApi.FIGURE.getLabel(), "Figure (http://semanticscience.org/resource/SIO_000080)", "Figure"), 
	CASE_STUDY("Case study",  AnnotationSectionApi.CASE_STUDY.getLabel(), "Case study (http://purl.obolibrary.org/obo/IAO_0000613)","CaseStudy"), 
	SUPPLEMENTARY_MATERIAL("Supplementary material",  AnnotationSectionApi.SUPPLEMENTARY_MATERIAL.getLabel(), "Supplementary material (http://purl.obolibrary.org/obo/IAO_0000326)", "SupplementaryMaterial"), 
	CONCLUSION("Conclusion",  AnnotationSectionApi.CONCLUSION.getLabel(), "Conclusion (http://purl.obolibrary.org/obo/IAO_0000615)", "Conclusion"), 
	ABBREVIATION("Abbreviations",  AnnotationSectionApi.ABBREVIATION.getLabel(), "Abbreviations (http://purl.obolibrary.org/obo/IAO_0000606)", "Abbreviations"),
	COMPETING_INTEREST("Competing Interests", AnnotationSectionApi.COMPETING_INTEREST.getLabel(), "Competing Interests (http://purl.obolibrary.org/obo/IAO_0000616)", "CompetingInterests"),
	AUTHOR_CONTRIBUTIONS("Author Contributions", AnnotationSectionApi.AUTHOR_CONTRIBUTIONS.getLabel(), "Author Contributions (http://purl.obolibrary.org/obo/IAO_0000323)", "AuthorContributions"),
	UNSPECIFIED("UNSPECIFIED", "", "", "");
	
	private String inputParameter;
	private String dbValue;
	private String description;
	private String jsonLdDescription;
	
	private AnnotationSection(String inputParameter, String dbValue, String description, String jsonLdDescription){
		this.inputParameter = inputParameter;
		this.dbValue = dbValue;
		this.description=description;
		this.jsonLdDescription = jsonLdDescription;
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
	
	public String getJsonLdDescription() {
		return jsonLdDescription;
	}
	
	public static AnnotationSection getSectionByInputParameter (String inputParameter){
		for (AnnotationSection val : AnnotationSection.values()){
			if (val.getInputParameter().equalsIgnoreCase(inputParameter)){
				return val;
			}
		}
		
		return UNSPECIFIED;
	}
	
	public static AnnotationSection getSectionByDbValue (String dbValue){
		for (AnnotationSection val : AnnotationSection.values()){
			if (val.getDbValue().equalsIgnoreCase(dbValue)){
				return val;
			}
		}
		
		return UNSPECIFIED;
	}
	
	public static String getValidationMessage() {
		List<String> validSections = new ArrayList<String>();
		for (AnnotationSection val : AnnotationSection.values()){
			if (val!= AnnotationSection.UNSPECIFIED){
				validSections.add(val.getInputParameter());
			}
		}
		
		String ret = "Section not valid. It must have on of the following values: "+validSections.toString()+". ";
		
		return ret;
	}

}
