package anubis;

/**
 * Anubis で扱うオブジェクトのルートとなるインタフェイスです。
 * @author SiroKuro
 */
public interface AnubisObject {
	/**
	 * このオブジェクト(およびプロトオブジェクト)からスロットを検索します。
	 * @param name スロット名
	 * @return スロットに設定されていたオブジェクト
	 * @see AnubisObject#getSlot(String)
	 */
	public AnubisObject findSlot(String name);
	
	/**
	 * このオブジェクト(およびプロトオブジェクト)からスロット参照を検索します。
	 * @param name スロット名
	 * @return スロットへの参照
	 * @see AnubisObject#findSlot(String)
	 */
	public SlotRef findSlotRef(String name);
	
	/**
	 * このオブジェクトの特殊スロットを返します。
	 * @param type 特殊スロットの種類
	 * @return スロットに設定されていたオブジェクト
	 */
	public AnubisObject getSlot(SpecialSlot type);
	
	/**
	 * このオブジェクトからスロットを検索します。
	 * @param name スロット名
	 * @return スロットに設定されていたオブジェクト
	 * @see AnubisObject#findSlot(String)
	 */
	public AnubisObject getSlot(String name);
	
	/**
	 * このオブジェクトの特殊スロットを設定します。オブジェクトが null の場合は特殊スロットが削除されます。
	 * @param type 特殊スロットの種類
	 * @param value オブジェクト
	 */
	public void setSlot(SpecialSlot type, AnubisObject value);
	
	/**
	 * このオブジェクトにスロットを設定します。オブジェクトが null の場合はスロットが削除されます。
	 * @param name スロット名
	 * @param value オブジェクト
	 */
	public void setSlot(String name, AnubisObject value);
}
