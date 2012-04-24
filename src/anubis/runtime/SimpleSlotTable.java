package anubis.runtime;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public class SimpleSlotTable extends AbstractSlotTable {
	private volatile Map<String, AnubisObject> internal = null;
	
	public AnubisObject get(String name) {
		if (internal != null) {
			return internal.get(name);
		}
		return null;
	}
	
	public synchronized Map<String, AnubisObject> getSnap() {
		if (internal == null) {
			return Collections.emptyMap();
		}
		else {
			Map<String, AnubisObject> result = new TreeMap<String, AnubisObject>();
			for (Entry<String, AnubisObject> elm: internal.entrySet()) {
				result.put(elm.getKey(), elm.getValue());
			}
			return result;
		}
	}
	
	public synchronized void put(String name, AnubisObject value, boolean setReadonly) {
		assertNotReadonly(name, setReadonly);
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
