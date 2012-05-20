package anubis.runtime.traits;

import anubis.SpecialSlot;
import anubis.runtime.ATrait;
import anubis.runtime.TraitsFactory;
import anubis.runtime.traits.func.ARootDebugString;
import anubis.runtime.traits.func.ARootDumpString;
import anubis.runtime.traits.func.ARootEquals;
import anubis.runtime.traits.func.ARootNewSlot;
import anubis.runtime.traits.func.ARootNotEquals;
import anubis.runtime.traits.func.ARootSetSlot;
import anubis.runtime.traits.func.ARootSetSpecialSlot;
import anubis.runtime.traits.func.ARootToString;

public class ARoot extends ATrait {
	public ARoot() {
		super("Root");
	}
	
	@Override
	protected void initSlots(TraitsFactory factory) {
		factory.attach(new ARootEquals(this, "=="));
		factory.attach(new ARootNotEquals(this, "!="));
		factory.attach(new ARootNewSlot(this, "newSlot"));
		factory.attach(new ARootSetSlot(this, "setSlot"));
		factory.attach(new ARootSetSpecialSlot(this, "setSuper", SpecialSlot.SUPER));
		factory.attach(new ARootSetSpecialSlot(this, "setOuter", SpecialSlot.OUTER));
		factory.attach(new ARootToString(this, "toString"));
		factory.attach(new ARootDebugString(this, "debugString"));
		factory.attach(new ARootDumpString(this, "dumpString"));
		// TODO new
	}
}
