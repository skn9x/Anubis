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
	public JOverloadMismatchException() {
		;
	}
	
	/**
	 * @param message
	 */
	public JOverloadMismatchException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public JOverloadMismatchException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param cause
	 */
	public JOverloadMismatchException(Throwable cause) {
		super(cause);
	}
	
}
