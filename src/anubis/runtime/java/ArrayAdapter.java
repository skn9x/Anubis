package anubis.runtime.java;

import java.lang.reflect.Array;
import java.util.AbstractList;
import anubis.ACastable;
import anubis.AnubisObject;
import anubis.runtime.AList;
import anubis.runtime.AObjects;

public class ArrayAdapter extends AbstractList<AnubisObject> implements Adapter {
	private final Object internal;
	
	public ArrayAdapter(Object origin) {
		this.internal = origin;
	}
	
	@Override
	public AnubisObject get(int index) {
		return AObjects.getObject(Array.get(internal, index));
	}
	
	@Override
	public Object getOrigin() {
		return internal;
	}
	
	@Override
	public int size() {
		return Array.getLength(internal);
	}
	
	public AList toAList() {
		return new AListAdapter();
	}
	
	private class AListAdapter extends AList implements ACastable {
		public AListAdapter() {
			super(ArrayAdapter.this);
		}
		
		@Override
		public Object asJava() {
			return internal;
		}
	}
}
