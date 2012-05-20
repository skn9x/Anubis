package anubis.runtime.traits.func;

import anubis.AnubisObject;
import anubis.runtime.ABuiltinFunction;
import anubis.runtime.ANumber;
import anubis.runtime.AObjects;
import anubis.runtime.Utils;

public abstract class AbstractNumberFunction2 extends ABuiltinFunction._1 implements ANumber.BinaryOperator<Number> {
	
	public AbstractNumberFunction2() {
		super();
	}
	
	public AbstractNumberFunction2(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	protected AnubisObject exec(AnubisObject this1, AnubisObject x) {
		ANumber nx = Utils.cast(ANumber.class, this1);
		ANumber ny = Utils.cast(ANumber.class, x);
		return AObjects.getNumber(nx.operate(this, ny));
	}
}
