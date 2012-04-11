package anubis.ast;

public class SetIndexLeftValue extends LeftValue {
	private final Expression index;
	
	public SetIndexLeftValue(Expression expr, Expression index) {
		super(expr);
		assert index != null;
		this.index = index;
	}
	
	/**
	 * @return the index
	 */
	public Expression getIndex() {
		return index;
	}
}
