package anubis.except;

/**
 * @author SiroKuro
 */
public class JOverloadMismatchException extends AnubisRuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2874993653868050760L;
	
	/**
	 * 
	 */
	protected JOverloadMismatchException() {
		;
	}
	
	/**
	 * @param message
	 */
	protected JOverloadMismatchException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	protected JOverloadMismatchException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param cause
	 */
	protected JOverloadMismatchException(Throwable cause) {
		super(cause);
	}
	
}
