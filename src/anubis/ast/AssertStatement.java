package anubis.ast;

public class AssertStatement extends LabelStatement {
	private final Expression condition;
	private final Statement _else;
	
	public AssertStatement(Expression condition) {
		this(null, condition, null);
	}
	
	public AssertStatement(String label, Expression condition) {
		this(label, condition, null);
	}
	
	public AssertStatement(String label, Expression condition, Statement _else) {
		super(label);
		assert condition != null;
		this.condition = condition;
		this._else = _else;
	}
	
	public Expression getCondition() {
		return condition;
	}
	
	public Statement getElse() {
		return _else;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
	
}
