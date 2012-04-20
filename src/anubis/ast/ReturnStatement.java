package anubis.ast;

public class ReturnStatement extends Statement {
	private final Expression expr;
	
	public ReturnStatement() {
		this(null);
	}
	
	public ReturnStatement(Expression expr) {
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
