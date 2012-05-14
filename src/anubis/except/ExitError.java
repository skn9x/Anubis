package anubis.except;

import anubis.AnubisObject;

public class ExitError extends Error {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8310610255427215079L;
	
	private final AnubisObject value;
	
	public ExitError() {
		this.value = null;
	}
	
	public ExitError(AnubisObject value) {
		this.value = value;
	}
	
	public AnubisObject getValue() {
		return value;
	}
}
