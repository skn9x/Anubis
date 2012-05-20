package anubis.runtime.traits.func;

import anubis.AnubisObject;
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
		ANumber num = Utils.cast(ANumber.class, this1);
		return AObjects.getNumber(num.operate(this));
	}
}
