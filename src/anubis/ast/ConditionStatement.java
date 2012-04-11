package anubis.ast;

public class ConditionStatement extends LabelStatement {
	private final Expression condition;
	private final Statement _then;
	private final Statement _else;
	
	public ConditionStatement(Expression condition, Statement _then, Statement _else) {
		this(null, condition, _then, _else);
	}
	
	public ConditionStatement(String label, Expression condition, Statement _then, Statement _else) {
		super(label);
		assert condition != null;
		assert _then != null;
		assert _else != null;
		this.condition = condition;
		this._then = _then;
		this._else = _else;
	}
	
	public Expression getCondition() {
		return condition;
	}
	
	public Statement getElse() {
		return _else;
	}
	
	public Statement getThen() {
		return _then;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
}
