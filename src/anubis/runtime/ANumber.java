package anubis.runtime;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author SiroKuro
 */
public abstract class ANumber extends APrimitive {
	@Override
	public Object asJava() {
		return getNumber();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ANumber))
			return false;
		return operate(new BinaryOperator<Boolean>() {
			@Override
			public Boolean operate(BigDecimal x, BigDecimal y) {
				return x.compareTo(y) == 0;
			}
			
			@Override
			public Boolean operate(BigInteger x, BigInteger y) {
				return x.compareTo(y) == 0;
			}
		}, (ANumber) obj);
	}
	
	public abstract Number getNumber();
	
	@Override
	public String getType() {
		return ObjectType.NUMBER;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getNumber().hashCode();
		return result;
	}
	
	public abstract <T> T operate(BinaryOperator<T> op, ANumber y);
	
	public abstract <T> T operate(UnaryOperator<T> op);
	
	@Override
	public String toString() {
		return getNumber().toString();
	}
	
	protected abstract <T> T operate(BinaryOperator<T> op, BigDecimal x);
	
	protected abstract <T> T operate(BinaryOperator<T> op, BigInteger x);
	
	public static interface BinaryOperator<T> {
		public T operate(BigDecimal x, BigDecimal y);
		
		public T operate(BigInteger x, BigInteger y);
	}
	
	public static interface UnaryOperator<T> {
		public T operate(BigDecimal x);
		
		public T operate(BigInteger x);
	}
	
}
