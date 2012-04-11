package anubis.runtime.builtin;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public class ANumberAddFunction extends AbstractNumberFunction2 {
	public static final ANumberAddFunction INSTANCE = new ANumberAddFunction();
	
	/**
	 * @param owner
	 * @param name
	 * @param factory
	 */
	public ANumberAddFunction(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	private ANumberAddFunction() {
		super();
	}
	
	@Override
	public Number operate(BigDecimal x, BigDecimal y) {
		return x.add(y);
	}
	
	@Override
	public Number operate(BigInteger x, BigInteger y) {
		return x.add(y);
	}
}
