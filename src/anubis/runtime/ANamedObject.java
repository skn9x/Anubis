package anubis.runtime;

/**
 * @author SiroKuro
 */
public class ANamedObject extends AObject {
	public final String name;
	
	public ANamedObject(String name) {
		super();
		this.name = name;
	}
	
	public ANamedObject(String name, SlotTable slots) {
		super(slots);
		this.name = name;
	}
	
	@Override
	public String getType() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
