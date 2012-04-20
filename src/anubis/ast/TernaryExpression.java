package anubis.ast;

/**
 * @author SiroKuro
 */
public class TernaryExpression extends Expression {
	private final TernaryOperator operator;
	private final Expression left, middle, right;
	
	public TernaryExpression(TernaryOperator operator, Expression left, Expression middle, Expression right) {
		assert operator != null;
		assert left != null;
		assert middle != null;
		assert right != null;
		this.operator = operator;
		this.left = left;
		this.middle = middle;
		this.right = right;
	}
	
	/**
	 * @return the left
	 */
	public Expression getLeft() {
		return left;
	}
	
	/**
	 * @return the middle
	 */
	public Expression getMiddle() {
		return middle;
	}
	
	/**
	 * @return the operator
	 */
	public TernaryOperator getOperator() {
		return operator;
	}
	
	/**
	 * @return the right
	 */
	public Expression getRight() {
		return right;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
}
