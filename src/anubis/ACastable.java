package anubis;

/**
 * Java オブジェクトへキャスト可能な AnubisObject です。
 * @author SiroKuro
 */
public interface ACastable extends AnubisObject {
	/**
	 * このオブジェクトを Java オブジェクトへ変換します。
	 * @return Java オブジェクト
	 */
	public Object asJava();
}
