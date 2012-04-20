package anubis.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewFunctionExpression extends Expression {
	private final List<String> argNames;
	private final Statement statement;
	
	public NewFunctionExpression(List<String> argNames, Statement statement) {
		assert argNames != null;
		assert statement != null;
		this.argNames = Collections.unmodifiableList(new ArrayList<String>(argNames));
		this.statement = statement;
	}
	
	public List<String> getArgNames() {
		return argNames;
	}
	
	public Statement getStatement() {
		return statement;
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
	
}
