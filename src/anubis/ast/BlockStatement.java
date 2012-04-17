package anubis.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockStatement extends LabelStatement {
	private final List<Statement> body;
	
	public BlockStatement() {
		this(null, new ArrayList<Statement>());
	}
	
	public BlockStatement(List<Statement> body) {
		this(null, body);
	}
	
	public BlockStatement(String label) {
		this(label, new ArrayList<Statement>());
	}
	
	public BlockStatement(String label, List<Statement> body) {
		super(label);
		assert body != null;
		this.body = body;
	}
	
	public BlockStatement append(Statement stmt) {
		body.add(stmt);
		return this;
	}
	
	public List<Statement> getBody() {
		return Collections.unmodifiableList(body);
	}
	
	@Override
	public <Arg, Ret> Ret visit(AstVisitor<Arg, Ret> visitor, Arg object) {
		return visitor.accept(this, object);
	}
}
