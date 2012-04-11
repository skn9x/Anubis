package anubis.ast;

public class NewRangeExpression implements Expression {
	private final Expression start;
	private final Expression end;
	private final Expression step;
	
	public NewRangeExpression(Expression start, Expression end, Expression step) {
		assert start != null;
		this.start = start;
		this.end = end;
		this.step = step;
	}
	
	public Expression getEnd() {
		return end;
	}
	
	public Expression getStart() {
		return start;
	}
	
	public Expression getStep() {
		return step;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
}
