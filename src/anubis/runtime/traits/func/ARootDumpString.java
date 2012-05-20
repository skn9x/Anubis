package anubis.runtime.traits.func;

import anubis.AnubisObject;
import anubis.runtime.ABuiltinFunction._0;
import anubis.runtime.AObjects;
import anubis.runtime.Operator;

public class ARootDumpString extends _0 {
	public ARootDumpString(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	protected AnubisObject exec(AnubisObject this1) {
		return AObjects.getString(Operator.toDumpString(this1));
	}
}
