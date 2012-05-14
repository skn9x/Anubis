package anubis.except;

public class AssertionException extends AnubisRuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7753753044679656421L;
	
	protected AssertionException() {
	}
	
	protected AssertionException(String message) {
		super(message);
	}
	
	protected AssertionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	protected AssertionException(Throwable cause) {
		super(cause);
	}
	
}
