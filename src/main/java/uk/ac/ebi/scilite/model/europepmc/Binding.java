package uk.ac.ebi.scilite.model.europepmc;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Binding {
	
	@JsonIgnore
	private Object _id;
	
	public Object get_id() {
		return _id;
	}
	public void set_id(Object _id) {
		this._id = _id;
	}
	private BindingElement annotation;
	private BindingElement position;
	private List<TagElement> tags = new ArrayList<TagElement>();
	private BindingElement prefix;
	private BindingElement exact;
	private BindingElement postfix;
	//private BindingElement section;
	//private BindingElement source;
	//private boolean valid;
	//private String pmcid;
	
	public List<TagElement> getTags() {
		return tags;
	}
	public void setTags(List<TagElement> tags) {
		this.tags = tags;
	}
	public BindingElement getPrefix() {
		return prefix;
	}
	public void setPrefix(BindingElement prefix) {
		this.prefix = prefix;
	}
	public BindingElement getExact() {
		return exact;
	}
	public void setExact(BindingElement exact) {
		this.exact = exact;
	}
	public BindingElement getPostfix() {
		return postfix;
	}
	public void setPostfix(BindingElement postfix) {
		this.postfix = postfix;
	}
	
	public BindingElement getPosition() {
		return position;
	}
	public void setPosition(BindingElement position) {
		this.position = position;
	}
	
	public BindingElement getAnnotation() {
		return annotation;
	}
	public void setAnnotation(BindingElement annotation) {
		this.annotation = annotation;
	}
	
}
