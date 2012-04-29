package anubis.runtime;

import java.util.Map.Entry;
import anubis.ADumpable;
import anubis.AnubisObject;
import anubis.SpecialSlot;
import anubis.parser.ParserHelper;

/**
 * @author SiroKuro
 */
public class AObject extends AbstractAObject implements ADumpable {
	public AObject() {
		super();
	}
	
	public AObject(SlotTable slots) {
		super(slots);
	}
	
	@Override
	public String debugString() {
		return getType() + "@" + Integer.toHexString(System.identityHashCode(this));
	}
	
	@Override
	public String dumpString() {
		StringBuilder sb = new StringBuilder();
		sb.append(debugString()); // TODO 改行文字の整理
		sb.append(" {\n");
		// 特殊スロットのダンプ
		AnubisObject _this = getSlot(SpecialSlot.THIS);
		AnubisObject _super = getSlot(SpecialSlot.SUPER);
		AnubisObject _outer = getSlot(SpecialSlot.OUTER);
		if (_this != null && _this != this) {
			sb.append(String.format("*   this = %s;\n", Operator.toString(_this)));
		}
		if (_super != null) {
			sb.append(String.format("    super = %s;\n", Operator.toString(_super)));
		}
		if (_outer != null) {
			sb.append(String.format("    outer = %s;\n", Operator.toString(_outer)));
		}
		// 一般スロットのダンプ
		for (Entry<String, AnubisObject> elm: slots.getSnap().entrySet()) { // TODO 読み取り専用スロットにマーク付けたい
			sb.append(String.format("    %s = %s;\n", ParserHelper.quoteIdentifier(elm.getKey()),
					Operator.toString(elm.getValue())));
		}
		sb.append("}");
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return debugString();
	}
}
