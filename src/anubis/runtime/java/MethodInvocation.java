package anubis.runtime.java;

import java.lang.reflect.Method;
import anubis.AnubisObject;
import anubis.runtime.AObjects;

public class MethodInvocation extends Invocation {
	private final Method method;
	
	public MethodInvocation(Method method) {
		super(method.getParameterTypes());
		this.method = method;
	}
	
	@Override
	public AnubisObject invoke(AnubisObject this1, AnubisObject... args) throws Exception {
		return AObjects.getObject(method.invoke(JCaster.cast(method.getDeclaringClass(), this1), convertParams(args)));
	}
}
