package uk.ac.ebi.scilite.controller;

import java.beans.PropertyEditorSupport;

import uk.ac.ebi.scilite.model.AnnotationType;
import uk.ac.ebi.scilite.model.OutputFormat;

public class AnnotationTypeConverter extends PropertyEditorSupport {

	 public void setAsText(final String text) throws IllegalArgumentException { 
		 setValue(AnnotationType.getByInputParameter(text)); 
     }

}
