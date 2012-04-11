package anubis.runtime;

import anubis.AnubisObject;
import anubis.runtime.java.FunctionAccessor;

/**
 * @author SiroKuro
 */
public class JFunction extends AFunction {
	private final FunctionAccessor func;
	
	public JFunction(FunctionAccessor func) {
		assert func != null;
		this.func = func;
	}
	
	@Override
	public AnubisObject call(AnubisObject this1, AnubisObject... args) {
		return AObjects.getObject(func.call(this1, args));
	}
	
	@Override
	public String getType() {
		return ObjectType.JFUNCTION;
	}
	
	public static JFunction valueOf(FunctionAccessor func) {
		return new JFunction(func);
	}
}
