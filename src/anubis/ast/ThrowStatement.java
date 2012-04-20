package anubis.ast;

public class ThrowStatement extends Statement {
	private final Expression expr;
	
	public ThrowStatement() {
		this(null);
	}
	
	public ThrowStatement(Expression expr) {
		this.expr = expr;
	}
	
	public Expression getExpression() {
		return expr;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
	
}
