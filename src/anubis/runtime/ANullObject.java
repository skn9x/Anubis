package anubis.runtime;

import anubis.AFalse;
import anubis.ANull;

@TypeName(ObjectType.NULL)
public class ANullObject extends APrimitive implements AFalse, ANull {
	@Override
	public Object asJava() {
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass() == getClass();
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
