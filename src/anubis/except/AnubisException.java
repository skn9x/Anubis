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
	
	public AnubisException() {
		super();
	}
	
	public AnubisException(String message) {
		super(message);
	}
	
	public AnubisException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AnubisException(Throwable cause) {
		super(cause);
	}
}
