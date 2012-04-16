package anubis.except;

import anubis.AnubisObject;

public class AnubisUserException extends AnubisRuntimeException {
	private final AnubisObject value;
	
	public AnubisUserException(AnubisObject value) {
		this.value = value;
	}
	
	public AnubisObject getValue() {
		return value;
	}
}
