package anubis.runtime;

import java.util.IdentityHashMap;
import anubis.AnubisObject;
import anubis.SpecialSlot;

/**
 * @author SiroKuro
 */
public abstract class ProtoVisitor<A, R> {
	public R start(AnubisObject _this, A arg) {
		IdentityHashMap<AnubisObject, Object> visited = new IdentityHashMap<AnubisObject, Object>();
		while (_this != null && firstpass(_this, visited)) {
			R result = visit(_this, arg);
			if (result != null) {
				return result;
			}
			AnubisObject _super = _this.getSlot(SpecialSlot.SUPER);
			while (_super != null && firstpass(_super, visited)) {
				result = visit(_super, arg);
				if (result != null) {
					return result;
				}
				_super = _super.getSlot(SpecialSlot.SUPER);
			}
			_this = _this.getSlot(SpecialSlot.OUTER);
		}
		return null;
	}
	
	protected abstract R visit(AnubisObject obj, A arg);
	
	public static boolean firstpass(AnubisObject object, IdentityHashMap<AnubisObject, Object> visited) {
		if (visited == null)
			return true;
		boolean result = visited.containsKey(object);
		visited.put(object, object);
		return !result;
	}
}
