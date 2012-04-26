package anubis.runtime;

import java.util.IdentityHashMap;
import anubis.AnubisObject;
import anubis.SpecialSlot;

/**
 * @author SiroKuro
 */
public abstract class ProtoVisitor<A, R> {
	public R start(AnubisObject _this, A arg) {
		IdentityHashMap<AnubisObject, Object> outer_visited = new IdentityHashMap<AnubisObject, Object>();
		while (_this != null) {
			R result = visit(_this, arg);
			if (result != null) {
				return result;
			}
			AnubisObject _super = _this.getSlot(SpecialSlot.SUPER);
			if (_super != null) {
				IdentityHashMap<AnubisObject, Object> super_visited = new IdentityHashMap<AnubisObject, Object>();
				do {
					result = visit(_super, arg);
					if (result != null) {
						return result;
					}
					if (secondpass(_super, super_visited)) {
						break;
					}
					_super = _super.getSlot(SpecialSlot.SUPER);
				} while (_super != null);
			}
			if (secondpass(_this, outer_visited)) {
				break;
			}
			_this = _this.getSlot(SpecialSlot.OUTER);
		}
		return null;
	}
	
	protected abstract R visit(AnubisObject obj, A arg);
	
	public static boolean secondpass(AnubisObject object, IdentityHashMap<AnubisObject, Object> visited) {
		if (visited == null)
			return false;
		boolean result = visited.containsKey(object);
		visited.put(object, null);
		return result;
	}
}
