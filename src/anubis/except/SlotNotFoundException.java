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
	public SlotNotFoundException() {
		;
	}
	
	/**
	 * @param message
	 */
	public SlotNotFoundException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public SlotNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param cause
	 */
	public SlotNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
