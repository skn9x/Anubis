package anubis.runtime.builtin;

import anubis.ACallable;
import anubis.AnubisObject;
import anubis.runtime.ABuiltinFunction;
import anubis.runtime.Utils;

public class AFunctionInvokeWith extends ABuiltinFunction {
	
	public AFunctionInvokeWith(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	public AnubisObject call(AnubisObject this1, AnubisObject... args) {
		ACallable func = Utils.cast(ACallable.class, this1);
		return func.call(anubis.runtime.builtin.Utils.car(args), anubis.runtime.builtin.Utils.cdr(args));
	}
}
