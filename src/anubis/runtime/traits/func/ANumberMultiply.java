package anubis.runtime.traits.func;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public class ANumberMultiply extends AbstractNumberFunction2 {
	public static final ANumberMultiply INSTANCE = new ANumberMultiply();
	
	public ANumberMultiply() {
		;
	}
	
	/**
	 * @param owner
	 * @param name
	 * @param factory
	 */
	public ANumberMultiply(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	public Number operate(BigDecimal x, BigDecimal y) {
		return x.multiply(y);
	}
	
	@Override
	public Number operate(BigInteger x, BigInteger y) {
		return x.multiply(y);
	}
}
