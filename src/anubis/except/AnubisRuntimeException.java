package anubis.except;

/**
 * AnubisRuntimeException は、スクリプト自体が原因となってスローされる各種例外のスーパークラスです。
 * @author SiroKuro
 */
public class AnubisRuntimeException extends AnubisException {
	
	/**
	 * 
	 */
	public AnubisRuntimeException() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param arg0
	 */
	public AnubisRuntimeException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param arg0
	 * @param arg1
	 */
	public AnubisRuntimeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param arg0
	 */
	public AnubisRuntimeException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
}
