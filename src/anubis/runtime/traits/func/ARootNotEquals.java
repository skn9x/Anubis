package anubis.runtime.traits.func;

import anubis.AnubisObject;
import anubis.runtime.ABuiltinFunction;
import anubis.runtime.AObjects;

/**
 * @author SiroKuro
 */
public class ARootNotEquals extends ABuiltinFunction._1 {
	/**
	 * @param owner
	 * @param name
	 * @param factory
	 */
	public ARootNotEquals(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	protected AnubisObject exec(AnubisObject this1, AnubisObject x) {
		boolean eq = this1 == null ? x == null : this1.equals(x);
		return !eq ? AObjects.getTrue() : AObjects.getFalse();
	}
}
