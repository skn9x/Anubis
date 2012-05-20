package anubis.runtime.traits;

import anubis.runtime.ATrait;
import anubis.runtime.TraitsFactory;
import anubis.runtime.traits.func.AFunctionInvoke;
import anubis.runtime.traits.func.AFunctionInvokeWith;
import anubis.runtime.traits.func.AFunctionPartial;
import anubis.runtime.traits.func.AFunctionWith;

public class AListTrait extends ATrait {
	public AListTrait() {
		super("FunctionTrait");
	}
	
	@Override
	protected void initSlots(TraitsFactory factory) {
		factory.attach(this);
		factory.attach(new AFunctionInvoke(this, "invoke"));
		factory.attach(new AFunctionInvokeWith(this, "invokeWith"));
		factory.attach(new AFunctionPartial(this, "partial"));
		factory.attach(new AFunctionWith(this, "with"));
	}
}
