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
	public NotCallableException() {
		;
	}
	
	/**
	 * @param message
	 */
	public NotCallableException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public NotCallableException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param cause
	 */
	public NotCallableException(Throwable cause) {
		super(cause);
	}
	
}
