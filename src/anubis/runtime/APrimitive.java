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
	
	@Override
	public abstract boolean equals(Object obj);
	
	@Override
	public abstract int hashCode();
	
	protected Object debugValue() {
		return asJava();
	}
}
