package anubis.except;

public class IllegalValueException extends AnubisRuntimeException {
	private static final long serialVersionUID = -1005662225273456728L;
	
	public IllegalValueException() {
		;
	}
	
	public IllegalValueException(String message) {
		super(message);
	}
	
	public IllegalValueException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public IllegalValueException(Throwable cause) {
		super(cause);
	}
	
}
