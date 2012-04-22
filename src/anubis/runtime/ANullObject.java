package anubis.runtime;

import anubis.AFalse;

public class ANullObject extends APrimitive implements AFalse {
	@Override
	public Object asJava() {
		return null;
	}
	
	@Override
	public String getType() {
		return ObjectType.NULL;
	}
	
	@Override
	public String toString() {
		return "null";
	}
}
