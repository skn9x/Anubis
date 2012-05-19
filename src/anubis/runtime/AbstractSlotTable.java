package anubis.runtime;

import anubis.except.ExceptionProvider;

/**
 * @author SiroKuro
 */
public abstract class AbstractSlotTable implements SlotTable {
	private volatile boolean immutable = false;
	
	public void freeze() {
		this.immutable = true;
	}
	
	protected void assertNotFreeze() {
		if (immutable) {
			throw ExceptionProvider.newObjectFreeze();
		}
	}
}
