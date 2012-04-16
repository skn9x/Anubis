package anubis.runtime;

import java.util.Iterator;
import anubis.ACallable;
import anubis.ADumpable;
import anubis.AFalse;
import anubis.AIndexable;
import anubis.AIterable;
import anubis.ASliceable;
import anubis.AnubisObject;
import anubis.SlotRef;
import anubis.except.AnubisException;
import anubis.except.AnubisUserException;
import anubis.except.ExceptionProvider;
import anubis.runtime.java.IteratorAdapter;
import anubis.runtime.java.JCaster;

/**
 * @author SiroKuro
 */
public class Operator {
	public static final String SLOT_FORWARD = "$forward";
	
	public static void fail() {
		throw ExceptionProvider.newAssertionException();
	}
	
	/**
	 * obj �̃X���b�g���Q�Ƃ��A�֐���ԋp���܂��B�X���b�g��������Ȃ��ꍇ $forward ���l�����܂��B
	 * ����ł�������Ȃ��ꍇ�ɂ� SlotNotFoundException ���X���[����܂��B
	 * @param obj
	 * @param name
	 * @return
	 */
	public static ACallable findFunction(AnubisObject obj, String name) {
		AnubisObject val = findSlot(obj, name);
		if (val != null) {
			if (val instanceof ACallable)
				return (ACallable) val;
			else
				throw ExceptionProvider.newNotCallable(val);
		}
		val = findSlot(obj, SLOT_FORWARD);
		if (val != null) {
			return AFunction.partialApply(val, name);
		}
		throw ExceptionProvider.newSlotNotFound(obj, name);
	}
	
	/**
	 * obj �̃X���b�g���Q�Ƃ��܂��B����ł�������Ȃ��ꍇ�ɂ� null ���Ԃ�܂��B
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
	
	public static Iterator<AnubisObject> getIterator(AnubisObject obj) {
		if (obj instanceof AIterable) {
			return ((AIterable) obj).getAIterator();
		}
		if (obj instanceof Iterable) {
			return new IteratorAdapter(((Iterable<?>) obj).iterator());
		}
		throw new IllegalArgumentException(); // TODO
	}
	
	public static boolean isFalse(AnubisObject value) {
		return value == null || value instanceof AFalse;
	}
	
	public static boolean isNull(AnubisObject value) {
		return JCaster.cast(value) == null; // TODO ���\�����čl
	}
	
	public static boolean isTrue(AnubisObject value) {
		return !isFalse(value);
	}
	
	public static AnubisObject opAssignSlot(AnubisObject object, String name, AnubisObject value) {
		SlotRef ref = object.findSlotRef(name);
		if (ref != null)
			ref.set(value);
		else
			object.setSlot(name, value);
		return value;
	}
	
	public static boolean opEquals(AnubisObject x, AnubisObject y) {
		if (x == null) {
			return y == null;
		}
		return x.equals(y);
	}
	
	public static AnubisObject opIndex(AnubisObject x, AnubisObject index) {
		if (x instanceof AIndexable) {
			AIndexable indexable = (AIndexable) x;
			return indexable.getItem(index);
		}
		else {
			throw new AnubisException("not indexable"); // TODO
		}
	}
	
	public static boolean opNot(AnubisObject value) {
		return value == null || value instanceof AFalse;
	}
	
	public static boolean opNotEquals(AnubisObject x, AnubisObject y) {
		if (x == null) {
			return y != null;
		}
		return !x.equals(y);
	}
	
	public static AnubisObject opSlice(AnubisObject x, AnubisObject start, AnubisObject end) {
		if (x instanceof ASliceable) {
			ASliceable sliceable = (ASliceable) x;
			return sliceable.getItem(start, end);
		}
		else {
			throw new AnubisException("not sliceable"); // TODO
		}
	}
	
	public static boolean opXor(AnubisObject x, AnubisObject y) {
		return isTrue(x) ^ isTrue(y);
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
		if (ex instanceof AnubisUserException)
			return ((AnubisUserException) ex).getValue();
		return AObjects.getJObject(ex); // TODO �������� AObjects �g���̂͂Ȃ񂩌�
	}
	
	public static Throwable wrapException(AnubisObject value) {
		if (value instanceof Throwable)
			return (Throwable) value;
		Throwable th = (Throwable) JCaster.as(Throwable.class, value); // TODO �������� JCaster �g���̂͂Ȃ񂩌�
		if (th != null)
			return th;
		return ExceptionProvider.newUserException(value);
	}
}
