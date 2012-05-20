package anubis.runtime.traits.func;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public class ANumberAdd extends AbstractNumberFunction2 {
	public static final ANumberAdd INSTANCE = new ANumberAdd();
	
	/**
	 * @param owner
	 * @param name
	 * @param factory
	 */
	public ANumberAdd(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	private ANumberAdd() {
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
