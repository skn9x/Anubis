package anubis.ast;

import anubis.SpecialSlot;

public class SetSpecialLeftValue extends LeftValue {
	private final SpecialSlot name;
	
	public SetSpecialLeftValue(Expression expr, SpecialSlot name) {
		super(expr);
		assert name != null;
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public SpecialSlot getName() {
		return name;
	}
	
}
