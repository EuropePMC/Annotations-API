package uk.ac.ebi.scilite.model.europepmc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.bson.Document;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.mongodb.client.FindIterable;

import uk.ac.ebi.literature.mongodb.model.annotations.AnnotationMultiple;
import uk.ac.ebi.literature.mongodb.model.annotations.AnnotationSingle;
import uk.ac.ebi.literature.mongodb.model.annotations.AnnotationTag;
import uk.ac.ebi.literature.mongodb.model.annotations.abstracts.AnnotationAbstract;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationApi;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationItemApi;
import uk.ac.ebi.scilite.common.AnnotationsLambda;
import uk.ac.ebi.literature.mongodb.model.annotations.AnnotationItem;
import uk.ac.ebi.literature.mongodb.model.annotations.AnnotationItemGeneral;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultEuropePMC {
	
	@JsonIgnore
	private Object _id;
	
	private String extId;
	
	private String src;
	
	private String provider;
	
	private String pmcId;
	
	private DataEuropePMC results;

	public DataEuropePMC getResults() {
		return results;
	}

	public void setResults(DataEuropePMC results) {
		this.results = results;
	}
	
	public void setExtId(String extId){
		this.extId=extId;
	}

	public Object get_id() {
		return _id;
	}

	public void set_id(Object _id) {
		this._id = _id;
	}

	public String getExtId() {
		return extId;
	}
	
	public ResultEuropePMC(){
		DataEuropePMC results = new DataEuropePMC();
		results.setBindings(new ArrayList<Binding>());
		results.setDistinct(false);
		results.setOrdered(true);
		this.setResults(results);
	}
	
	public ResultEuropePMC(AnnotationApi annotationsData, String provider){
		this.setExtId(annotationsData.getExt_id());
		this.setProvider(provider);
		this.setSrc(annotationsData.getSrc());
		if (annotationsData.getPmcid()!=null){
			this.setPmcId(annotationsData.getPmcid().toString());
		}else{
			this.setPmcId(null);
		}
		
		
		List<Binding> bindings = new ArrayList<Binding>();
		
		Binding bindingData;
		List<TagElement> tagsList;
		if (annotationsData!=null){
			
			List<AnnotationItemApi> filteredAnnotations =annotationsData.getAnns();
			
			for (AnnotationItemApi annotationData : filteredAnnotations){
				bindingData = new Binding();
				bindingData.setAnnotation(new BindingElement(annotationData.getAnn()));
				bindingData.setPosition(new BindingElement(annotationData.getPosition()));
				tagsList = new ArrayList<TagElement>();
				for (AnnotationTag tagData : annotationData.getTags()){
					tagsList.add(new TagElement(tagData.getName(), tagData.getUri()));
				}
				bindingData.setTags(tagsList);
				if ("europepmc".equalsIgnoreCase(provider)){
					bindingData.setPrefix(new BindingElement(StringEscapeUtils.unescapeJava(StringEscapeUtils.unescapeXml(annotationData.getPrefix()))));
					bindingData.setPostfix(new BindingElement(StringEscapeUtils.unescapeJava(StringEscapeUtils.unescapeXml(annotationData.getPostfix()))));
				}else{
					bindingData.setPrefix(new BindingElement(annotationData.getPrefix()));
					bindingData.setPostfix(new BindingElement(annotationData.getPostfix()));
				}
				bindingData.setExact(new BindingElement(StringEscapeUtils.unescapeJava(annotationData.getExact())));
				bindings.add(bindingData);
			}
		}
		
		DataEuropePMC results = new DataEuropePMC();
		results.setBindings(bindings);
		results.setDistinct(false);
		results.setOrdered(true);
		this.setResults(results);
	}

	public ResultEuropePMC(AnnotationMultiple preprintsData, String provider) {
		this.setExtId(preprintsData.getPmcid().toString());
		this.setProvider(provider);
		this.setSrc("PMC");
		this.setPmcId(preprintsData.getPmcid().toString());
		
		
		List<Binding> bindings = new ArrayList<Binding>();
		
		Binding bindingData;
		List<TagElement> tagsList;
		if (preprintsData!=null){
			
			List<AnnotationItem> filteredAnnotations =preprintsData.getAnns();
			
			for (AnnotationItem annotationData : filteredAnnotations){
				bindingData = new Binding();
				bindingData.setAnnotation(new BindingElement(annotationData.getAnn()));
				bindingData.setPosition(new BindingElement(annotationData.getPosition()));
				tagsList = new ArrayList<TagElement>();
				tagsList.add(new TagElement(annotationData.getExact(), annotationData.getTag()));
				bindingData.setTags(tagsList);
				if ("europepmc".equalsIgnoreCase(provider)){
					bindingData.setPrefix(new BindingElement(StringEscapeUtils.unescapeJava(StringEscapeUtils.unescapeXml(annotationData.getPrefix()))));
					bindingData.setPostfix(new BindingElement(StringEscapeUtils.unescapeJava(StringEscapeUtils.unescapeXml(annotationData.getPostfix()))));
				}else{
					bindingData.setPrefix(new BindingElement(annotationData.getPrefix()));
					bindingData.setPostfix(new BindingElement(annotationData.getPostfix()));
				}
				bindingData.setExact(new BindingElement(StringEscapeUtils.unescapeJava(annotationData.getExact())));
				bindings.add(bindingData);
			}
		}
		
		DataEuropePMC results = new DataEuropePMC();
		results.setBindings(bindings);
		results.setDistinct(false);
		results.setOrdered(true);
		this.setResults(results);
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getPmcId() {
		return pmcId;
	}

	public void setPmcId(String pmcId) {
		this.pmcId = pmcId;
	}
	
}
