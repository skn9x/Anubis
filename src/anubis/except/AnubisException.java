package anubis.except;

/**
 * AnubisException は、スクリプト実行中にスローされる各種例外のスーパークラスです。
 * @author SiroKuro
 */
public class AnubisException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4260630747050644374L;
	
	protected AnubisException() {
		super();
	}
	
	protected AnubisException(String message) {
		super(message);
	}
	
	protected AnubisException(String message, Throwable cause) {
		super(message, cause);
	}
	
	protected AnubisException(Throwable cause) {
		super(cause);
	}
}
