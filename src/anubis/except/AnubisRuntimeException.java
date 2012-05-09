package anubis.except;

/**
 * AnubisRuntimeException は、スクリプト自体が原因となってスローされる各種例外のスーパークラスです。
 * @author SiroKuro
 */
public class AnubisRuntimeException extends AnubisException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5808317853561210737L;
	
	/**
	 * 
	 */
	public AnubisRuntimeException() {
	}
	
	/**
	 * @param message
	 */
	public AnubisRuntimeException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public AnubisRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param cause
	 */
	public AnubisRuntimeException(Throwable cause) {
		super(cause);
	}
	
}
