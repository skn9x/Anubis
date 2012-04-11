package anubis.runtime.builtin;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

public class ANumberIsMinusFunction extends AbstractNumberFunction1 {
	public static final ANumberIsMinusFunction INSTANCE = new ANumberIsMinusFunction();
	
	public ANumberIsMinusFunction() {
		super();
	}
	
	public ANumberIsMinusFunction(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	public Number operate(BigDecimal x) {
		return x.signum() < 0 ? x : null;
	}
	
	@Override
	public Number operate(BigInteger x) {
		return x.signum() < 0 ? x : null;
	}
	
}
