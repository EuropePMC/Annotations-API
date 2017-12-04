package uk.ac.ebi.scilite.model;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationItemApi;

@ApiModel(description="It holds the data of a specific annotation")
@JsonInclude(Include.NON_NULL)
@XmlType(propOrder = {"prefix", "exact", "postfix", "tags", "ann_link", "type", "section", "provider"})
@JsonPropertyOrder({"prefix", "exact", "postfix", "tags", "ann_link", "type", "section", "provider"})
public class Annotation implements Serializable{
	
	@ApiModelProperty(value="List of external database resources connected with the annotation")
	@JacksonXmlElementWrapper(localName = "tags")
	@JacksonXmlProperty(localName="tag")
	private List<AnnotationTagInfo> tags;
	
	@ApiModelProperty(value="Prefix of the annotation inside the article")
	private String prefix;
	
	@ApiModelProperty(value="Text of the annotation inside the article")
	private String exact;
	
	@ApiModelProperty(value="Suffix of the annotation inside the article")
	private String postfix;
	
	@ApiModelProperty(value="Link to be used to link back to the specific sentence of the article that contains the annotation")
	private String ann_link;

	@ApiModelProperty(value="Semantic type of the annotation")
	private String type;
	
	@ApiModelProperty(value="Section of the article where the annotation is appearing")
	private String section;
	
	@ApiModelProperty(value="Provider of the annotation")
	private String provider;
	
	public Annotation (AnnotationItemApi input){
		this.setAnn_link(input.getAnn_link());
		this.setAnn_link(null);
		this.setExact(input.getExact());
		this.setPostfix(input.getPostfix());
		this.setPrefix(input.getPrefix());
		List<AnnotationTagInfo> tagsInfo = input.getTags().stream().map(tag -> new AnnotationTagInfo(tag)).collect(Collectors.<AnnotationTagInfo>toList());
		this.setTags(tagsInfo);
		this.setSection(AnnotationSection.getSectionByDbValue(input.getSection()).getDescription());
		this.setProvider(AnnotationProvider.getProviderByDbValue(input.getProvider()).getDescription());
		this.setType(AnnotationType.getByDbValue(input.getType()).getDescription());
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<AnnotationTagInfo> getTags() {
		return tags;
	}

	public void setTags(List<AnnotationTagInfo> tags) {
		this.tags = tags;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getExact() {
		return exact;
	}

	public void setExact(String exact) {
		this.exact = exact;
	}

	public String getPostfix() {
		return postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	public String getAnn_link() {
		return ann_link;
	}

	public void setAnn_link(String ann_link) {
		this.ann_link = ann_link;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
}
