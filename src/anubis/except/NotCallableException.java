package anubis.except;

/**
 * @author SiroKuro
 */
public class NotCallableException extends AnubisRuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3630857915063248586L;
	
	/**
	 * 
	 */
	protected NotCallableException() {
		;
	}
	
	/**
	 * @param message
	 */
	protected NotCallableException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	protected NotCallableException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param cause
	 */
	protected NotCallableException(Throwable cause) {
		super(cause);
	}
	
}
