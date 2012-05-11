package anubis.runtime;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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
	
	public synchronized void put(String name, AnubisObject value, boolean setReadonly) {
		assertNotReadonly(name, setReadonly);
		if (value == null)
			prepareInternal().remove(name);
		else
			prepareInternal().put(name, value);
	}
	
	@Override
	protected Map<String, AnubisObject> getSlotSnaps() {
		if (internal == null)
			return Collections.emptyMap();
		return Collections.unmodifiableMap(new HashMap<String, AnubisObject>(internal));
	}
	
	private Map<String, AnubisObject> prepareInternal() {
		if (this.internal == null) {
			this.internal = new HashMap<String, AnubisObject>();
		}
		return this.internal;
	}
}
