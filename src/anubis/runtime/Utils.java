package anubis.runtime;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import anubis.AnubisObject;
import anubis.except.ExceptionProvider;

/**
 * @author SiroKuro
 */
public class Utils {
	private static final ProtoVisitor<Class<? extends AnubisObject>, AnubisObject> CAST = new ProtoVisitor<Class<? extends AnubisObject>, AnubisObject>() {
		@Override
		protected AnubisObject visit(AnubisObject obj, Class<? extends AnubisObject> cls) {
			if (cls.isInstance(obj))
				return obj;
			return null;
		}
	};
	
	public static Number asBigNumber(Number num) {
		if (num == null)
			return null;
		if (num instanceof BigInteger || num instanceof BigDecimal)
			return num;
		if (num instanceof Integer || num instanceof Long)
			return BigInteger.valueOf(num.longValue());
		if (num instanceof Double || num instanceof Float)
			return BigDecimal.valueOf(num.doubleValue());
		if (num instanceof AtomicInteger || num instanceof AtomicLong)
			return BigInteger.valueOf(num.longValue());
		throw new IllegalArgumentException();
	}
	
	public static int asIntArgs(AnubisObject obj) {
		ANumber num = Utils.cast(obj, ANumber.class);
		if (num == null)
			throw ExceptionProvider.newVoidOperation(); // TODO IllegalArgumentException
		// TODO オーバーフローチェックをいれる
		return num.getNumber().intValue();
	}
	
	public static int asIntArgs(AnubisObject obj, int _default) {
		ANumber num = Utils.cast(obj, ANumber.class);
		if (num == null)
			return _default;
		// TODO オーバーフローチェックをいれる
		return num.getNumber().intValue();
	}
	
	public static <T extends AnubisObject> T cast(AnubisObject obj, Class<T> cls) { // TODO null を返したときの対処を入れる
		return cls.cast(CAST.start(obj, cls));
	}
	
	public static String getIdString(Object obj) {
		return Integer.toHexString(System.identityHashCode(obj));
	}
}
