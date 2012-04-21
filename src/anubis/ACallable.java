package anubis;

/**
 * メソッドとして呼び出し可能な AnubisObject です。
 * @author SiroKuro
 */
public interface ACallable extends AnubisObject {
	/**
	 * このオブジェクトをメソッドとして呼び出します。
	 * @param _this This引数
	 * @param args 引数
	 * @return メソッドの戻り値
	 */
	public AnubisObject call(AnubisObject _this, AnubisObject... args);
}
