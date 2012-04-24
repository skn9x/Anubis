package anubis.runtime.java;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import anubis.AnubisObject;

public class SetAdapter extends AbstractSet<AnubisObject> {
	private final Set<Object> internal;
	
	public SetAdapter(Set<Object> internal) {
		this.internal = internal;
	}
	
	@Override
	public boolean add(AnubisObject arg0) {
		// TODO Auto-generated method stub
		return super.add(arg0);
	}
	
	@Override
	public Iterator<AnubisObject> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
}
