package anubis.code.asm;

import anubis.AnubisObject;
import anubis.ast.AssertStatement;
import anubis.ast.BlockStatement;
import anubis.ast.BreakStatement;
import anubis.ast.ConditionStatement;
import anubis.ast.ContinueStatement;
import anubis.ast.EmptyStatement;
import anubis.ast.Expression;
import anubis.ast.ExpressionStatement;
import anubis.ast.ForStatement;
import anubis.ast.ReturnStatement;
import anubis.ast.Statement;
import anubis.ast.SwitchStatement;
import anubis.ast.ThrowStatement;
import anubis.ast.TryCatchStatement;
import anubis.ast.TryFinallyStatement;
import anubis.ast.WhileStatement;
import anubis.runtime.Operator;

public class StatementEmitter {
	private final CodeGenerator owner;
	
	public StatementEmitter(CodeGenerator owner) {
		this.owner = owner;
	}
	
	public void emit(CodeBuilder builder, AssertStatement stmt) { // TODO デバッグモード対応
		Label L01 = builder.newLabel();
		stmt.getAssertion().visit(owner, builder);
		builder.emitInvoke(Operator.class, "isTrue", AnubisObject.class);
		builder.emitIfTrue(L01);
		if (stmt.getElse() == null) {
			builder.emitInvoke(Operator.class, "fail");
		}
		else {
			stmt.getElse().visit(owner, builder);
		}
		builder.emitLabel(L01);
	}
	
	public void emit(CodeBuilder builder, BlockStatement stmt) {
		for (Statement stmt2: stmt.getInner()) {
			stmt2.visit(owner, builder);
		}
	}
	
	public void emit(CodeBuilder builder, BreakStatement stmt) {
		// TODO Auto-generated method stub
		return;
	}
	
	public void emit(CodeBuilder builder, ConditionStatement stmt) {
		// TODO Auto-generated method stub
		return;
	}
	
	public void emit(CodeBuilder builder, ContinueStatement stmt) {
		// TODO Auto-generated method stub
		return;
	}
	
	public void emit(CodeBuilder builder, EmptyStatement stmt) {
		// nop
		return;
	}
	
	public void emit(CodeBuilder builder, ExpressionStatement stmt) {
		stmt.getExpr().visit(owner, builder).emitPop();
		return;
	}
	
	public void emit(CodeBuilder builder, ForStatement stmt) {
		// TODO Auto-generated method stub
		return;
	}
	
	public void emit(CodeBuilder builder, ReturnStatement stmt) {
		Expression expr = stmt.getExpression();
		if (expr == null)
			builder.pushNull();
		else
			expr.visit(owner, builder);
		builder.emitReturn();
	}
	
	public void emit(CodeBuilder builder, SwitchStatement stmt) {
		// TODO Auto-generated method stub
		return;
	}
	
	public void emit(CodeBuilder builder, ThrowStatement stmt) {
		// TODO Auto-generated method stub
		return;
	}
	
	public void emit(CodeBuilder builder, TryCatchStatement stmt) {
		// TODO Auto-generated method stub
		return;
	}
	
	public void emit(CodeBuilder builder, TryFinallyStatement stmt) {
		// TODO Auto-generated method stub
		return;
	}
	
	public void emit(CodeBuilder builder, WhileStatement stmt) {
		// TODO Auto-generated method stub
		return;
	}
	
}
