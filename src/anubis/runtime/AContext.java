package anubis.runtime;

import anubis.AnubisObject;
import anubis.SpecialSlot;
import anubis.TypeName;

/**
 * @author SiroKuro
 */
@TypeName(ObjectType.CONTEXT)
public class AContext extends AObject {
	private final AnubisObject _this;
	
	/**
	 * AContext を初期化する
	 * @param _this 新しいコンテキストでの this オブジェクト (null可)
	 * @param outer 新しいコンテキストでの outer オブジェクト (null可)
	 */
	public AContext(AnubisObject _this, AnubisObject outer) {
		this._this = _this;
		setSlot(SpecialSlot.SUPER, _this);
		setSlot(SpecialSlot.OUTER, outer);
	}
	
	@Override
	public AnubisObject getSlot(SpecialSlot type) {
		switch (type) {
			case THIS:
				return _this;
			case SUPER:
				return _this == null ? null : _this.getSlot(type);
			default:
				return super.getSlot(type);
		}
	}
}
