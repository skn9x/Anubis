package anubis.runtime.java;

import java.util.Iterator;
import anubis.AnubisObject;
import anubis.runtime.AObjects;

public class IteratorAdapter implements Iterator<AnubisObject> {
	private final Iterator<?> iter;
	
	public IteratorAdapter(Iterator<?> iter) {
		assert iter != null;
		this.iter = iter;
	}
	
	@Override
	public boolean hasNext() {
		return iter.hasNext();
	}
	
	@Override
	public AnubisObject next() {
		return AObjects.getObject(iter.next());
	}
	
	@Override
	public void remove() {
		iter.remove();
	}
}
