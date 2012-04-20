package anubis.ast;

/**
 * @author SiroKuro
 */
public class LocalExpression extends Expression {
	public static final LocalExpression INSTANCE = new LocalExpression();
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
	
}
