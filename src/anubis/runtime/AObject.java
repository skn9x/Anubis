package anubis.runtime;

import java.util.Map.Entry;
import anubis.ADumpable;
import anubis.AnubisObject;
import anubis.SlotRef;
import anubis.SpecialSlot;
import anubis.except.ExceptionProvider;
import anubis.except.SlotReadonlyException;
import anubis.parser.ParserHelper;

/**
 * @author SiroKuro
 */
public class AObject implements AnubisObject, ADumpable {
	protected final SlotTable slots;
	private AnubisObject _super = null;
	private AnubisObject _outer = null;
	
	private static final ProtoVisitor<String, SlotRef> GETSLOTREF = new ProtoVisitor<String, SlotRef>() {
		@Override
		public SlotRef visit(AnubisObject obj, String arg) {
			if (obj.getSlot(arg) != null)
				return new SlotRef(obj, arg);
			return null;
		}
	};
	private static final ProtoVisitor<String, AnubisObject> GETSLOT = new ProtoVisitor<String, AnubisObject>() {
		@Override
		public AnubisObject visit(AnubisObject obj, String arg) {
			return obj.getSlot(arg);
		}
	};
	
	public AObject() {
		this(new SimpleSlotTable());
	}
	
	public AObject(SlotTable slots) {
		assert slots != null;
		this.slots = slots;
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
	public AnubisObject findSlot(String name) {
		return GETSLOT.start(this, name);
	}
	
	@Override
	public SlotRef findSlotRef(String name) {
		return GETSLOTREF.start(this, name);
	}
	
	@Override
	public AnubisObject getSlot(SpecialSlot type) {
		switch (type) {
			case THIS:
				return this;
			case SUPER:
				return this._super;
			case OUTER:
				return this._outer;
			default:
				return null;
		}
	}
	
	@Override
	public AnubisObject getSlot(String name) {
		return slots.get(name);
	}
	
	@Override
	public String getType() {
		return ObjectType.OBJECT;
	}
	
	@Override
	public void setSlot(SpecialSlot type, AnubisObject value) {
		switch (type) {
			case SUPER:
				this._super = value;
				break;
			case OUTER:
				this._outer = value;
				break;
			case THIS:
				throw ExceptionProvider.newSlotReadonly(this, "this");
			default:
				throw ExceptionProvider.newSlotNotFound(this, type.toString());
		}
	}
	
	@Override
	public void setSlot(String name, AnubisObject value) {
		try {
			slots.put(name, value, false);
		}
		catch (SlotReadonlyException ex) {
			throw ExceptionProvider.newSlotReadonly(ex, this, name);
		}
	}
	
	@Override
	public void setSlot(String name, AnubisObject value, boolean readonly) {
		try {
			slots.put(name, value, readonly);
		}
		catch (SlotReadonlyException ex) {
			throw ExceptionProvider.newSlotReadonly(ex, this, name);
		}
	}
	
	@Override
	public String toString() {
		return debugString();
	}
}
