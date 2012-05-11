package anubis.except;

/**
 * @author SiroKuro
 */
public class ObjectFreezeException extends AnubisRuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5748286537107496644L;
	
	public ObjectFreezeException() {
		super();
	}
	
	public ObjectFreezeException(String message) {
		super(message);
	}
	
	public ObjectFreezeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ObjectFreezeException(Throwable cause) {
		super(cause);
	}
	
}
