package anubis.ast;

public class ContinueStatement implements Statement {
	private final String label;
	
	public ContinueStatement() {
		this(null);
	}
	
	public ContinueStatement(String label) {
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
