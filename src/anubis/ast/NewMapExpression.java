package anubis.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import anubis.runtime.util.Pair;

public class NewMapExpression extends Expression {
	private final List<Pair<Expression, Expression>> exprs;
	
	public NewMapExpression(List<Pair<Expression, Expression>> exprs) {
		this.exprs = Collections.unmodifiableList(exprs);
	}
	
	public NewMapExpression(Pair<Expression, Expression>... exprs) {
		this(Arrays.asList(exprs));
	}
	
	public List<Pair<Expression, Expression>> getExprs() {
		return exprs;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
	
}
