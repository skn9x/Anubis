package anubis.except;

public class IllegalValueException extends AnubisRuntimeException {
	private static final long serialVersionUID = -1005662225273456728L;
	
	protected IllegalValueException() {
		;
	}
	
	protected IllegalValueException(String message) {
		super(message);
	}
	
	protected IllegalValueException(String message, Throwable cause) {
		super(message, cause);
	}
	
	protected IllegalValueException(Throwable cause) {
		super(cause);
	}
	
}
