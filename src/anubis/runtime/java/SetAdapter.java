package anubis.runtime.java;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import anubis.ACastable;
import anubis.AnubisObject;
import anubis.runtime.ASet;

public class SetAdapter<T> extends AbstractSet<AnubisObject> implements Adapter {
	private final Set<T> internal;
	private final Class<T> cls;
	
	public SetAdapter(Set<T> internal, Class<T> cls) {
		this.internal = internal;
		this.cls = cls;
	}
	
	@Override
	public boolean add(AnubisObject value) {
		return internal.add(cls.cast(JCaster.cast(cls, value)));
	}
	
	@Override
	public Object getOrigin() {
		return internal;
	}
	
	@Override
	public Iterator<AnubisObject> iterator() {
		return new IteratorAdapter(internal.iterator());
	}
	
	@Override
	public int size() {
		return internal.size();
	}
	
	public ASet toASet() {
		return new ASetAdapter();
	}
	
	private class ASetAdapter extends ASet implements ACastable {
		public ASetAdapter() {
			super(SetAdapter.this);
		}
		
		@Override
		public Object asJava() {
			return internal;
		}
	}
}
