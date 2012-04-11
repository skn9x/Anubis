package anubis.ast;

/**
 * @author SiroKuro
 */
public interface AstVisitor<Arg, Ret> {
	public Ret accept(AssertStatement stmt, Arg object);
	
	public Ret accept(BinaryExpression expr, Arg object);
	
	public Ret accept(BlockExpression expr, Arg object);
	
	public Ret accept(BlockStatement stmt, Arg object);
	
	public Ret accept(BreakStatement stmt, Arg object);
	
	public Ret accept(CallExpression expr, Arg object);
	
	public Ret accept(ConditionStatement stmt, Arg object);
	
	public Ret accept(ContinueStatement stmt, Arg object);
	
	public Ret accept(EmptyStatement stmt, Arg object);
	
	public Ret accept(ExpressionStatement stmt, Arg object);
	
	public Ret accept(ForStatement stmt, Arg object);
	
	public Ret accept(GetSlotExpression expr, Arg object);
	
	public Ret accept(GetSpecialExpression expr, Arg object);
	
	public Ret accept(LocalExpression expr, Arg object);
	
	public Ret accept(NewFunctionExpression expr, Arg object);
	
	public Ret accept(NewListExpression expr, Arg object);
	
	public Ret accept(NewMapExpression expr, Arg object);
	
	public Ret accept(NewObjectExpression expr, Arg object);
	
	public Ret accept(NewRangeExpression expr, Arg object);
	
	public Ret accept(NewSetExpression expr, Arg object);
	
	public Ret accept(PrimitiveExpression expr, Arg object);
	
	public Ret accept(ReturnStatement stmt, Arg object);
	
	public Ret accept(SwitchStatement stmt, Arg object);
	
	public Ret accept(TernaryExpression expr, Arg object);
	
	public Ret accept(ThrowStatement stmt, Arg object);
	
	public Ret accept(TryCatchStatement stmt, Arg object);
	
	public Ret accept(TryFinallyStatement stmt, Arg object);
	
	public Ret accept(UnaryExpression expr, Arg object);
	
	public Ret accept(WhileStatement stmt, Arg object);
}
