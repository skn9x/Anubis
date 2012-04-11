package anubis.ast;

public abstract class LeftValue {
	private final Expression expr;
	
	protected LeftValue(Expression expr) {
		assert expr != null;
		this.expr = expr;
	}
	
	public Expression getExpr() {
		return expr;
	}
}
