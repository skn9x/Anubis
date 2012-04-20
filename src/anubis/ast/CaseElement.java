package anubis.ast;

public class CaseElement extends Node {
	private final Expression value;
	private final Statement _then;
	
	public CaseElement(Expression value, Statement _then) {
		assert value != null;
		assert _then != null;
		this.value = value;
		this._then = _then;
	}
	
	public Statement getThen() {
		return _then;
	}
	
	public Expression getValue() {
		return value;
	}
}
