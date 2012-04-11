package anubis.ast;

/**
 * @author SiroKuro
 */
public interface Node {
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object);
}
