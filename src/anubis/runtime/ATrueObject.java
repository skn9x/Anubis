package anubis.runtime;

@TypeName(ObjectType.BOOL)
public class ATrueObject extends APrimitive {
	@Override
	public Object asJava() {
		return Boolean.TRUE;
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
		return "true";
	}
	
}
