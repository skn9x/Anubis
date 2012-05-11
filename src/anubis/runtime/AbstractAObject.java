package anubis.runtime;

import anubis.AnubisObject;
import anubis.SlotRef;
import anubis.SpecialSlot;
import anubis.except.ExceptionProvider;
import anubis.except.ObjectFreezeException;

public abstract class AbstractAObject implements AnubisObject {
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
	
	protected AbstractAObject() {
		this(new SimpleSlotTable());
	}
	
	protected AbstractAObject(SlotTable slots) {
		this.slots = slots;
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
				throw ExceptionProvider.newObjectFreeze(this, "this");
			default:
				throw ExceptionProvider.newSlotNotFound(this, type.toString());
		}
	}
	
	@Override
	public void setSlot(String name, AnubisObject value) {
		try {
			slots.put(name, value);
		}
		catch (ObjectFreezeException ex) {
			throw ExceptionProvider.newObjectFreeze(ex, this, name);
		}
	}
}
