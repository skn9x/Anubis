package anubis.runtime.java;

import java.lang.reflect.AccessibleObject;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import anubis.AnubisObject;
import anubis.except.ExceptionProvider;

public abstract class Invocation implements Comparable<Invocation> {
	/**
	 * パラメータ型の配列
	 */
	private final Class<?>[] argTypes;
	/**
	 * 引数スコア(比較用)
	 */
	private final String argsScore;
	
	private static final Class<?>[] argsOrder = {
		AnubisObject.class,
		BigDecimal.class,
		Double.TYPE,
		Double.class,
		Float.TYPE,
		Float.class,
		BigInteger.class,
		Long.TYPE,
		Long.class,
		Integer.TYPE,
		Integer.class,
		Short.TYPE,
		Short.class,
		Byte.TYPE,
		Byte.class,
		String.class,
		CharSequence.class,
		Boolean.TYPE,
		Boolean.class,
	};
	
	public Invocation(Class<?>... argTypes) {
		this.argTypes = argTypes.clone();
		this.argsScore = getArgsScore(argTypes);
		assert this.argTypes != null;
		assert this.argsScore != null;
	}
	
	@Override
	public int compareTo(Invocation o) {
		return InvocationComparator.INSTANCE.compare(this, o);
	}
	
	/**
	 * @param args
	 * @return
	 */
	protected Object[] convertParams(AnubisObject[] args) {
		try {
			return JCaster.cast(argTypes, args);
		}
		catch (ClassCastException ex) {
			throw ExceptionProvider.newJOverloadMismatch(ex);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invocation other = (Invocation) obj;
		if (!Arrays.equals(argTypes, other.argTypes))
			return false;
		return true;
	}
	
	public int getArgLength() {
		return argTypes.length;
	}
	
	public String getArgsScore() {
		return argsScore;
	}
	
	public List<Class<?>> getArgTypes() {
		return Collections.unmodifiableList(Arrays.asList(argTypes));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + argsScore.hashCode();
		return result;
	}
	
	public abstract AnubisObject invoke(AnubisObject this1, AnubisObject... args) throws Exception;
	
	/**
	 * @param args
	 * @return
	 */
	public boolean match(AnubisObject... args) {
		return JCaster.match(argTypes, args);
	}
	
	protected void setAccessible(AccessibleObject ao) {
		ao.setAccessible(true);
	}
	
	private static String getArgsScore(Class<?>... classes) {
		StringBuilder sb = new StringBuilder();
		for (Class<?> cls: classes) {
			sb.append(getScore(cls));
		}
		return sb.toString();
	}
	
	private static char getScore(Class<?> cls) {
		int score = 0;
		while (score < argsOrder.length) {
			if (argsOrder[score].isAssignableFrom(cls)) {
				break;
			}
			score++;
		}
		return Character.forDigit(score, Character.MAX_RADIX);
	}
}
