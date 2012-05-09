package anubis.except;

import anubis.AnubisObject;

public class AnubisExitError extends Error {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8310610255427215079L;
	
	private final AnubisObject value;
	
	public AnubisExitError() {
		this.value = null;
	}
	
	public AnubisExitError(AnubisObject value) {
		this.value = value;
	}
	
	public AnubisObject getValue() {
		return value;
	}
}
