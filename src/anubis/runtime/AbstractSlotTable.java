package anubis.runtime;

import java.util.HashSet;
import java.util.Set;
import anubis.except.ExceptionProvider;

/**
 * @author SiroKuro
 */
public abstract class AbstractSlotTable implements SlotTable {
	private Set<String> readonly = null;
	private volatile boolean immutable = false;
	
	protected synchronized void assertNotReadonly(String name, boolean setReadonly) {
		if (immutable || (readonly != null && readonly.contains(name))) {
			throw ExceptionProvider.newSlotReadonly(name);
		}
		if (setReadonly) {
			prepareReadonlySet().add(name);
		}
	}
	
	public void freeze() {
		this.immutable = true;
	}
	
	private Set<String> prepareReadonlySet() {
		if (this.readonly == null) {
			this.readonly = new HashSet<String>();
		}
		return this.readonly;
	}
}
