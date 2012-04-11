package anubis.runtime.builtin;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public class ANumberGreaterThanFunction extends AbstractNumberFunction2 {
	public static final ANumberGreaterThanFunction INSTANCE = new ANumberGreaterThanFunction();
	
	/**
	 * @param owner
	 * @param name
	 * @param factory
	 */
	public ANumberGreaterThanFunction(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	private ANumberGreaterThanFunction() {
		super();
	}
	
	@Override
	public Number operate(BigDecimal x, BigDecimal y) {
		return x.compareTo(y) > 0 ? y : null;
	}
	
	@Override
	public Number operate(BigInteger x, BigInteger y) {
		return x.compareTo(y) > 0 ? y : null;
	}
}
