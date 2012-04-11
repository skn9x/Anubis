package anubis.runtime;

import anubis.ACastable;

public class ATrueObject extends APrimitive implements ACastable {
	@Override
	public Object asJava() {
		return Boolean.TRUE;
	}
	
	@Override
	public String getType() {
		return ObjectType.BOOL;
	}
	
	@Override
	public String toString() {
		return "true";
	}
}
