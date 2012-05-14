package anubis.runtime;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import anubis.AIterable;
import anubis.AnubisObject;

@TypeName(ObjectType.SET)
public class ASet extends AObject implements AIterable {
	private final Set<AnubisObject> set;
	
	public ASet() {
		this(new HashSet<AnubisObject>());
	}
	
	public ASet(Set<AnubisObject> set) {
		this.set = set;
	}
	
	public void add(AnubisObject obj) {
		set.add(obj);
	}
	
	@Override
	public Iterator<AnubisObject> getAIterator() {
		return set.iterator();
	}
	
	@Override
	public Iterator<AnubisObject> iterator() {
		return set.iterator();
	}
	
	public int size() {
		return set.size();
	}
	
}
