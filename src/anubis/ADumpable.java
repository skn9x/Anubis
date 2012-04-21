package anubis;

/**
 * オブジェクト内部の詳細な文字列表現を取得可能な AnubisObject です。
 * @author SiroKuro
 */
public interface ADumpable extends AnubisObject {
	/**
	 * このオブジェクトのデバッグ用文字列表現を返します。
	 * @return 文字列
	 */
	public String debugString();
	
	/**
	 * このオブジェクトの詳細な文字列表現を返します。
	 * @return 文字列
	 */
	public String dumpString();
}
