package anubis.runtime.util;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

public class Cache<K, V> {
	private final Map<Holder<K>, Reference<V>> cache = new WeakHashMap<Holder<K>, Reference<V>>();
	
	public void clear() {
		cache.clear();
	}
	
	public V get(K key) {
		Reference<V> ref = cache.get(new Holder<K>(key));
		return ref == null ? null : ref.get();
	}
	
	public void put(K key, V value) {
		cache.put(new Holder<K>(key), new WeakReference<V>(value));
	}
	
	public int size() {
		return cache.size();
	}
	
	private static class Holder<K> {
		private final K object;
		
		public Holder(K object) {
			assert object != null;
			this.object = object;
		}
		
		@Override
		public boolean equals(Object obj) {
			return obj instanceof Holder && object == ((Holder<?>) obj).object;
		}
		
		@Override
		public int hashCode() {
			return System.identityHashCode(object);
		}
	}
}
