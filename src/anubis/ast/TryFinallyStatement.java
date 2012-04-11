package anubis.ast;

public class TryFinallyStatement extends LabelStatement {
	private final Statement _try;
	private final Statement _finally;
	
	public TryFinallyStatement(Statement _try, Statement _finally) {
		this(null, _try, _finally);
	}
	
	public TryFinallyStatement(String label, Statement _try, Statement _finally) {
		super(label);
		assert _try != null;
		assert _finally != null;
		this._try = _try;
		this._finally = _finally;
	}
	
	public Statement getFinally() {
		return _finally;
	}
	
	public Statement getTry() {
		return _try;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
}
