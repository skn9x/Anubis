package anubis;

/**
 * @author SiroKuro
 */
public interface ACallable extends AnubisObject {
	/**
	 * このオブジェクトを関数として呼び出します。
	 * @param _this
	 * @param objects
	 * @return
	 */
	public AnubisObject call(AnubisObject _this, AnubisObject... objects);
}
