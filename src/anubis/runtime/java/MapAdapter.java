package anubis.runtime.java;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import anubis.AnubisObject;
import anubis.runtime.AObjects;

public class MapAdapter<K, V> extends AbstractMap<AnubisObject, AnubisObject> implements Adapter {
	private final Map<K, V> internal;
	private final Class<K> cls_key;
	private final Class<V> cls_val;
	private final EntrySetAdapter entrySet = new EntrySetAdapter();
	
	public MapAdapter(Map<K, V> internal, Class<K> cls_key, Class<V> cls_val) {
		this.internal = internal;
		this.cls_key = cls_key;
		this.cls_val = cls_val;
	}
	
	@Override
	public Set<Map.Entry<AnubisObject, AnubisObject>> entrySet() {
		return entrySet;
	}
	
	@Override
	public Object getOrigin() {
		return internal;
	}
	
	@Override
	public AnubisObject put(AnubisObject key, AnubisObject value) {
		return AObjects.getObject(internal.put(cls_key.cast(JCaster.cast(cls_key, key)),
				cls_val.cast(JCaster.cast(cls_val, value))));
	}
	
	private class EntryAdapter implements Entry<AnubisObject, AnubisObject>, Adapter {
		private final Entry<K, V> entry;
		
		public EntryAdapter(java.util.Map.Entry<K, V> entry) {
			this.entry = entry;
		}
		
		@Override
		public AnubisObject getKey() {
			return AObjects.getObject(entry.getKey());
		}
		
		@Override
		public Object getOrigin() {
			return entry;
		}
		
		@Override
		public AnubisObject getValue() {
			return AObjects.getObject(entry.getValue());
		}
		
		@Override
		public AnubisObject setValue(AnubisObject value) {
			return AObjects.getObject(entry.setValue(cls_val.cast(JCaster.cast(cls_val, value))));
		}
	}
	
	private class EntrySetAdapter extends AbstractSet<Map.Entry<AnubisObject, AnubisObject>> implements Adapter {
		@Override
		public Object getOrigin() {
			return internal.entrySet();
		}
		
		@Override
		public Iterator<Entry<AnubisObject, AnubisObject>> iterator() {
			return new EntrySetIteratorAdapter(internal.entrySet().iterator());
		}
		
		@Override
		public int size() {
			return internal.entrySet().size();
		}
	}
	
	private class EntrySetIteratorAdapter implements Iterator<Entry<AnubisObject, AnubisObject>>, Adapter {
		private final Iterator<Entry<K, V>> iter;
		
		public EntrySetIteratorAdapter(Iterator<Entry<K, V>> iter) {
			this.iter = iter;
		}
		
		@Override
		public Object getOrigin() {
			return iter;
		}
		
		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}
		
		@Override
		public Entry<AnubisObject, AnubisObject> next() {
			return new EntryAdapter(iter.next());
		}
		
		@Override
		public void remove() {
			iter.remove();
		}
	}
}
