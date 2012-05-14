package anubis.runtime.java;

import java.math.BigDecimal;
import java.math.BigInteger;
import anubis.ACastable;
import anubis.AFalse;
import anubis.AnubisObject;
import anubis.except.ExceptionProvider;
import anubis.runtime.ADecimal;
import anubis.runtime.AInteger;
import anubis.runtime.ProtoVisitor;

/**
 * @author SiroKuro
 */
public class JCaster {
	private static final Object NULL = new Object();
	
	private static final ProtoVisitor<Object, Object> SIMPLECAST = new ProtoVisitor<Object, Object>() {
		@Override
		protected Object visit(AnubisObject object, Object x) {
			if (object instanceof ACastable) {
				Object value = ((ACastable) object).asJava();
				if (value == null)
					return NULL;
				return value;
			}
			return null;
		}
	};
	private static final ProtoVisitor<Class<?>, Object> DEEPCAST = new ProtoVisitor<Class<?>, Object>() {
		@Override
		protected Object visit(AnubisObject object, Class<?> cls) {
			if (object == null) {
				if (!cls.isPrimitive()) {
					return NULL;
				}
			}
			if (cls.isInstance(object)) {
				return object;
			}
			if (cls == Boolean.TYPE || cls == Boolean.class) {
				return !(object instanceof AFalse);
			}
			if (object instanceof AInteger) {
				BigInteger value = ((AInteger) object).getValue();
				if (cls == Byte.TYPE || cls == Byte.class)
					return value.byteValue();
				if (cls == Short.TYPE || cls == Short.class)
					return value.shortValue();
				if (cls == Integer.TYPE || cls == Integer.class)
					return value.intValue();
				if (cls == Long.TYPE || cls == Long.class)
					return value.longValue();
				if (cls == Float.TYPE || cls == Float.class)
					return value.floatValue();
				if (cls == Double.TYPE || cls == Double.class)
					return value.doubleValue();
				if (cls == BigInteger.class)
					return value;
				if (cls == BigDecimal.class)
					return new BigDecimal(value);
			}
			if (object instanceof ADecimal) {
				BigDecimal value = ((ADecimal) object).getValue();
				if (cls == Float.TYPE || cls == Float.class)
					return value.floatValue();
				if (cls == Double.TYPE || cls == Double.class)
					return value.doubleValue();
				if (cls == BigDecimal.class)
					return value;
			}
			if (object instanceof ACastable) {
				Object value = ((ACastable) object).asJava();
				if (value == null)
					return NULL;
				if (cls.isInstance(value))
					return value;
			}
			return null;
		}
	};
	private static final ProtoVisitor<Class<?>, Boolean> DEEPMATCH = new ProtoVisitor<Class<?>, Boolean>() {
		@Override
		protected Boolean visit(AnubisObject object, Class<?> cls) {
			if (object == null) {
				if (!cls.isPrimitive()) {
					return true;
				}
			}
			if (cls.isInstance(object)) {
				return true;
			}
			if (cls == Boolean.TYPE || cls == Boolean.class) {
				return true;
			}
			if (object instanceof AInteger) {
				if (cls == Byte.TYPE || cls == Byte.class)
					return true;
				if (cls == Short.TYPE || cls == Short.class)
					return true;
				if (cls == Integer.TYPE || cls == Integer.class)
					return true;
				if (cls == Long.TYPE || cls == Long.class)
					return true;
				if (cls == Float.TYPE || cls == Float.class)
					return true;
				if (cls == Double.TYPE || cls == Double.class)
					return true;
				if (cls == BigInteger.class)
					return true;
				if (cls == BigDecimal.class)
					return true;
			}
			if (object instanceof ADecimal) {
				if (cls == Float.TYPE || cls == Float.class)
					return true;
				if (cls == Double.TYPE || cls == Double.class)
					return true;
				if (cls == BigDecimal.class)
					return true;
			}
			if (object instanceof ACastable) {
				Object value = ((ACastable) object).asJava();
				if (cls.isInstance(value)) {
					return true;
				}
			}
			return null;
		}
	};
	
	public static Object as(Class<?> cls, AnubisObject arg) {
		Object value = DEEPCAST.start(arg, cls);
		if (value == NULL)
			return null;
		if (value != null)
			return toWrappedClass(cls).cast(value);
		return null;
	}
	
	public static Object cast(AnubisObject obj) {
		Object value = SIMPLECAST.start(obj, null);
		if (value == NULL)
			return null;
		if (value != null)
			return value;
		return obj;
	}
	
	public static Object cast(Class<?> cls, AnubisObject obj) { // プリミティブ型を考慮するので return-type は Object にする
		if (cls == null) {
			return cast(obj);
		}
		else {
			Object value = DEEPCAST.start(obj, cls);
			if (value == NULL)
				return null;
			if (value != null)
				return toWrappedClass(cls).cast(value);
			throw ExceptionProvider.newClassCastException(cls, obj);
		}
	}
	
	public static Object[] cast(Class<?>[] cls, AnubisObject[] args) {
		Object[] result = new Object[cls.length];
		int max = Math.min(cls.length, args.length);
		for (int i = 0; i < max; i++) {
			result[i] = cast(cls[i], args[i]);
		}
		return result;
	}
	
	public static boolean match(Class<?> cls, AnubisObject arg) {
		Boolean result = DEEPMATCH.start(arg, cls);
		if (result == null)
			return false;
		else
			return result;
	}
	
	public static boolean match(Class<?>[] cls, AnubisObject[] args) {
		if (args.length != cls.length) {
			return false;
		}
		for (int i = 0; i < args.length; i++) {
			if (!match(cls[i], args[i])) {
				return false;
			}
		}
		return true;
	}
	
	private static Class<?> toWrappedClass(Class<?> cls) {
		if (cls.isPrimitive()) {
			if (cls == Byte.TYPE)
				return Byte.class;
			if (cls == Short.TYPE)
				return Short.class;
			if (cls == Character.TYPE)
				return Character.class;
			if (cls == Integer.TYPE)
				return Integer.class;
			if (cls == Long.TYPE)
				return Long.class;
			if (cls == Float.TYPE)
				return Float.class;
			if (cls == Double.TYPE)
				return Double.class;
		}
		return cls;
	}
}
