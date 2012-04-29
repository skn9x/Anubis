package anubis.runtime;

import anubis.ACastable;

/**
 * @author SiroKuro
 */
public abstract class APrimitive extends AObject implements ACastable {
	@Override
	public String debugString() {
		return super.debugString() + "(" + debugValue() + ")";
	}
	
	protected Object debugValue() {
		return asJava();
	}
}
