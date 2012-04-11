package anubis.ast;

public class AssertStatement implements Statement {
	private final Expression assertion;
	private final Statement _else;
	
	public AssertStatement(Expression assertion) {
		this(assertion, null);
	}
	
	public AssertStatement(Expression assertion, Statement _else) {
		super();
		assert assertion != null;
		this.assertion = assertion;
		this._else = _else;
	}
	
	public Expression getAssertion() {
		return assertion;
	}
	
	public Statement getElse() {
		return _else;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
	
}
