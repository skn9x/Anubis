package anubis.runtime;

import anubis.ACastable;

/**
 * @author SiroKuro
 */
public abstract class APrimitive extends AObject implements ACastable {
	@Override
	public String debugString() {
		return super.debugString() + "(" + asJava() + ")"; // TODO asJava の戻り値を一定長で切り詰める
	}
}
