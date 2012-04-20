package anubis.ast;

import anubis.SpecialSlot;

/**
 * @author SiroKuro
 */
public class GetSpecialExpression extends Expression {
	private final Expression expr;
	private final SpecialSlot name;
	
	public GetSpecialExpression(Expression expr, SpecialSlot name) {
		assert expr != null;
		assert name != null;
		this.expr = expr;
		this.name = name;
	}
	
	/**
	 * @return the expr
	 */
	public Expression getExpr() {
		return expr;
	}
	
	/**
	 * @return the name
	 */
	public SpecialSlot getName() {
		return name;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
	
}
