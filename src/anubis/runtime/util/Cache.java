package anubis.runtime.util;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

public class Cache<K, V> {
	private final Map<K, Reference<V>> cache = new WeakHashMap<K, Reference<V>>();
	
	public void clear() {
		cache.clear();
	}
	
	public V get(K key) {
		Reference<V> ref = cache.get(key);
		return ref == null ? null : ref.get();
	}
	
	public void put(K key, V value) {
		cache.put(key, new WeakReference<V>(value));
	}
	
	public int size() {
		return cache.size();
	}
}
