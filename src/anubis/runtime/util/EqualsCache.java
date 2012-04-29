package anubis.runtime.util;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

public class EqualsCache<K, V> implements Cache<K, V> {
	private final Map<K, Reference<V>> cache = new WeakHashMap<K, Reference<V>>();
	
	/* (non-Javadoc)
	 * @see anubis.runtime.util.Cache#clear()
	 */
	@Override
	public void clear() {
		cache.clear();
	}
	
	/* (non-Javadoc)
	 * @see anubis.runtime.util.Cache#get(K)
	 */
	@Override
	public V get(K key) {
		Reference<V> ref = cache.get(key);
		return ref == null ? null : ref.get();
	}
	
	/* (non-Javadoc)
	 * @see anubis.runtime.util.Cache#put(K, V)
	 */
	@Override
	public void put(K key, V value) {
		cache.put(key, new WeakReference<V>(value));
	}
	
	/* (non-Javadoc)
	 * @see anubis.runtime.util.Cache#size()
	 */
	@Override
	public int size() {
		return cache.size();
	}
}
