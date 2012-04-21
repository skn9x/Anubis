package anubis;

/**
 * スライサでアクセスすることができる AnubisObject です。
 * @author SiroKuro
 */
public interface ASliceable extends AIndexable {
	/**
	 * start から end までのオブジェクトを返します。
	 * @param start 開始インデックス(この値を含む)
	 * @param end 終了インデックス(この値を含む)
	 * @return オブジェクト
	 */
	public AnubisObject getItem(AnubisObject start, AnubisObject end);
	
	// TODO setItem
}
