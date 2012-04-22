package anubis.runtime.builtin;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public class ANumberLessThan extends AbstractNumberFunction2 {
	public static final ANumberLessThan INSTANCE = new ANumberLessThan();
	
	/**
	 * @param owner
	 * @param name
	 * @param factory
	 */
	public ANumberLessThan(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	private ANumberLessThan() {
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
