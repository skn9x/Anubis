package anubis.ast;

public class SetSlotLeftValue extends LeftValue {
	private final String name;
	
	public SetSlotLeftValue(Expression expr, String name) {
		super(expr);
		assert name != null;
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
}
