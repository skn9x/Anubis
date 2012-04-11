package anubis.ast;

import anubis.runtime.APrimitive;

/**
 * @author SiroKuro
 */
public class PrimitiveExpression implements Expression {
	private final APrimitive value; // void ���Ȃ킿 null �����e����
	
	public PrimitiveExpression(APrimitive value) {
		this.value = value;
	}
	
	/**
	 * @return the value
	 */
	public APrimitive getValue() {
		return value;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
	
}
