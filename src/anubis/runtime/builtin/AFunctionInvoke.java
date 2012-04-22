package anubis.runtime.builtin;

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
		ACallable func = Utils.cast(this1, ACallable.class);
		return func.call(null, args);
	}
}
