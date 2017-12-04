package uk.ac.ebi.scilite.model.europepmc;

public class BindingElement {
	
	public BindingElement(String value){
		this.setValue(value);
	}

	//private String datatype;
	
	//private String type;
	
	private String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
}
