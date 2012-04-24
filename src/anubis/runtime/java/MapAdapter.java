package anubis.runtime.java;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import anubis.AnubisObject;

public class MapAdapter extends AbstractMap<AnubisObject, AnubisObject> {
	private final Map<Object, Object> internal;
	
	public MapAdapter(Map<Object, Object> internal) {
		this.internal = internal;
	}
	
	@Override
	public Set<java.util.Map.Entry<AnubisObject, AnubisObject>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public AnubisObject put(AnubisObject arg0, AnubisObject arg1) {
		// TODO Auto-generated method stub
		return super.put(arg0, arg1);
	}
	
}
