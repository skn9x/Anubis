package anubis.runtime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import anubis.AIterable;
import anubis.ASliceable;
import anubis.AnubisObject;

@TypeName(ObjectType.LIST)
public class AList extends AObject implements ASliceable, AIterable {
	private final List<AnubisObject> list;
	
	public AList() {
		this(new ArrayList<AnubisObject>());
	}
	
	public AList(List<AnubisObject> list) {
		assert list != null;
		this.list = list;
	}
	
	public void add(AnubisObject obj) {
		list.add(obj);
	}
	
	@Override
	public Iterator<AnubisObject> getAIterator() {
		return list.iterator();
	}
	
	@Override
	public AnubisObject getItem(AnubisObject index) {
		int i = Utils.asIntArgs(index);
		if (0 <= i && i < list.size())
			return list.get(i);
		else
			return null;
	}
	
	@Override
	public AnubisObject getItem(AnubisObject start, AnubisObject end) {
		int size = list.size();
		int s = Utils.asIntArgs(start, 0);
		int e = Utils.asIntArgs(end, size - 1);
		if (s < 0) {
			s = 0;
		}
		if (size <= e) {
			e = size - 1;
		}
		return new AList(list.subList(s, e + 1));
	}
	
	@Override
	public Iterator<AnubisObject> iterator() {
		return list.iterator();
	}
	
	@Override
	public AnubisObject setItem(AnubisObject index, AnubisObject value) {
		list.set(Utils.asIntArgs(index), value);
		return value;
	}
	
	public int size() {
		return list.size();
	}
}
