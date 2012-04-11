package anubis.ast;

/**
 * @author SiroKuro
 */
public class GetSlotExpression implements Expression {
	private final Expression expr;
	private final String name;
	
	public GetSlotExpression(Expression expr, String name) {
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
	public String getName() {
		return name;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
	
}
