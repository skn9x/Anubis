package anubis.runtime.builtin;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public class ANumberTrueDivideFunction extends AbstractNumberFunction2 {
	/**
	 * @param owner
	 * @param name
	 * @param factory
	 */
	public ANumberTrueDivideFunction(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	public Number operate(BigDecimal x, BigDecimal y) {
		return x.divide(y);
	}
	
	@Override
	public Number operate(BigInteger x, BigInteger y) {
		return operate(new BigDecimal(x), new BigDecimal(y));
	}
}
