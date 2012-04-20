package anubis.ast;

public class NewObjectExpression extends Expression {
	private final Statement statement;
	
	public NewObjectExpression(Statement statement) {
		assert statement != null;
		this.statement = statement;
	}
	
	public Statement getStatement() {
		return statement;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
	
}
