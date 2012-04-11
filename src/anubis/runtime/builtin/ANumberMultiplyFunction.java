package anubis.runtime.builtin;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public class ANumberMultiplyFunction extends AbstractNumberFunction2 {
	public static final ANumberMultiplyFunction INSTANCE = new ANumberMultiplyFunction();
	
	public ANumberMultiplyFunction() {
		;
	}
	
	/**
	 * @param owner
	 * @param name
	 * @param factory
	 */
	public ANumberMultiplyFunction(AnubisObject owner, String name) {
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
