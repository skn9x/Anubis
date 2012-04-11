package anubis.ast;

public class EmptyStatement implements Statement {
	public static final EmptyStatement INSTANCE = new EmptyStatement();
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
}
