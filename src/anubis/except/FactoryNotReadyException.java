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
	public FactoryNotReadyException() {
		;
	}
	
	/**
	 * @param message
	 */
	public FactoryNotReadyException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public FactoryNotReadyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param cause
	 */
	public FactoryNotReadyException(Throwable cause) {
		super(cause);
	}
	
}
