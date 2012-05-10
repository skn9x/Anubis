package anubis.runtime.java;

import java.lang.reflect.Array;
import java.util.AbstractList;
import anubis.AnubisObject;
import anubis.runtime.AObjects;

public class ArrayAdapter extends AbstractList<AnubisObject> implements Adapter {
	private final Object origin;
	
	public ArrayAdapter(Object origin) {
		this.origin = origin;
	}
	
	@Override
	public AnubisObject get(int index) {
		return AObjects.getObject(Array.get(origin, index));
	}
	
	@Override
	public Object getOrigin() {
		return origin;
	}
	
	@Override
	public int size() {
		return Array.getLength(origin);
	}
	
}
