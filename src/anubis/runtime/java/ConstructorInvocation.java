package anubis.runtime.java;

import java.lang.reflect.Constructor;
import anubis.AnubisObject;
import anubis.runtime.AObjects;

public class ConstructorInvocation extends Invocation {
	private final Constructor<?> constructor;
	
	public ConstructorInvocation(Constructor<?> constructor) {
		super(constructor.getParameterTypes());
		this.constructor = constructor;
	}
	
	@Override
	public AnubisObject invoke(AnubisObject this1, AnubisObject... args) throws Exception {
		return AObjects.newJObject(constructor.newInstance(convertParams(args)));
	}
}
