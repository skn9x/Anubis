package anubis.except;

/**
 * @author SiroKuro
 */
public class ParseException extends AnubisRuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6476766469011922021L;
	
	/**
	 * 
	 */
	protected ParseException() {
	}
	
	/**
	 * @param message
	 */
	protected ParseException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	protected ParseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param cause
	 */
	protected ParseException(Throwable cause) {
		super(cause);
	}
	
}
