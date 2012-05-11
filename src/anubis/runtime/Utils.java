package anubis.runtime;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import anubis.AnubisObject;
import anubis.SpecialSlot;
import anubis.except.ExceptionProvider;
import anubis.parser.ParserHelper;
import anubis.runtime.SlotTable.SnapShot;

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
	
	public static <T extends AnubisObject> T as(Class<T> cls, AnubisObject obj) { // TODO null を返したときの対処を入れる
		return cls.cast(CAST.start(obj, cls));
	}
	
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
		ANumber num = Utils.as(ANumber.class, obj);
		if (num == null)
			throw ExceptionProvider.newVoidOperation(); // TODO IllegalArgumentException
		// TODO オーバーフローチェックをいれる
		return num.getNumber().intValue();
	}
	
	public static int asIntArgs(AnubisObject obj, int _default) {
		ANumber num = Utils.as(ANumber.class, obj);
		if (num == null)
			return _default;
		// TODO オーバーフローチェックをいれる
		return num.getNumber().intValue();
	}
	
	public static String formatDumpString(AObject object, SnapShot snapShot) {
		int max_length = 5;
		for (String key: snapShot.getSlots().keySet()) {
			key = ParserHelper.quoteIdentifier(key);
			if (max_length < key.length()) {
				max_length = key.length();
			}
		}
		max_length += 4;
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		{
			pw.println(object.debugString());
			// 特殊スロットのダンプ
			AnubisObject _this = object.getSlot(SpecialSlot.THIS);
			AnubisObject _super = object.getSlot(SpecialSlot.SUPER);
			AnubisObject _outer = object.getSlot(SpecialSlot.OUTER);
			if (_this != null)
				pw.println(padding(max_length, "    this") + " = " + Operator.toDebugString(_this));
			if (_super != null)
				pw.println(padding(max_length, "    super") + " = " + Operator.toDebugString(_super));
			if (_outer != null)
				pw.println(padding(max_length, "    outer") + " = " + Operator.toDebugString(_outer));
			// 一般スロットのダンプ
			for (Entry<String, AnubisObject> elm: snapShot.getSlots().entrySet()) {
				if (snapShot.getReadonlySlots().contains(elm.getKey())) {
					pw.println(padding(max_length, "R   " + ParserHelper.quoteIdentifier(elm.getKey())) + " = "
							+ Operator.toDebugString(elm.getValue()));
				}
				else {
					pw.println(padding(max_length, "    " + ParserHelper.quoteIdentifier(elm.getKey())) + " = "
							+ Operator.toDebugString(elm.getValue()));
				}
			}
			pw.println("}");
		}
		pw.close();
		return sw.toString();
	}
	
	public static String getIdString(Object obj) {
		return Integer.toHexString(System.identityHashCode(obj));
	}
	
	private static String padding(int length, String text) {
		StringBuilder sb = new StringBuilder(text);
		sb.setLength(length);
		return sb.toString().replace('\u0000', ' ');
	}
}
