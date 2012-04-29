package anubis.runtime.util;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class IdentityCache<K, V> implements Cache<K, V> {
	private final Map<IdentityHolder, Reference<V>> cache = new HashMap<IdentityHolder, Reference<V>>();
	private final Map<Reference<K>, IdentityHolder> refmap = new HashMap<Reference<K>, IdentityHolder>();
	private final ReferenceQueue<K> refqueue = new ReferenceQueue<K>();
	
	/* (non-Javadoc)
	 * @see anubis.runtime.util.Cache#clear()
	 */
	@Override
	public void clear() {
		cache.clear();
		refmap.clear();
	}
	
	/* (non-Javadoc)
	 * @see anubis.runtime.util.Cache#get(K)
	 */
	@Override
	public V get(K key) {
		gc();
		Reference<V> ref = cache.get(new IdentityHolder(key));
		return ref == null ? null : ref.get();
	}
	
	/* (non-Javadoc)
	 * @see anubis.runtime.util.Cache#put(K, V)
	 */
	@Override
	public void put(K key, V value) {
		gc();
		IdentityHolder holder = new IdentityHolder(key);
		cache.put(holder, new WeakReference<V>(value));
	}
	
	/* (non-Javadoc)
	 * @see anubis.runtime.util.Cache#size()
	 */
	@Override
	public int size() {
		gc();
		return cache.size();
	}
	
	private void gc() {
		Reference<? extends K> ref = refqueue.poll();
		while (ref != null) {
			IdentityHolder holder = refmap.get(ref);
			refmap.remove(ref);
			if (holder != null) {
				cache.remove(holder);
			}
			ref = refqueue.poll();
		}
	}
	
	private class IdentityHolder {
		private final int hashcode;
		private final Reference<K> value;
		
		public IdentityHolder(K value) {
			assert value != null;
			this.hashcode = System.identityHashCode(value);
			this.value = new WeakReference<K>(value, refqueue);
			refmap.put(this.value, this);
		}
		
		@Override
		public boolean equals(Object obj) {
			return obj instanceof IdentityCache.IdentityHolder
					&& equals(value, ((IdentityCache<?, ?>.IdentityHolder) obj).value);
		}
		
		@Override
		public int hashCode() {
			return hashcode;
		}
		
		private boolean equals(Reference<?> x, Reference<?> y) {
			Object a = x.get();
			Object b = y.get();
			if (a == null || b == null)
				return false; // どちらかが回収されているときは必ず false
			return a == b;
		}
		
	}
}
