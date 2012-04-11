package anubis.runtime.java;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.script.Bindings;
import anubis.AnubisObject;
import anubis.runtime.AObjects;
import anubis.runtime.AbstractSlotTable;

/**
 * @author SiroKuro
 */
public class BindingsSlotTable extends AbstractSlotTable {
	private final Bindings bindings;
	
	public BindingsSlotTable(Bindings bindings) {
		assert bindings != null;
		this.bindings = bindings;
	}
	
	@Override
	public AnubisObject get(String key) {
		synchronized (bindings) {
			if (!bindings.containsKey(key))
				return null;
			else
				return AObjects.getObject(bindings.get(key));
		}
	}
	
	@Override
	public Map<String, AnubisObject> getSnap() {
		synchronized (bindings) {
			Map<String, AnubisObject> result = new TreeMap<String, AnubisObject>();
			for (Entry<String, Object> elm: bindings.entrySet()) {
				result.put(elm.getKey(), AObjects.getObject(elm.getValue()));
			}
			return result;
		}
	}
	
	@Override
	public void put(String name, AnubisObject value, boolean setReadonly) {
		synchronized (bindings) {
			assertNotReadonly(name, setReadonly);
			if (value == null)
				bindings.remove(name);
			else
				bindings.put(name, JCaster.cast(value));
		}
	}
}
