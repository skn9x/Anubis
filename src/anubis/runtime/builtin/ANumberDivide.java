package anubis.runtime.builtin;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public class ANumberDivide extends AbstractNumberFunction2 {
	public static final ANumberDivide INSTANCE = new ANumberDivide();
	
	/**
	 * @param owner
	 * @param name
	 * @param factory
	 */
	public ANumberDivide(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	private ANumberDivide() {
		;
	}
	
	@Override
	public Number operate(BigDecimal x, BigDecimal y) {
		return x.divide(y);
	}
	
	@Override
	public Number operate(BigInteger x, BigInteger y) {
		return x.divide(y);
	}
	
}
