package anubis.runtime;


public class ATrueObject extends APrimitive {
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
