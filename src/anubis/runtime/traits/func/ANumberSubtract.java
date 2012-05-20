package anubis.runtime.traits.func;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public class ANumberSubtract extends AbstractNumberFunction2 {
	public static final ANumberSubtract INSTANCE = new ANumberSubtract();
	
	/**
	 * @param owner
	 * @param name
	 * @param factory
	 */
	public ANumberSubtract(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	private ANumberSubtract() {
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
