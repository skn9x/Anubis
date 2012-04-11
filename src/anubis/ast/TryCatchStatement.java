package anubis.ast;

public class TryCatchStatement extends LabelStatement {
	private final Statement _try;
	private final String varname;
	private final Statement _catch;
	
	public TryCatchStatement(Statement _try, String varname, Statement _catch) {
		this(null, _try, varname, _catch);
	}
	
	public TryCatchStatement(String label, Statement _try, String varname, Statement _catch) {
		super(label);
		assert _try != null;
		assert varname != null;
		assert _catch != null;
		this._try = _try;
		this.varname = varname;
		this._catch = _catch;
	}
	
	public Statement getCatch() {
		return _catch;
	}
	
	public Statement getTry() {
		return _try;
	}
	
	public String getVarname() {
		return varname;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
}
