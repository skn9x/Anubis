package anubis.except;

public class AssertionException extends AnubisRuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7753753044679656421L;
	
	public AssertionException() {
	}
	
	public AssertionException(String message) {
		super(message);
	}
	
	public AssertionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AssertionException(Throwable cause) {
		super(cause);
	}
	
}
