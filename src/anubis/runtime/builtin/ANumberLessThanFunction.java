package anubis.runtime.builtin;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public class ANumberLessThanFunction extends AbstractNumberFunction2 {
	public static final ANumberLessThanFunction INSTANCE = new ANumberLessThanFunction();
	
	/**
	 * @param owner
	 * @param name
	 * @param factory
	 */
	public ANumberLessThanFunction(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	private ANumberLessThanFunction() {
		super();
	}
	
	@Override
	public Number operate(BigDecimal x, BigDecimal y) {
		return x.compareTo(y) < 0 ? y : null;
	}
	
	@Override
	public Number operate(BigInteger x, BigInteger y) {
		return x.compareTo(y) < 0 ? y : null;
	}
}
