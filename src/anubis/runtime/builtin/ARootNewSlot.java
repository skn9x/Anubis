package anubis.runtime.builtin;

import anubis.AnubisObject;
import anubis.runtime.ABuiltinFunction._3;
import anubis.runtime.AString;
import anubis.runtime.Operator;
import anubis.runtime.Utils;

public class ARootNewSlot extends _3 {
	public ARootNewSlot(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	protected AnubisObject exec(AnubisObject _this, AnubisObject name, AnubisObject value, AnubisObject readonly) {
		AString text = Utils.as(AString.class, name);
		_this.setSlot(text.getValue(), value, Operator.isTrue(readonly));
		return value;
	}
}
