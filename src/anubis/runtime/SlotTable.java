package anubis.runtime;

import java.util.Map;
import java.util.Set;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public interface SlotTable {
	
	public void freeze();
	
	public AnubisObject get(String name);
	
	public SnapShot getSnap();
	
	public void put(String name, AnubisObject value, boolean setReadonly);
	
	public interface SnapShot {
		public Set<String> getReadonlySlots();
		
		public Map<String, AnubisObject> getSlots();
	}
}
