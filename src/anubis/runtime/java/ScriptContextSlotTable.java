package anubis.runtime.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.script.Bindings;
import javax.script.ScriptContext;
import anubis.AnubisObject;
import anubis.runtime.AObjects;
import anubis.runtime.AbstractSlotTable;

public class ScriptContextSlotTable extends AbstractSlotTable {
	private final ScriptContext context;
	
	public ScriptContextSlotTable(ScriptContext context) {
		assert context != null;
		this.context = context;
	}
	
	@Override
	public AnubisObject get(String name) {
		synchronized (context) {
			if (context.getAttributesScope(name) == -1)
				return null;
			else
				return AObjects.getObject(context.getAttribute(name));
		}
	}
	
	@Override
	public Map<String, AnubisObject> getSnap() {
		synchronized (context) {
			Map<String, AnubisObject> result = new TreeMap<String, AnubisObject>();
			List<Integer> scopes = new ArrayList<Integer>(context.getScopes());
			Collections.sort(scopes);
			for (int scope: scopes) {
				Bindings bindings = context.getBindings(scope);
				for (Entry<String, Object> elm: bindings.entrySet()) {
					result.put(elm.getKey(), AObjects.getObject(elm.getValue()));
				}
			}
			return result;
		}
	}
	
	@Override
	public void put(String name, AnubisObject value, boolean setReadonly) {
		synchronized (context) {
			assertNotReadonly(name, setReadonly);
			if (value == null)
				context.removeAttribute(name, ScriptContext.ENGINE_SCOPE);
			else
				context.setAttribute(name, JCaster.cast(value), ScriptContext.ENGINE_SCOPE);
		}
	}
}
