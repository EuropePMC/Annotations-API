package uk.ac.ebi.scilite.controller;

import java.beans.PropertyEditorSupport;

import uk.ac.ebi.literature.mongodb.model.annotations.sciliteapi.AnnotationApi;
import uk.ac.ebi.scilite.model.AnnotationProvider;
import uk.ac.ebi.scilite.model.AnnotationSection;
import uk.ac.ebi.scilite.model.AnnotationType;
import uk.ac.ebi.scilite.model.OutputFormat;

public class AnnotationSectionConverter extends PropertyEditorSupport {

	 public void setAsText(final String text) throws IllegalArgumentException { 
		 setValue(AnnotationSection.getSectionByInputParameter(text)); 
     }

}
