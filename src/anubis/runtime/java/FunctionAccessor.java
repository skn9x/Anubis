package anubis.runtime.java;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import anubis.AnubisObject;
import anubis.except.AnubisException;
import anubis.except.ExceptionProvider;

public class FunctionAccessor {
	private final String name;
	private final Set<Invocation> invoke = Collections.synchronizedSet(new TreeSet<Invocation>());
	
	public FunctionAccessor(Class<?> declaredClass, String memberName) {
		name = declaredClass.getSimpleName() + "/" + memberName;
	}
	
	public void add(Invocation i) {
		invoke.add(i);
	}
	
	public AnubisObject call(AnubisObject this1, AnubisObject... args) {
		Invocation invoke = select(args);
		if (invoke != null) {
			try {
				return invoke.invoke(this1, args);
			}
			catch (AnubisException ex) {
				throw ex; // 再スロー
			}
			catch (Exception ex) {
				throw ExceptionProvider.wrapRuntimeException(ex);
			}
		}
		else {
			throw ExceptionProvider.newJOverloadMismatch();
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void remove(Invocation i) {
		invoke.remove(i);
	}
	
	/**
	 * @param args
	 * @return
	 */
	private Invocation select(AnubisObject[] args) {
		for (Invocation i: invoke) {
			if (i.match(args)) {
				return i;
			}
		}
		return null;
	}
}
