package anubis.runtime.builtin;

import anubis.AnubisObject;
import anubis.SlotRef;
import anubis.except.ExceptionProvider;
import anubis.runtime.ABuiltinFunction._3;
import anubis.runtime.AString;
import anubis.runtime.Operator;
import anubis.runtime.Utils;

public class ARootSetSlotFunction extends _3 {
	public ARootSetSlotFunction(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	protected AnubisObject exec(AnubisObject _this, AnubisObject name, AnubisObject value, AnubisObject opname) {
		String op = opname == null ? null : Utils.cast(opname, AString.class).getValue();
		String slotName = Utils.cast(name, AString.class).getValue();
		
		SlotRef ref = _this.findSlotRef(slotName);
		if (ref != null) {
			if (op != null) {
				AnubisObject left = ref.get();
				value = Operator.findFunction(left, op).call(left, value);
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
