package anubis.runtime.traits;

import anubis.runtime.ATrait;
import anubis.runtime.TraitsFactory;
import anubis.runtime.traits.func.ANumberAdd;
import anubis.runtime.traits.func.ANumberDivide;
import anubis.runtime.traits.func.ANumberGreaterThan;
import anubis.runtime.traits.func.ANumberGreaterThanEquals;
import anubis.runtime.traits.func.ANumberLessThan;
import anubis.runtime.traits.func.ANumberLessThanEquals;
import anubis.runtime.traits.func.ANumberMultiply;
import anubis.runtime.traits.func.ANumberNegative;
import anubis.runtime.traits.func.ANumberPositive;
import anubis.runtime.traits.func.ANumberPower;
import anubis.runtime.traits.func.ANumberRemainder;
import anubis.runtime.traits.func.ANumberSubtract;
import anubis.runtime.traits.func.ANumberTrueDivide;

public class ANumberTrait extends ATrait {
	public ANumberTrait() {
		super("NumberTrait");
	}
	
	@Override
	protected void initSlots(TraitsFactory factory) {
		factory.attach(this);
		factory.attach(new ANumberPositive(this, "+p"));
		factory.attach(new ANumberNegative(this, "-n"));
		factory.attach(new ANumberAdd(this, "+"));
		factory.attach(new ANumberSubtract(this, "-"));
		factory.attach(new ANumberMultiply(this, "*"));
		factory.attach(new ANumberDivide(this, "/"));
		factory.attach(new ANumberTrueDivide(this, "\\"));
		factory.attach(new ANumberRemainder(this, "%"));
		factory.attach(new ANumberPower(this, "**"));
		factory.attach(new ANumberGreaterThan(this, ">"));
		factory.attach(new ANumberGreaterThanEquals(this, ">="));
		factory.attach(new ANumberLessThan(this, "<"));
		factory.attach(new ANumberLessThanEquals(this, "<="));
	}
}
