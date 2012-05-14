package anubis.except;

import anubis.AnubisObject;

public class UserException extends AnubisRuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6083809228609101657L;
	private final AnubisObject value;
	
	protected UserException(AnubisObject value) {
		this.value = value;
	}
	
	public AnubisObject getValue() {
		return value;
	}
}
