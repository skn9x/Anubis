package anubis.runtime;

/**
 * @author SiroKuro
 */
public class ANamedObject extends AObject {
	public final String name;
	
	public ANamedObject(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
