package anubis.runtime;

import anubis.ADumpable;

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
		return ObjectType.get(this) + "_" + Utils.getIdString(this);
	}
	
	@Override
	public String dumpString() {
		return Utils.formatDumpString(this, getSlotTable().getSnap());
	}
	
	@Override
	public String toString() {
		return ObjectType.get(this) + "_" + Utils.getIdString(this);
	}
}
