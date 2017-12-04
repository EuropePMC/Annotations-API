package uk.ac.ebi.scilite.controller;

public class AnnotationsAPIParameterErrorType {
	
	private long timestamp;

	private int status;
	
	private String error;
	
	private String exception;
	
	private String message;
	
	private String path;
	
	/**timestamp	1504686619938
	status	400
	error	"Bad Request"
	exception	"org.springframework.web.bind.MissingServletRequestParameterException"
	message	"Required AnnotationProvider parameter 'provider' is not present"
	path	"/scilite_api/annotationsArticlesByProvider"*/
	
	public AnnotationsAPIParameterErrorType(int status, String message, String error, String path, String exception) {
		this.setStatus(status);
		this.setMessage(message);
		this.setTimestamp(System.currentTimeMillis());
		this.setError(error);
		this.setPath(path);
		this.setException(exception);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
