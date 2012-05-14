package anubis.runtime.java;

import java.util.AbstractList;
import java.util.List;
import anubis.ACastable;
import anubis.AnubisObject;
import anubis.runtime.AList;
import anubis.runtime.AObjects;

public class ListAdapter<T> extends AbstractList<AnubisObject> implements Adapter {
	private final List<T> internal;
	private final Class<T> cls;
	
	public ListAdapter(List<T> internal, Class<T> cls) {
		this.internal = internal;
		this.cls = cls;
	}
	
	@Override
	public void add(int index, AnubisObject element) {
		internal.add(index, cls.cast(JCaster.cast(cls, element)));
	}
	
	@Override
	public AnubisObject get(int index) {
		return AObjects.getObject(internal.get(index));
	}
	
	@Override
	public Object getOrigin() {
		return internal;
	}
	
	@Override
	public AnubisObject remove(int index) {
		return AObjects.getObject(internal.remove(index));
	}
	
	@Override
	public AnubisObject set(int index, AnubisObject element) {
		return AObjects.getObject(internal.set(index, cls.cast(JCaster.cast(cls, element))));
	}
	
	@Override
	public int size() {
		return internal.size();
	}
	
	public AList toAList() {
		return new AListAdapter();
	}
	
	private class AListAdapter extends AList implements ACastable {
		public AListAdapter() {
			super(ListAdapter.this);
		}
		
		@Override
		public Object asJava() {
			return internal;
		}
	}
}
