package anubis.ast;

public abstract class LabelStatement implements Statement {
	private final String label;
	
	public LabelStatement(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
