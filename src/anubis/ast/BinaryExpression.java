package anubis.ast;

/**
 * @author SiroKuro
 */
public class BinaryExpression extends Expression {
	private final Expression left;
	private final BinaryOperator operator;
	private final Expression right;
	
	public BinaryExpression(BinaryOperator operator, Expression left, Expression right) {
		assert operator != null;
		assert left != null;
		assert right != null;
		this.operator = operator;
		this.left = left;
		this.right = right;
	}
	
	/**
	* @return the left
	*/
	public Expression getLeft() {
		return left;
	}
	
	/**
	 * @return the operator
	 */
	public BinaryOperator getOperator() {
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
