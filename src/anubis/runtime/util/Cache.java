package anubis.runtime.util;

public interface Cache<K, V> {
	
	public abstract void clear();
	
	public abstract V get(K key);
	
	public abstract void put(K key, V value);
	
	public abstract int size();
	
}
