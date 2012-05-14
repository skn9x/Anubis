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
	protected VoidOperationException() {
		;
	}
	
	/**
	 * @param message
	 */
	protected VoidOperationException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	protected VoidOperationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param cause
	 */
	protected VoidOperationException(Throwable cause) {
		super(cause);
	}
	
}
