package anubis.ast;

/**
 * @author SiroKuro
 */
public class ExpressionStatement implements Statement {
	private final Expression expr;
	
	public ExpressionStatement(Expression expr) {
		assert expr != null;
		this.expr = expr;
	}
	
	/**
	 * @return the expr
	 */
	public Expression getExpr() {
		return expr;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
	
}
