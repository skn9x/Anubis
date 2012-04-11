package anubis.runtime.builtin;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

public class ANumberPositiveFunction extends AbstractNumberFunction1 {
	
	public ANumberPositiveFunction(AnubisObject owner, String name) {
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
