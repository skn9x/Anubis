package anubis;

/**
 * AnubisObject のスロットへの参照を表すクラスです。
 * @author SiroKuro
 */
public class SlotRef {
	private final AnubisObject obj;
	private final String name;
	
	/**
	 * SlotRef オブジェクトを初期化します。
	 * @param obj 参照先スロットを含む AnubisObject
	 * @param name 参照するスロット名
	 */
	public SlotRef(AnubisObject obj, String name) {
		if (obj == null || name == null)
			throw new IllegalArgumentException();
		this.obj = obj;
		this.name = name;
	}
	
	/**
	 * スロットから値を取得します。
	 * @return スロットに代入されていた値
	 */
	public AnubisObject get() {
		return obj.getSlot(name);
	}
	
	/**
	 * 参照先スロットを含む KuzhaObject を返します。
	 * @return 参照先スロットを含む  AnubisObject
	 */
	public AnubisObject getObject() {
		return obj;
	}
	
	/**
	 * 参照しているスロット名を返します。
	 * @return 参照しているスロット名。
	 */
	public String getSlotName() {
		return name;
	}
	
	/**
	 * スロットに値を設定します。
	 * @param value スロットに設定する値。
	 */
	public void set(AnubisObject value) {
		obj.setSlot(name, value);
	}
	
	/**
	 * スロットに値を設定します。
	 * @param value スロットに設定する値。
	 */
	public void set(AnubisObject value, boolean readonly) {
		obj.setSlot(name, value, readonly);
	}
}
