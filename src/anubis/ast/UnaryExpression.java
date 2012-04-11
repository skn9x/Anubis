package anubis.ast;

/**
 * @author SiroKuro
 */
public class UnaryExpression implements Expression {
	private final UnaryOperator operator;
	private final Expression expr;
	
	public UnaryExpression(UnaryOperator operator, Expression expr) {
		assert operator != null;
		assert expr != null;
		this.operator = operator;
		this.expr = expr;
	}
	
	/**
	 * @return the expr
	 */
	public Expression getExpr() {
		return expr;
	}
	
	/**
	* @return the operator
	*/
	public UnaryOperator getOperator() {
		return operator;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
	
}
