package anubis.runtime.traits.func;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public class ANumberGreaterThan extends AbstractNumberFunction2 {
	public static final ANumberGreaterThan INSTANCE = new ANumberGreaterThan();
	
	/**
	 * @param owner
	 * @param name
	 * @param factory
	 */
	public ANumberGreaterThan(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	private ANumberGreaterThan() {
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
