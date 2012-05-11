package anubis.runtime;

import java.util.Iterator;
import anubis.ACallable;
import anubis.ACastable;
import anubis.ADumpable;
import anubis.AFalse;
import anubis.AIndexable;
import anubis.AIterable;
import anubis.ASliceable;
import anubis.AnubisObject;
import anubis.SlotRef;
import anubis.SpecialSlot;

public class ANop implements AnubisObject, AIndexable, ACallable, ACastable, ADumpable, ASliceable, AFalse, AIterable, Cloneable {
	@Override
	public Object asJava() {
		return this;
	}
	
	@Override
	public AnubisObject call(AnubisObject _this, AnubisObject... args) {
		return this;
	}
	
	@Override
	public String debugString() {
		return getType();
	}
	
	@Override
	public String dumpString() {
		return getType();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && getClass() == obj.getClass();
	}
	
	@Override
	public AnubisObject findSlot(String name) {
		return this;
	}
	
	@Override
	public SlotRef findSlotRef(String name) {
		return new SlotRef(this, name);
	}
	
	@Override
	public Iterator<AnubisObject> getAIterator() {
		return new Iterator<AnubisObject>() {
			@Override
			public boolean hasNext() {
				return false;
			}
			
			@Override
			public AnubisObject next() {
				return null;
			}
			
			@Override
			public void remove() {
				;
			}
		};
	}
	
	@Override
	public AnubisObject getItem(AnubisObject index) {
		return this;
	}
	
	@Override
	public AnubisObject getItem(AnubisObject start, AnubisObject end) {
		return this;
	}
	
	@Override
	public AnubisObject getSlot(SpecialSlot type) {
		return this;
	}
	
	@Override
	public AnubisObject getSlot(String name) {
		return this;
	}
	
	@Override
	public String getType() {
		return ObjectType.NOP;
	}
	
	@Override
	public int hashCode() {
		return 1234567;
	}
	
	@Override
	public Iterator<AnubisObject> iterator() {
		return getAIterator();
	}
	
	@Override
	public AnubisObject setItem(AnubisObject index, AnubisObject value) {
		return this;
	}
	
	@Override
	public void setSlot(SpecialSlot type, AnubisObject value) {
		;
	}
	
	@Override
	public void setSlot(String name, AnubisObject value) {
		;
	}
	
	@Override
	public String toString() {
		return debugString();
	}
}
