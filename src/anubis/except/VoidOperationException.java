package anubis.except;

/**
 * @author SiroKuro
 */
public class VoidOperationException extends AnubisRuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8815077953145049795L;
	
	/**
	 * 
	 */
	public VoidOperationException() {
		;
	}
	
	/**
	 * @param message
	 */
	public VoidOperationException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public VoidOperationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param cause
	 */
	public VoidOperationException(Throwable cause) {
		super(cause);
	}
	
}
