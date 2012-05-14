package anubis.except;

/**
 * @author SiroKuro
 */
public class SlotNotFoundException extends AnubisRuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1872262137885585713L;
	
	/**
	 * 
	 */
	protected SlotNotFoundException() {
		;
	}
	
	/**
	 * @param message
	 */
	protected SlotNotFoundException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	protected SlotNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param cause
	 */
	protected SlotNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
