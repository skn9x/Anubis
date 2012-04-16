package anubis.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockStatement extends LabelStatement {
	private final List<Statement> statements;
	
	public BlockStatement() {
		this(null, new ArrayList<Statement>());
	}
	
	public BlockStatement(List<Statement> statements) {
		this(null, statements);
	}
	
	public BlockStatement(String label) {
		this(label, new ArrayList<Statement>());
	}
	
	public BlockStatement(String label, List<Statement> statements) {
		super(label);
		assert statements != null;
		this.statements = statements;
	}
	
	public BlockStatement append(Statement stmt) {
		statements.add(stmt);
		return this;
	}
	
	public List<Statement> getInner() {
		return Collections.unmodifiableList(statements);
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
}
