package anubis;

/**
 * インデクサでアクセスすることができる AnubisObject です。
 * @author SiroKuro
 */
public interface AIndexable extends AnubisObject {
	/**
	 * index に対応するオブジェクトを返します。
	 * @param index インデックス値
	 * @return 対応するオブジェクト、または null
	 */
	public AnubisObject getItem(AnubisObject index);
	
	/**
	 * index に対応するオブジェクトを設定します。
	 * @param index インデックス値
	 * @param value 設定するオブジェクト、または null
	 * @return value そのもの
	 */
	public AnubisObject setItem(AnubisObject index, AnubisObject value);
}
