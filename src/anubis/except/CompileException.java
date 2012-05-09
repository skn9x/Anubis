package anubis.except;

public class CompileException extends AnubisException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -257761622724727404L;
	
	public CompileException() {
	}
	
	public CompileException(String message) {
		super(message);
	}
	
	public CompileException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CompileException(Throwable cause) {
		super(cause);
	}
}
