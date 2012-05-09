package anubis.runtime;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author SiroKuro
 */
public class AInteger extends ANumber {
	private final BigInteger value;
	
	protected AInteger(BigInteger value) {
		assert value != null;
		this.value = value;
	}
	
	@Override
	public Number getNumber() {
		return value;
	}
	
	public BigInteger getValue() {
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
		return op.operate(x, new BigDecimal(value));
	}
	
	@Override
	protected <T> T operate(BinaryOperator<T> op, BigInteger x) {
		return op.operate(x, value);
	}
	
	public static AInteger valueOf(BigInteger value) {
		return new AInteger(value);
	}
	
	public static AInteger valueOf(long value) {
		return valueOf(BigInteger.valueOf(value));
	}
	
}
