package anubis.except;

/**
 * @author SiroKuro
 */
public class ObjectFreezeException extends AnubisRuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5748286537107496644L;
	
	protected ObjectFreezeException() {
		super();
	}
	
	protected ObjectFreezeException(String message) {
		super(message);
	}
	
	protected ObjectFreezeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	protected ObjectFreezeException(Throwable cause) {
		super(cause);
	}
	
}
