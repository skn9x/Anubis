package anubis.runtime;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public class ADecimal extends ANumber {
	public final BigDecimal value;
	
	protected ADecimal(BigDecimal value) {
		assert value != null;
		this.value = value;
	}
	
	@Override
	public Number getNumber() {
		return value;
	}
	
	public BigDecimal getValue() {
		return value;
	}
	
	@Override
	public <T> T operate(BinaryOperator<T> op, ANumber y) {
		return y.operate(op, value);
	}
	
	@Override
	public <T> T operate(UnaryOperator<T> op) {
		return op.operate(value);
	}
	
	@Override
	protected <T> T operate(BinaryOperator<T> op, BigDecimal x) {
		return op.operate(x, value);
	}
	
	@Override
	protected <T> T operate(BinaryOperator<T> op, BigInteger x) {
		return op.operate(new BigDecimal(x), value);
	}
	
	public static ADecimal valueOf(BigDecimal value) {
		return new ADecimal(value);
	}
	
	/**
	 * @param d
	 * @return
	 */
	public static AnubisObject valueOf(double d) {
		return valueOf(BigDecimal.valueOf(d));
	}
}
