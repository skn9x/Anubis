package anubis.runtime.traits.func;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

public class ANumberIsMinus extends AbstractNumberFunction1 {
	public static final ANumberIsMinus INSTANCE = new ANumberIsMinus();
	
	public ANumberIsMinus() {
		super();
	}
	
	public ANumberIsMinus(AnubisObject owner, String name) {
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
