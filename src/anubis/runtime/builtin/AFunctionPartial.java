package anubis.runtime.builtin;

import anubis.ACallable;
import anubis.AnubisObject;
import anubis.runtime.ABuiltinFunction;
import anubis.runtime.AFunction;
import anubis.runtime.Utils;

public class AFunctionPartial extends ABuiltinFunction {
	public AFunctionPartial(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	public AnubisObject call(AnubisObject _this, AnubisObject... args) {
		ACallable func = Utils.cast(_this, ACallable.class);
		return AFunction.partial(func, args);
	}
	
}
