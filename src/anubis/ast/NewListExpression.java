package anubis.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NewListExpression extends Expression {
	private final List<Expression> exprs;
	
	public NewListExpression(Expression... exprs) {
		this(Arrays.asList(exprs));
	}
	
	public NewListExpression(List<Expression> exprs) {
		this.exprs = Collections.unmodifiableList(exprs);
	}
	
	public List<Expression> getExprs() {
		return exprs;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
	
}
