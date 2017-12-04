package uk.ac.ebi.scilite.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackageClasses = AnnotationsAPIController.class)
public class AnnotationsAPIControllerAdvice {
	
	@ExceptionHandler(AnnotationsAPIParameterException.class)
    @ResponseBody
    ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        return new ResponseEntity<>(new AnnotationsAPIParameterErrorType(status.value(), ex.getMessage(), status.getReasonPhrase(), request.getRequestURI(), ex.getClass().getCanonicalName()), status);
    }

}
