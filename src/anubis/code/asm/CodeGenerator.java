package anubis.code.asm;

import anubis.ast.AssertStatement;
import anubis.ast.AstVisitor;
import anubis.ast.BinaryExpression;
import anubis.ast.BlockExpression;
import anubis.ast.BlockStatement;
import anubis.ast.BreakStatement;
import anubis.ast.CallExpression;
import anubis.ast.CompilationUnit;
import anubis.ast.ContinueStatement;
import anubis.ast.EmptyStatement;
import anubis.ast.ExpressionStatement;
import anubis.ast.ForStatement;
import anubis.ast.GetSlotExpression;
import anubis.ast.GetSpecialExpression;
import anubis.ast.IfStatement;
import anubis.ast.LocalExpression;
import anubis.ast.LockStatement;
import anubis.ast.NewFunctionExpression;
import anubis.ast.NewListExpression;
import anubis.ast.NewMapExpression;
import anubis.ast.NewObjectExpression;
import anubis.ast.NewRangeExpression;
import anubis.ast.NewSetExpression;
import anubis.ast.Node;
import anubis.ast.Position;
import anubis.ast.PrimitiveExpression;
import anubis.ast.ReturnStatement;
import anubis.ast.SwitchStatement;
import anubis.ast.TernaryExpression;
import anubis.ast.ThrowStatement;
import anubis.ast.TryCatchStatement;
import anubis.ast.TryFinallyStatement;
import anubis.ast.UnaryExpression;
import anubis.ast.UsingStatement;
import anubis.ast.WhileStatement;
import anubis.code.Option;

public class CodeGenerator implements AstVisitor<CodeBuilder, CodeBuilder> {
	private final ExpressionEmitter emit_expr = new ExpressionEmitter(this);
	private final StatementEmitter emit_stmt = new StatementEmitter(this);
	private final AsmCodeBlockFactory owner;
	private final Option option;
	
	public CodeGenerator(AsmCodeBlockFactory owner, Option option) {
		this.owner = owner;
		this.option = option;
	}
	
	@Override
	public CodeBuilder accept(AssertStatement stmt, CodeBuilder builder) {
		mark(builder, stmt);
		emit_stmt.emit(builder, stmt);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(BinaryExpression expr, CodeBuilder builder) {
		mark(builder, expr);
		emit_expr.emit(builder, expr);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(BlockExpression expr, CodeBuilder builder) {
		mark(builder, expr);
		emit_expr.emit(builder, expr);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(BlockStatement stmt, CodeBuilder builder) {
		mark(builder, stmt);
		emit_stmt.emit(builder, stmt);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(BreakStatement stmt, CodeBuilder builder) {
		mark(builder, stmt);
		emit_stmt.emit(builder, stmt);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(CallExpression expr, CodeBuilder builder) {
		mark(builder, expr);
		emit_expr.emit(builder, expr);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(ContinueStatement stmt, CodeBuilder builder) {
		mark(builder, stmt);
		emit_stmt.emit(builder, stmt);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(EmptyStatement stmt, CodeBuilder builder) {
		mark(builder, stmt);
		emit_stmt.emit(builder, stmt);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(ExpressionStatement stmt, CodeBuilder builder) {
		mark(builder, stmt);
		emit_stmt.emit(builder, stmt);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(ForStatement stmt, CodeBuilder builder) {
		mark(builder, stmt);
		emit_stmt.emit(builder, stmt);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(GetSlotExpression expr, CodeBuilder builder) {
		mark(builder, expr);
		emit_expr.emit(builder, expr);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(GetSpecialExpression expr, CodeBuilder builder) {
		mark(builder, expr);
		emit_expr.emit(builder, expr);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(IfStatement stmt, CodeBuilder builder) {
		mark(builder, stmt);
		emit_stmt.emit(builder, stmt);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(LocalExpression expr, CodeBuilder builder) {
		mark(builder, expr);
		emit_expr.emit(builder, expr);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(LockStatement stmt, CodeBuilder builder) {
		mark(builder, stmt);
		emit_stmt.emit(builder, stmt);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(NewFunctionExpression expr, CodeBuilder builder) {
		mark(builder, expr);
		emit_expr.emit(builder, expr);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(NewListExpression expr, CodeBuilder builder) {
		mark(builder, expr);
		emit_expr.emit(builder, expr);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(NewMapExpression expr, CodeBuilder builder) {
		mark(builder, expr);
		emit_expr.emit(builder, expr);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(NewObjectExpression expr, CodeBuilder builder) {
		mark(builder, expr);
		emit_expr.emit(builder, expr);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(NewRangeExpression expr, CodeBuilder builder) {
		mark(builder, expr);
		emit_expr.emit(builder, expr);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(NewSetExpression expr, CodeBuilder builder) {
		mark(builder, expr);
		emit_expr.emit(builder, expr);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(PrimitiveExpression expr, CodeBuilder builder) {
		mark(builder, expr);
		emit_expr.emit(builder, expr);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(ReturnStatement stmt, CodeBuilder builder) {
		mark(builder, stmt);
		emit_stmt.emit(builder, stmt);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(SwitchStatement stmt, CodeBuilder builder) {
		mark(builder, stmt);
		emit_stmt.emit(builder, stmt);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(TernaryExpression expr, CodeBuilder builder) {
		mark(builder, expr);
		emit_expr.emit(builder, expr);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(ThrowStatement stmt, CodeBuilder builder) {
		mark(builder, stmt);
		emit_stmt.emit(builder, stmt);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(TryCatchStatement stmt, CodeBuilder builder) {
		mark(builder, stmt);
		emit_stmt.emit(builder, stmt);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(TryFinallyStatement stmt, CodeBuilder builder) {
		mark(builder, stmt);
		emit_stmt.emit(builder, stmt);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(UnaryExpression expr, CodeBuilder builder) {
		mark(builder, expr);
		emit_expr.emit(builder, expr);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(UsingStatement stmt, CodeBuilder builder) {
		mark(builder, stmt);
		emit_stmt.emit(builder, stmt);
		return builder;
	}
	
	@Override
	public CodeBuilder accept(WhileStatement stmt, CodeBuilder builder) {
		mark(builder, stmt);
		emit_stmt.emit(builder, stmt);
		return builder;
	}
	
	public byte[] generate(CompilationUnit node, String className) {
		Position pos = node.getPos();
		CodeBuilder builder = node.visit(this, new CodeBuilder(className, pos == null ? null : pos.getFilename()));
		return builder.finallize();
	}
	
	public Option getOption() {
		return option;
	}
	
	public Class<?> newCodeBlockClass(CompilationUnit node) {
		return owner.newCodeBlockClass(node, getOption());
	}
	
	private void mark(CodeBuilder builder, Node node) {
		Position pos = node.getPos();
		if (pos != null) {
			builder.mark(pos.getFilename(), pos.getLine());
		}
	}
}
