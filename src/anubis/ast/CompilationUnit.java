package anubis.ast;

/**
 * @author SiroKuro
 */
public abstract class CompilationUnit extends Node {
	public abstract <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object);
}
