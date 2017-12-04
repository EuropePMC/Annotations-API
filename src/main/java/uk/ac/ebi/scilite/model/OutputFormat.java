package uk.ac.ebi.scilite.model;

public enum OutputFormat {

	JSON_LD("json-ld"),
	JSON("json"),
	XML("xml"),
	ID_LIST("id_list"),
	TSV("tsv"),
	HTML("html");
	
	private String label;

	private OutputFormat(String label){
		this.label=label;
	}

	public String getLabel() {
		return label;
	}
	
	public static OutputFormat getOutputFormat(String text){
		OutputFormat ret = OutputFormat.JSON;
		for (OutputFormat outputFormat : OutputFormat.values()){
			if (outputFormat.getLabel().equalsIgnoreCase(text)){
				ret=outputFormat;
				break;
			}
		}
		
		return ret;
		
	}
	
	
}
