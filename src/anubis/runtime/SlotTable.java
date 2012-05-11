package anubis.runtime;

import java.util.Map;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public interface SlotTable {
	
	public void freeze();
	
	public AnubisObject get(String name);
	
	public Map<String, AnubisObject> getSnap();
	
	public void put(String name, AnubisObject value);
}
