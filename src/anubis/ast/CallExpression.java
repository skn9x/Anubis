package anubis.ast;

/**
 * @author SiroKuro
 */
public class CallExpression implements Expression {
	private final Expression expr;
	private final String funcName;
	private final CallArgument args;
	
	public CallExpression(Expression expr, String funcName) {
		this(expr, funcName, null);
	}
	
	public CallExpression(Expression expr, String funcName, CallArgument args) {
		assert expr != null;
		assert funcName != null;
		assert args != null;
		this.expr = expr;
		this.funcName = funcName;
		this.args = args != null ? args : new CallArgument(null);
	}
	
	/**
	 * @return the args
	 */
	public CallArgument getArgs() {
		return args;
	}
	
	/**
	 * @return the expr
	 */
	public Expression getExpr() {
		return expr;
	}
	
	/**
	 * @return the funcName
	 */
	public String getFuncName() {
		return funcName;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
}
