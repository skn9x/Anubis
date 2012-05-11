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
		return getType() + "_" + Utils.getIdString(this);
	}
	
	@Override
	public String dumpString() {
		return Utils.formatDumpString(this, slots.getSnap());
	}
	
	@Override
	public String toString() {
		return getType() + "_" + Utils.getIdString(this);
	}
}
