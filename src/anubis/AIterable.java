package anubis;

import java.util.Iterator;

/**
 * Iterable&lt;AnubisObject&gt; を返却できる AnubisObject です。
 * @author SiroKuro
 */
public interface AIterable extends AnubisObject, Iterable<AnubisObject> {
	/**
	 * Iterable&lt;AnubisObject&gt; を返却します。
	 * @return Iterable オブジェクト
	 */
	public Iterator<AnubisObject> getAIterator();
}
