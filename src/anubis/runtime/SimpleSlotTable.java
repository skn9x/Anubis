package anubis.runtime;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public class SimpleSlotTable extends AbstractSlotTable {
	private volatile Map<String, AnubisObject> internal = null;
	
	@Override
	public AnubisObject get(String name) {
		Map<String, AnubisObject> map = getInternal();
		if (map != null) {
			synchronized (this) {
				return map.get(name);
			}
		}
		return null;
	}
	
	@Override
	public Map<String, AnubisObject> getSnap() {
		Map<String, AnubisObject> map = getInternal();
		if (map != null) {
			synchronized (this) {
				return Collections.unmodifiableMap(new TreeMap<String, AnubisObject>(map));
			}
		}
		return Collections.emptyMap();
	}
	
	@Override
	public void put(String name, AnubisObject value) {
		assertNotFreeze();
		synchronized (this) {
			if (value == null)
				prepareInternal().remove(name);
			else
				prepareInternal().put(name, value);
		}
	}
	
	private Map<String, AnubisObject> getInternal() {
		return this.internal;
	}
	
	private Map<String, AnubisObject> prepareInternal() {
		if (this.internal == null) {
			this.internal = new HashMap<String, AnubisObject>();
		}
		return this.internal;
	}
}
