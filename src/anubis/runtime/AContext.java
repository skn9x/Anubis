package anubis.runtime;

import anubis.AnubisObject;
import anubis.SpecialSlot;

/**
 * @author SiroKuro
 */
public class AContext extends AObject {
	private final AnubisObject _this;
	
	public AContext(AnubisObject _this, AnubisObject outer) {
		this._this = _this;
		setSlot(SpecialSlot.SUPER, _this);
		setSlot(SpecialSlot.OUTER, outer);
	}
	
	public AContext(SlotTable slots, AnubisObject _this, AnubisObject outer) {
		super(slots);
		this._this = _this;
	}
	
	@Override
	public AnubisObject getSlot(SpecialSlot type) {
		switch (type) {
			case THIS:
				return _this;
			case SUPER:
				return _this.getSlot(type);
			default:
				return super.getSlot(type);
		}
	}
	
	@Override
	public String getType() {
		return ObjectType.CONTEXT;
	}
}
