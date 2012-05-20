package anubis.runtime.traits.func;

import anubis.ACallable;
import anubis.AnubisObject;
import anubis.runtime.ABuiltinFunction;
import anubis.runtime.AFunction;
import anubis.runtime.Utils;

public class AFunctionWith extends ABuiltinFunction {
	public AFunctionWith(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	public AnubisObject call(AnubisObject _this, AnubisObject... args) {
		ACallable func = Utils.cast(ACallable.class, _this);
		return AFunction.with(func, args == null || args.length == 0 ? null : args[0]);
	}
	
}
