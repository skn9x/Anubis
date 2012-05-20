package anubis.runtime.traits.func;

import anubis.ACallable;
import anubis.AnubisObject;
import anubis.runtime.ABuiltinFunction;
import anubis.runtime.Utils;

public class AFunctionInvoke extends ABuiltinFunction {
	
	public AFunctionInvoke(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	public AnubisObject call(AnubisObject this1, AnubisObject... args) {
		ACallable func = Utils.cast(ACallable.class, this1);
		return func.call(null, args);
	}
}
