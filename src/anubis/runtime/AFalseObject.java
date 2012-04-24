package anubis.runtime;

import anubis.AFalse;

public class AFalseObject extends APrimitive implements AFalse {
	@Override
	public Object asJava() {
		return Boolean.FALSE;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass() == getClass();
	}
	
	@Override
	public String getType() {
		return ObjectType.BOOL;
	}
	
	@Override
	public int hashCode() {
		return 1;
	}
	
	@Override
	public String toString() {
		return "false";
	}
	
}
