package anubis.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SwitchStatement extends LabelStatement {
	private final Expression condition;
	private final List<CaseElement> cases;
	private final Statement _else;
	
	public SwitchStatement(String label, Expression condition, List<CaseElement> cases, Statement _else) {
		super(label);
		assert condition != null;
		assert cases != null;
		assert _else != null;
		this.condition = condition;
		this.cases = Collections.unmodifiableList(new ArrayList<CaseElement>(cases));
		this._else = _else;
	}
	
	public List<CaseElement> getCases() {
		return cases;
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
