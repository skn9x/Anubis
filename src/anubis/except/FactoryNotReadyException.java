package anubis.except;

/**
 * @author SiroKuro
 */
public class FactoryNotReadyException extends AnubisException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2136242029822541228L;
	
	/**
	 * 
	 */
	protected FactoryNotReadyException() {
		;
	}
	
	/**
	 * @param message
	 */
	protected FactoryNotReadyException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	protected FactoryNotReadyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param cause
	 */
	protected FactoryNotReadyException(Throwable cause) {
		super(cause);
	}
	
}
