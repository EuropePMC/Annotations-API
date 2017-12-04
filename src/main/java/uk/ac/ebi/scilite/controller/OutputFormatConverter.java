package uk.ac.ebi.scilite.controller;

import java.beans.PropertyEditorSupport;

import uk.ac.ebi.scilite.model.OutputFormat;

public class OutputFormatConverter extends PropertyEditorSupport {

	 public void setAsText(final String text) throws IllegalArgumentException { 
		 setValue(OutputFormat.getOutputFormat(text)); 
     }

}
