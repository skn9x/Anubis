package anubis.except;

/**
 * @author SiroKuro
 */
public class SlotReadonlyException extends AnubisRuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5748286537107496644L;
	
	public SlotReadonlyException() {
		super();
	}
	
	public SlotReadonlyException(String message) {
		super(message);
	}
	
	public SlotReadonlyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SlotReadonlyException(Throwable cause) {
		super(cause);
	}
	
}
