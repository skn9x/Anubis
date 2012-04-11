package anubis.ast;

public class BlockExpression implements Expression {
	private final Statement stmt;
	
	public BlockExpression(Statement stmt) {
		assert stmt != null;
		this.stmt = stmt;
	}
	
	public Statement getStatement() {
		return stmt;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
}
