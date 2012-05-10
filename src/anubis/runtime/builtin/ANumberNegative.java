package anubis.runtime.builtin;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

public class ANumberNegative extends AbstractNumberFunction1 {
	public static final ANumberNegative INSTANCE = new ANumberNegative();
	
	public ANumberNegative() {
		super();
	}
	
	public ANumberNegative(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	public Number operate(BigDecimal x) {
		return x.negate();
	}
	
	@Override
	public Number operate(BigInteger x) {
		return x.negate();
	}
	
}
