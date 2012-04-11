package anubis.runtime.builtin;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public class ANumberSubtractFunction extends AbstractNumberFunction2 {
	public static final ANumberSubtractFunction INSTANCE = new ANumberSubtractFunction();
	
	/**
	 * @param owner
	 * @param name
	 * @param factory
	 */
	public ANumberSubtractFunction(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	private ANumberSubtractFunction() {
		;
	}
	
	@Override
	public Number operate(BigDecimal x, BigDecimal y) {
		return x.subtract(y);
	}
	
	@Override
	public Number operate(BigInteger x, BigInteger y) {
		return x.subtract(y);
	}
}
