package anubis.runtime;

import anubis.AFalse;

public class ANullObject extends APrimitive implements AFalse {
	@Override
	public Object asJava() {
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass() == getClass();
	}
	
	@Override
	public String getType() {
		return ObjectType.NULL;
	}
	
	@Override
	public int hashCode() {
		return 1;
	}
	
	@Override
	public String toString() {
		return "null";
	}
	
}
