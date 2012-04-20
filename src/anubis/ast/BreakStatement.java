package anubis.ast;

public class BreakStatement extends Statement {
	private final String label;
	
	public BreakStatement() {
		this(null);
	}
	
	public BreakStatement(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
	
}
