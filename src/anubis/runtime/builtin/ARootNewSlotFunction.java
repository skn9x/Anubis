package anubis.runtime.builtin;

import anubis.AnubisObject;
import anubis.runtime.ABuiltinFunction._3;
import anubis.runtime.AString;
import anubis.runtime.Operator;
import anubis.runtime.Utils;

public class ARootNewSlotFunction extends _3 {
	public ARootNewSlotFunction(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	protected AnubisObject exec(AnubisObject _this, AnubisObject name, AnubisObject value, AnubisObject readonly) {
		AString text = Utils.cast(name, AString.class);
		_this.setSlot(text.getValue(), value, Operator.isTrue(readonly));
		return value;
	}
}
