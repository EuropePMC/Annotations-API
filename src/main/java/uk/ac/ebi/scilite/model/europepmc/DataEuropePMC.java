package uk.ac.ebi.scilite.model.europepmc;

import java.util.List;

public class DataEuropePMC {

	private boolean distinct;
	
	private boolean ordered;
	
	private List<Binding> bindings;

	public List<Binding> getBindings() {
		return bindings;
	}

	public void setBindings(List<Binding> bindings) {
		this.bindings = bindings;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public boolean isOrdered() {
		return ordered;
	}

	public void setOrdered(boolean ordered) {
		this.ordered = ordered;
	}
		
		
}
