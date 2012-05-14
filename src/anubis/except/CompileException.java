package anubis.except;

public class CompileException extends AnubisException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -257761622724727404L;
	
	protected CompileException() {
	}
	
	protected CompileException(String message) {
		super(message);
	}
	
	protected CompileException(String message, Throwable cause) {
		super(message, cause);
	}
	
	protected CompileException(Throwable cause) {
		super(cause);
	}
}
