package anubis.runtime;

import anubis.AFalse;

public class AFalseObject extends APrimitive implements AFalse {
	@Override
	public Object asJava() {
		return Boolean.FALSE;
	}
	
	@Override
	public String getType() {
		return ObjectType.BOOL;
	}
	
	@Override
	public String toString() {
		return "false";
	}
	
}
