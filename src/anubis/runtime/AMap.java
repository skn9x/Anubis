package anubis.runtime;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import anubis.AIndexable;
import anubis.AIterable;
import anubis.AnubisObject;

public class AMap extends AObject implements AIndexable, AIterable {
	private final Map<AnubisObject, AnubisObject> map;
	
	public AMap() {
		this(new HashMap<AnubisObject, AnubisObject>());
	}
	
	public AMap(Map<AnubisObject, AnubisObject> map) {
		assert map != null;
		this.map = map;
	}
	
	public void add(AnubisObject key, AnubisObject obj) {
		map.put(key, obj);
	}
	
	@Override
	public Iterator<AnubisObject> getAIterator() {
		return map.keySet().iterator();
	}
	
	@Override
	public AnubisObject getItem(AnubisObject index) {
		return map.get(index);
	}
	
	@Override
	public String getType() {
		return ObjectType.MAP;
	}
	
	@Override
	public Iterator<AnubisObject> iterator() {
		return map.keySet().iterator();
	}
	
	@Override
	public AnubisObject setItem(AnubisObject key, AnubisObject value) {
		map.put(key, value);
		return value;
	}
	
	public int size() {
		return map.size();
	}
}
