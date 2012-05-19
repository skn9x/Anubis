package anubis.runtime;

import java.util.ArrayList;
import java.util.List;
import anubis.AnubisObject;
import anubis.SpecialSlot;

/**
 * @author SiroKuro
 */
public abstract class ProtoVisitor<A, R> {
	private final boolean visitThis;
	
	public ProtoVisitor() {
		this.visitThis = true;
	}
	
	public ProtoVisitor(boolean visitThis) {
		this.visitThis = visitThis;
	}
	
	public R start(AnubisObject _this, A arg) {
		if (_this != null) {
			R result;
			if (visitThis) {
				result = visit(_this, arg);
				if (result != null) {
					return result;
				}
			}
			result = visitToSuper(_this.getSlot(SpecialSlot.SUPER), arg);
			if (result != null) {
				return result;
			}
			result = visitToOuter(_this.getSlot(SpecialSlot.OUTER), arg);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	protected abstract R visit(AnubisObject obj, A arg);
	
	private R visitToOuter(AnubisObject _outer, A arg) {
		if (_outer != null) {
			List<AnubisObject> visited = new ArrayList<AnubisObject>(5);
			do {
				R result = visitToSuper(_outer, arg);
				if (result != null || secondpass(_outer, visited)) {
					return result;
				}
				_outer = _outer.getSlot(SpecialSlot.OUTER);
			} while (_outer != null);
		}
		return null;
	}
	
	private R visitToSuper(AnubisObject _super, A arg) {
		if (_super != null) {
			List<AnubisObject> visited = new ArrayList<AnubisObject>(5);
			do {
				R result = visit(_super, arg);
				if (result != null || secondpass(_super, visited)) {
					return result;
				}
				_super = _super.getSlot(SpecialSlot.SUPER);
			} while (_super != null);
		}
		return null;
	}
	
	public static boolean secondpass(AnubisObject object, List<AnubisObject> visited) {
		if (visited != null) {
			int size = visited.size();
			for (int i = 0; i < size; i++) {
				if (visited.get(i) == object) {
					return true;
				}
			}
			visited.add(object);
		}
		return false;
	}
}
