package uk.ac.ebi.scilite.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.scilite.model.jsonLD.AnnotationJsonLD;
import uk.ac.ebi.scilite.model.jsonLD.AnnotationJsonLDDoc;

@ApiModel(description="It holds the annotations of a number of articles")
@JsonInclude(Include.NON_NULL)
@XmlType(propOrder = {"nextCursorMark", "cursorMark", "articles", "annotations"})
@JsonPropertyOrder({"nextCursorMark", "cursorMark", "articles", "annotations"})
public class ResultDoc {
	
	@ApiModelProperty(value="CursorMark to be used in the next call to get the next page of the result set")
	private Double nextCursorMark;
	
	@ApiModelProperty(value="CursorMark used in the current call to get the current page of the result set")
	private Double cursorMark;

	@ApiModelProperty(value="List of articles with the relative annotations. It is only populated when the format parameter is equal to either JSON or XML or ID_LIST")
	private List<ArticleResult> articles;
	
	@ApiModelProperty(value="List of annotations in JSON-LD format. It is only populated when the format parameter is equal to JSON-LD. To see details about JSON-LD go to http://europepmc.org/AnnotationsApi#jsonLD")
	private List<AnnotationJsonLDDoc> annotations;
	
	public ResultDoc(OutputFormat format){
		this.setNextCursorMark(-1.0);
		this.setCursorMark(null);
		
		if (format==OutputFormat.JSON_LD){
			this.articles=null;
			this.annotations = new ArrayList<AnnotationJsonLDDoc>();
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

	public List<AnnotationJsonLDDoc> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<AnnotationJsonLDDoc> annotations) {
		this.annotations = annotations;
	}
	
}
