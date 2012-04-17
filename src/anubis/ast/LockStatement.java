package anubis.ast;

public class LockStatement extends LabelStatement {
	private final Expression expr;
	private final Statement body;
	
	public LockStatement(String label, Expression expr, Statement body) {
		super(label);
		assert expr != null;
		assert body != null;
		this.expr = expr;
		this.body = body;
	}
	
	public Statement getBody() {
		return body;
	}
	
	public Expression getExpression() {
		return expr;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
	
}
