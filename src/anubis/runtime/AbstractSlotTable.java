package anubis.runtime;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import anubis.AnubisObject;
import anubis.except.ExceptionProvider;

/**
 * @author SiroKuro
 */
public abstract class AbstractSlotTable implements SlotTable {
	private Set<String> readonly = null;
	private volatile boolean immutable = false;
	
	public void freeze() {
		this.immutable = true;
	}
	
	@Override
	public synchronized SnapShot getSnap() {
		final Map<String, AnubisObject> slots = getSlotSnaps();
		final Set<String> readonlys = getReadonlySnaps();
		return new SnapShot() {
			@Override
			public Set<String> getReadonlySlots() {
				return readonlys;
			}
			
			@Override
			public Map<String, AnubisObject> getSlots() {
				return slots;
			}
		};
	}
	
	protected synchronized void assertNotReadonly(String name, boolean setReadonly) {
		if (immutable || (readonly != null && readonly.contains(name))) {
			throw ExceptionProvider.newSlotReadonly(name);
		}
		if (setReadonly) {
			prepareReadonlySet().add(name);
		}
	}
	
	protected Set<String> getReadonlySnaps() {
		if (readonly == null)
			return Collections.emptySet();
		return Collections.unmodifiableSet(new HashSet<String>(readonly));
	}
	
	protected abstract Map<String, AnubisObject> getSlotSnaps();
	
	private Set<String> prepareReadonlySet() {
		if (this.readonly == null) {
			this.readonly = new HashSet<String>();
		}
		return this.readonly;
	}
}
