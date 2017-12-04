package uk.ac.ebi.scilite.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.scilite.model.jsonLD.AnnotationJsonLD;

@ApiModel(description="It holds the annotations of a number of articles coming from the api")
@JsonInclude(Include.NON_NULL)
@JacksonXmlRootElement(localName="Results")
@XmlType(propOrder = {"nextCursorMark", "cursorMark", "articles", "annotations"})
@JsonPropertyOrder({"nextCursorMark", "cursorMark", "articles", "annotations"})
public class Result {
	
	@ApiModelProperty(value="cursorMark to be used in the next call to get the next page of the result set")
	private Double nextCursorMark;
	
	@ApiModelProperty(value="cursorMark used in the current call to get the current page of the result set")
	private Double cursorMark;

	@ApiModelProperty(value="list of articles with the relative annotations. It is populated only in case the format parameter is equal to either JSON or XML")
	@JacksonXmlElementWrapper(localName = "articles")
	@JacksonXmlProperty(localName="article")
	private List<ArticleResult> articles;
	
	@ApiModelProperty(value="list of annotations in JSON LD format. It is populated only in case the format parameter is equal to JSON_LD")
	private List<AnnotationJsonLD> annotations;
	
	public Result(OutputFormat format){
		this.setNextCursorMark(-1.0);
		this.setCursorMark(null);
		
		if (format==OutputFormat.JSON_LD){
			this.articles=null;
			this.annotations = new ArrayList<AnnotationJsonLD>();
		}else{
			this.articles = new ArrayList<ArticleResult>();
			this.annotations=null;
		}
		
	}
	
	public Double getNextCursorMark() {
		return nextCursorMark;
	}

	public void setNextCursorMark(Double nextCursorMark) {
		this.nextCursorMark = nextCursorMark;
	}
	
	public List<ArticleResult> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleResult> articles) {
		this.articles = articles;
	}

	public Double getCursorMark() {
		return cursorMark;
	}

	public void setCursorMark(Double cursorMark) {
		this.cursorMark = cursorMark;
	}

	public List<AnnotationJsonLD> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<AnnotationJsonLD> annotations) {
		this.annotations = annotations;
	}
	
}
