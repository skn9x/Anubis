package anubis.runtime;

import anubis.AnubisObject;
import anubis.runtime.java.FunctionAccessor;

/**
 * @author SiroKuro
 */
@TypeName(ObjectType.JFUNCTION)
public class JFunction extends AFunction {
	private final FunctionAccessor func;
	private final String funcname;
	
	public JFunction(FunctionAccessor func) {
		assert func != null;
		this.func = func;
		this.funcname = func.getName();
	}
	
	@Override
	public AnubisObject call(AnubisObject this1, AnubisObject... args) {
		return AObjects.getObject(func.call(this1, args));
	}
	
	@Override
	public String debugString() {
		return super.debugString() + "(" + funcname + ")";
	}
	
	@Override
	public String toString() {
		return getType() + "(" + funcname + ")";
	}
	
	public static JFunction valueOf(FunctionAccessor func) {
		return new JFunction(func);
	}
}
