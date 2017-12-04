package uk.ac.ebi.scilite.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.scilite.model.jsonLD.AnnotationJsonLD;
import uk.ac.ebi.scilite.model.jsonLD.AnnotationJsonLDDoc;

@ApiModel(description="It holds the annotations of the articles coming from the api when the user search by Article Ids. The response will be an array of articles of type ArticleResult in case the format parameter is equal to either JSON or XML or ID_LIST. Otherwise if the format parameter is equal to JSON_LD the response will be an array of annotations of type AnnotationJsonLDDoc. To see details about Json LD go to http://europepmc.org/AnnotationsApi#jsonLD. ")
@JsonInclude(Include.NON_NULL)
public class ResultArticleIdDoc {
	@ApiModelProperty(value="List of articles with the relative annotations. It is only populated when the format parameter is equal to either JSON or XML or ID_LIST")
	private List<ArticleResult> articles;
	
	@ApiModelProperty(value="List of annotations in JSON-LD format. It is only populated when the format parameter is equal to JSON-LD. To see details about JSON-LD go to http://europepmc.org/AnnotationsApi#jsonLD")
	private List<AnnotationJsonLDDoc> annotations;
	
	public ResultArticleIdDoc(OutputFormat format){
		
		
		if (format==OutputFormat.JSON_LD){
			this.articles=null;
			this.annotations = new ArrayList<AnnotationJsonLDDoc>();
		}else{
			this.articles = new ArrayList<ArticleResult>();
			this.annotations=null;
		}
		
	}
	
	public List<ArticleResult> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleResult> articles) {
		this.articles = articles;
	}

	public List<AnnotationJsonLDDoc> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<AnnotationJsonLDDoc> annotations) {
		this.annotations = annotations;
	}
	
}
