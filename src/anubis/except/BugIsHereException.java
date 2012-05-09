package anubis.except;

public class BugIsHereException extends AnubisException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3484753599366445043L;
	
	public BugIsHereException() {
		super();
	}
	
	public BugIsHereException(String message) {
		super(message);
	}
	
	public BugIsHereException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BugIsHereException(Throwable cause) {
		super(cause);
	}
	
}
