package anubis.except;

/**
 * AnubisException は、スクリプト実行中にスローされる各種例外のスーパークラスです。
 * @author SiroKuro
 */
public class AnubisException extends RuntimeException {
	
	public AnubisException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AnubisException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	public AnubisException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	
	public AnubisException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
