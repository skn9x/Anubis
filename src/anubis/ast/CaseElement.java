package anubis.ast;

import anubis.runtime.APrimitive;

public class CaseElement {
	private final APrimitive value;
	private final Statement _then;
	
	public CaseElement(APrimitive value, Statement _then) {
		assert value != null;
		assert _then != null;
		this.value = value;
		this._then = _then;
	}
	
	public Statement getThen() {
		return _then;
	}
	
	public APrimitive getValue() {
		return value;
	}
}
