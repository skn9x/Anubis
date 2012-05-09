package anubis.except;

/**
 * @author SiroKuro
 */
public class AnubisParserException extends AnubisRuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6476766469011922021L;
	
	/**
	 * 
	 */
	public AnubisParserException() {
	}
	
	/**
	 * @param message
	 */
	public AnubisParserException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public AnubisParserException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param cause
	 */
	public AnubisParserException(Throwable cause) {
		super(cause);
	}
	
}
