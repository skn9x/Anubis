package anubis.runtime.builtin;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public class ANumberPower extends AbstractNumberFunction2 {
	/**
	 * @param owner
	 * @param name
	 * @param factory
	 */
	public ANumberPower(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	public Number operate(BigDecimal x, BigDecimal y) {
		return x.pow(y.intValue());
	}
	
	@Override
	public Number operate(BigInteger x, BigInteger y) {
		return x.pow(y.intValue());
	}
}
