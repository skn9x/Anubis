package anubis.runtime;

import java.util.Iterator;
import anubis.ACallable;
import anubis.ADumpable;
import anubis.AFalse;
import anubis.AIndexable;
import anubis.AIterable;
import anubis.ANull;
import anubis.ASliceable;
import anubis.AnubisObject;
import anubis.except.ExceptionProvider;
import anubis.except.UserException;
import anubis.runtime.java.IteratorAdapter;
import anubis.runtime.java.JCaster;

/**
 * @author SiroKuro
 */
public class Operator {
	public static final String SLOT_FORWARD = "$forward";
	
	public static void assertNotVoid(AnubisObject obj) {
		if (obj == null) {
			throw ExceptionProvider.newVoidOperation();
		}
	}
	
	public static void fail() {
		throw ExceptionProvider.newAssertionException();
	}
	
	/**
	 * obj のスロットを参照し、関数を返却します。スロットが見つからない場合 $forward を考慮します。
	 * それでも見つからない場合には SlotNotFoundException がスローされます。
	 * @param obj
	 * @param name
	 * @return
	 */
	public static ACallable findFunction(AnubisObject obj, String name) {
		AnubisObject val = findSlot(obj, name);
		if (val != null) {
			ACallable result = Utils.as(ACallable.class, val);
			if (result != null)
				return result;
			else
				throw ExceptionProvider.newNotCallable(val);
		}
		val = findSlot(obj, SLOT_FORWARD);
		if (val != null) {
			ACallable result = Utils.as(ACallable.class, val);
			if (result != null)
				return AFunction.partial(result, new AnubisObject[]{
					AObjects.getString(name)
				});
			else
				throw ExceptionProvider.newNotCallable(val);
		}
		throw ExceptionProvider.newSlotNotFound(obj, name);
	}
	
	/**
	 * obj のスロットを参照します。それでも見つからない場合には null が返ります。
	 * @param obj
	 * @param name
	 * @return
	 */
	public static AnubisObject findSlot(AnubisObject obj, String name) {
		if (name == null)
			throw new NullPointerException();
		if (obj == null)
			throw ExceptionProvider.newSlotNotFound(obj, name);
		return obj.findSlot(name);
	}
	
	public static AnubisObject getBool(boolean bool) {
		return bool ? AObjects.getTrue() : AObjects.getFalse();
	}
	
	public static Iterator<AnubisObject> getIterator(AnubisObject obj) {
		AIterable aiter = Utils.as(AIterable.class, obj);
		if (aiter != null) {
			return aiter.getAIterator();
		}
		Iterable<?> iter = (Iterable<?>) JCaster.as(Iterable.class, obj);
		if (iter != null) {
			return new IteratorAdapter(iter.iterator());
		}
		throw ExceptionProvider.newIllegalValue("iterable", obj);
	}
	
	public static boolean isFalse(AnubisObject value) {
		return value == null || value instanceof AFalse;
	}
	
	public static boolean isNull(AnubisObject value) {
		return value == null || value instanceof ANull;
	}
	
	public static boolean isTrue(AnubisObject value) {
		return !isFalse(value);
	}
	
	public static AnubisObject opCall(AnubisObject object, String funcname, AnubisObject _this, AnubisObject... args) {
		ACallable func = findFunction(object, funcname);
		return func.call(_this, args);
	}
	
	public static AnubisObject opClose(AnubisObject obj) {
		return opCall(obj, "close", obj);
	}
	
	public static AnubisObject opEquals(AnubisObject x, AnubisObject y) {
		if (x == null) {
			return getBool(y == null);
		}
		return opCall(x, "==", x, y);
	}
	
	public static AnubisObject opIndex(AnubisObject x, AnubisObject index) {
		AIndexable indexable = Utils.as(AIndexable.class, x);
		if (indexable != null) {
			return indexable.getItem(index);
		}
		throw ExceptionProvider.newIllegalValue("indexable", x);
	}
	
	public static AnubisObject opNot(AnubisObject value) {
		return getBool(value == null || value instanceof AFalse);
	}
	
	public static AnubisObject opNotEquals(AnubisObject x, AnubisObject y) {
		if (x == null) {
			return getBool(y != null);
		}
		return opCall(x, "!=", x, y);
	}
	
	public static AnubisObject opSlice(AnubisObject x, AnubisObject start, AnubisObject end) {
		ASliceable sliceable = Utils.as(ASliceable.class, x);
		if (sliceable != null) {
			return sliceable.getItem(start, end);
		}
		throw ExceptionProvider.newIllegalValue("sliceable", x);
	}
	
	public static AnubisObject opXor(AnubisObject x, AnubisObject y) {
		return getBool(isTrue(x) ^ isTrue(y));
	}
	
	public static String toDebugString(AnubisObject obj) {
		if (obj == null)
			return "void";
		if (obj instanceof ADumpable)
			return ((ADumpable) obj).debugString();
		return obj.toString();
	}
	
	public static String toDumpString(AnubisObject obj) {
		if (obj instanceof ADumpable)
			return ((ADumpable) obj).dumpString();
		return toDebugString(obj);
	}
	
	public static String toString(AnubisObject obj) {
		if (obj == null)
			return "void";
		return obj.toString();
	}
	
	public static AnubisObject unwrapException(Throwable ex) {
		if (ex instanceof AnubisObject)
			return (AnubisObject) ex;
		if (ex instanceof UserException)
			return ((UserException) ex).getValue();
		return AObjects.getObject(ex);
	}
	
	public static Throwable wrapException(AnubisObject value) {
		if (value instanceof Throwable)
			return (Throwable) value;
		Throwable th = (Throwable) JCaster.as(Throwable.class, value);
		if (th != null) {
			return th;
		}
		return ExceptionProvider.newUserException(value);
	}
}
