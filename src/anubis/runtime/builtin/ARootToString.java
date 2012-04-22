package anubis.runtime.builtin;

import anubis.AnubisObject;
import anubis.runtime.ABuiltinFunction._0;
import anubis.runtime.AObjects;
import anubis.runtime.Operator;

public class ARootToString extends _0 {
	public ARootToString(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	protected AnubisObject exec(AnubisObject this1) {
		return AObjects.getString(Operator.toString(this1));
	}
}
