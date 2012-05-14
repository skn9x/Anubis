package anubis.runtime.builtin;

import anubis.AnubisObject;
import anubis.SpecialSlot;
import anubis.runtime.ABuiltinFunction._2;
import anubis.runtime.AString;
import anubis.runtime.Operator;
import anubis.runtime.Utils;

public class ARootSetSpecialSlot extends _2 {
	private final SpecialSlot slotName;
	
	public ARootSetSpecialSlot(AnubisObject owner, String name, SpecialSlot slotName) {
		super(owner, name);
		this.slotName = slotName;
	}
	
	@Override
	protected AnubisObject exec(AnubisObject _this, AnubisObject value, AnubisObject opname) {
		String op = opname == null ? null : Utils.cast(AString.class, opname).getValue();
		
		if (op != null) {
			AnubisObject left = _this.getSlot(slotName);
			value = Operator.opCall(left, op, left, value);
		}
		_this.setSlot(slotName, value);
		return value;
	}
}
