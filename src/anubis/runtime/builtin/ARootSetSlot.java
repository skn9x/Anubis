package anubis.runtime.builtin;

import anubis.AnubisObject;
import anubis.SlotRef;
import anubis.except.ExceptionProvider;
import anubis.runtime.ABuiltinFunction._3;
import anubis.runtime.AString;
import anubis.runtime.Operator;
import anubis.runtime.Utils;

public class ARootSetSlot extends _3 {
	public ARootSetSlot(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	protected AnubisObject exec(AnubisObject _this, AnubisObject name, AnubisObject value, AnubisObject opname) {
		String op = opname == null ? null : Utils.as(AString.class, opname).getValue();
		String slotName = Utils.as(AString.class, name).getValue();
		
		SlotRef ref = _this.findSlotRef(slotName);
		if (ref != null) {
			if (op != null) {
				AnubisObject left = ref.get();
				value = Operator.opCall(left, op, left, value);
			}
			ref.set(value);
		}
		else {
			if (op != null) {
				throw ExceptionProvider.newSlotNotFound(_this, slotName);
			}
			_this.setSlot(slotName, value);
		}
		return value;
	}
}
