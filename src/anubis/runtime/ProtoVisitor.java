package anubis.runtime;

import anubis.AnubisObject;
import anubis.SpecialSlot;

/**
 * @author SiroKuro
 */
public abstract class ProtoVisitor<A, R> {
	public R start(AnubisObject _this, A arg) {
		if (_this != null) {
			R result = visit(_this, arg);
			if (result != null) {
				return result;
			}
			result = startFromSuper(_this, _this.getSlot(SpecialSlot.SUPER), arg);
			if (result != null) {
				return result;
			}
			result = startFromOuter(_this, _this.getSlot(SpecialSlot.OUTER), arg);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	private R startFromOuter(AnubisObject _this, AnubisObject _outer, A arg) {
		while (_outer != null && _outer != _this) {
			R result = startFromSuper(_this, _outer, arg);
			if (result != null) {
				return result;
			}
			_outer = _outer.getSlot(SpecialSlot.OUTER);
		}
		return null;
	}
	
	private R startFromSuper(AnubisObject _this, AnubisObject _super, A arg) {
		while (_super != null && _super != _this) {
			R result = visit(_super, arg);
			if (result != null) {
				return result;
			}
			_super = _super.getSlot(SpecialSlot.SUPER);
		}
		return null;
	}
	
	protected abstract R visit(AnubisObject obj, A arg);
}
