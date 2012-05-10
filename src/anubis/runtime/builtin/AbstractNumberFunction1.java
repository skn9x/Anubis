package anubis.runtime.builtin;

import anubis.AnubisObject;
import anubis.except.ExceptionProvider;
import anubis.runtime.ABuiltinFunction;
import anubis.runtime.ANumber;
import anubis.runtime.AObjects;
import anubis.runtime.Utils;

public abstract class AbstractNumberFunction1 extends ABuiltinFunction._0 implements ANumber.UnaryOperator<Number> {
	public AbstractNumberFunction1() {
		super();
	}
	
	public AbstractNumberFunction1(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	protected AnubisObject exec(AnubisObject this1) {
		ANumber num = Utils.as(ANumber.class, this1);
		if (num == null)
			throw ExceptionProvider.newVoidOperation(); // TODO IllegalArgumentException
		return AObjects.getNumber(num.operate(this));
	}
}
