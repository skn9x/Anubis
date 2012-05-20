package anubis.runtime.traits.func;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

public class ANumberPositive extends AbstractNumberFunction1 {
	
	public ANumberPositive(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	public Number operate(BigDecimal x) {
		return x;
	}
	
	@Override
	public Number operate(BigInteger x) {
		return x;
	}
	
}
