package anubis.runtime.traits.func;

import anubis.AnubisObject;
import anubis.runtime.ABuiltinFunction._2;
import anubis.runtime.AString;
import anubis.runtime.Utils;

public class ARootNewSlot extends _2 {
	public ARootNewSlot(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	protected AnubisObject exec(AnubisObject _this, AnubisObject name, AnubisObject value) {
		AString text = Utils.cast(AString.class, name);
		_this.setSlot(text.getValue(), value);
		return value;
	}
}
