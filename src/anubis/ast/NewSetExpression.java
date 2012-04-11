package anubis.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NewSetExpression implements Expression {
	private final List<Expression> exprs;
	
	public NewSetExpression(Expression... exprs) {
		this(Arrays.asList(exprs));
	}
	
	public NewSetExpression(List<Expression> exprs) {
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
