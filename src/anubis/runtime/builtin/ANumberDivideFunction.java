package anubis.runtime.builtin;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public class ANumberDivideFunction extends AbstractNumberFunction2 {
	public static final ANumberDivideFunction INSTANCE = new ANumberDivideFunction();
	
	/**
	 * @param owner
	 * @param name
	 * @param factory
	 */
	public ANumberDivideFunction(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	private ANumberDivideFunction() {
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
