package anubis.ast;

public class ForStatement extends LabelStatement {
	private final String varname;
	private final Expression condition;
	private final Statement _then;
	private final Statement _else;
	
	public ForStatement(String varname, Expression condition, Statement _then, Statement _else) {
		this(null, varname, condition, _then, _else);
	}
	
	public ForStatement(String label, String varname, Expression condition, Statement _then, Statement _else) {
		super(label);
		assert varname != null;
		assert condition != null;
		assert _then != null;
		assert _else != null;
		this.varname = varname;
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
	
	public String getVarname() {
		return varname;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
}
