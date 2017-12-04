package uk.ac.ebi.scilite.model.europepmc;

public class TagElement {
	
	private String uri;
	private String name;
	
	public TagElement(String name, String uri){
		this.setName(name);
		this.setUri(uri);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
