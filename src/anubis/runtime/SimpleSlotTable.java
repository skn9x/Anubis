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
		if (internal != null) {
			return internal.get(name);
		}
		return null;
	}
	
	@Override
	public Map<String, AnubisObject> getSnap() {
		if (internal == null)
			return Collections.emptyMap();
		return Collections.unmodifiableMap(new TreeMap<String, AnubisObject>(internal));
	}
	
	@Override
	public synchronized void put(String name, AnubisObject value) {
		assertNotFreeze();
		if (value == null)
			prepareInternal().remove(name);
		else
			prepareInternal().put(name, value);
	}
	
	private Map<String, AnubisObject> prepareInternal() {
		if (this.internal == null) {
			this.internal = new HashMap<String, AnubisObject>();
		}
		return this.internal;
	}
}
