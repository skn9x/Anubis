package anubis.runtime.java;

import java.util.AbstractList;
import java.util.List;
import anubis.AnubisObject;

public class ListAdapter extends AbstractList<AnubisObject> {
	private final List<Object> internal;
	
	public ListAdapter(List<Object> internal) {
		this.internal = internal;
	}
	
	@Override
	public void add(int index, AnubisObject element) {
		// TODO Auto-generated method stub
		super.add(index, element);
	}
	
	@Override
	public AnubisObject get(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public AnubisObject remove(int index) {
		// TODO Auto-generated method stub
		return super.remove(index);
	}
	
	@Override
	public AnubisObject set(int index, AnubisObject element) {
		// TODO Auto-generated method stub
		return super.set(index, element);
	}
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
}
