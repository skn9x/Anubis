package anubis.runtime;

import anubis.ACastable;
import anubis.AFalse;

public class AFalseObject extends APrimitive implements AFalse, ACastable {
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
