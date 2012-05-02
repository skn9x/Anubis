package anubis.code.asm;

import java.util.Iterator;
import anubis.AnubisObject;
import anubis.ast.AssertStatement;
import anubis.ast.BlockStatement;
import anubis.ast.BreakStatement;
import anubis.ast.CaseElement;
import anubis.ast.ContinueStatement;
import anubis.ast.EmptyStatement;
import anubis.ast.Expression;
import anubis.ast.ExpressionStatement;
import anubis.ast.ForStatement;
import anubis.ast.IfStatement;
import anubis.ast.LockStatement;
import anubis.ast.ReturnStatement;
import anubis.ast.Statement;
import anubis.ast.SwitchStatement;
import anubis.ast.ThrowStatement;
import anubis.ast.TryCatchStatement;
import anubis.ast.TryFinallyStatement;
import anubis.ast.UsingStatement;
import anubis.ast.WhileStatement;
import anubis.runtime.Operator;

public class StatementEmitter {
	private final CodeGenerator owner;
	private final BlockEmitter block = new BlockEmitter();
	
	public StatementEmitter(CodeGenerator owner) {
		this.owner = owner;
	}
	
	public void emit(CodeBuilder builder, AssertStatement stmt) {
		if (owner.getOption().isDisableAssertion()) {
			return;
		}
		Label _END = builder.newLabel();
		// condition
		stmt.getCondition().visit(owner, builder);
		builder.emitInvoke(Operator.class, "isTrue", AnubisObject.class);
		builder.emitIfTrue(_END);
		// else
		if (stmt.getElse() == null) {
			builder.emitInvoke(Operator.class, "fail");
		}
		else {
			block.startWeakBlock(stmt.getLabel(), _END, _END);
			try {
				stmt.getElse().visit(owner, builder);
			}
			finally {
				block.endBlock();
			}
		}
		builder.emitLabel(_END);
	}
	
	public void emit(CodeBuilder builder, BlockStatement stmt) {
		Label _END = builder.newLabel();
		block.startWeakBlock(stmt.getLabel(), _END, _END);
		try {
			for (Statement stmt2: stmt.getBody()) {
				stmt2.visit(owner, builder);
			}
		}
		finally {
			block.endBlock();
		}
		builder.emitLabel(_END);
	}
	
	public void emit(CodeBuilder builder, BreakStatement stmt) {
		block.emitBreak(builder, stmt.getLabel());
	}
	
	public void emit(CodeBuilder builder, ContinueStatement stmt) {
		block.emitContinue(builder, stmt.getLabel());
	}
	
	public void emit(CodeBuilder builder, EmptyStatement stmt) {
		// nop
	}
	
	public void emit(CodeBuilder builder, ExpressionStatement stmt) {
		stmt.getExpression().visit(owner, builder);
		builder.emitPop();
	}
	
	public void emit(CodeBuilder builder, final ForStatement stmt) {
		final int var = builder.allocLv();
		try {
			stmt.getCondition().visit(owner, builder);
			builder.emitInvoke(Operator.class, "getIterator", AnubisObject.class);
			builder.emitStoreLocalVar(var);
			new TryFinallyEmitter(block, null) {
				@Override
				public void emitFinally(CodeBuilder builder) {
					builder.pushNull();
					builder.emitStoreLocalVar(var);
				}
				
				@Override
				public void emitTry(CodeBuilder builder) {
					Label _LOOP = builder.newLabel();
					Label _ELSE = builder.newLabel();
					Label _END = builder.newLabel();
					
					// condition
					builder.emitLabel(_LOOP);
					builder.pushLocalVar(var);
					builder.emitInvoke(Iterator.class, "hasNext");
					builder.emitIfFalse(_ELSE);
					// setSlot
					builder.pushLocal();
					builder.pushString(stmt.getVarname());
					builder.pushLocalVar(var);
					builder.emitInvoke(Iterator.class, "next");
					builder.emitCheckCast(AnubisObject.class);
					builder.emitInvoke(AnubisObject.class, "setSlot", String.class, AnubisObject.class);
					// body
					block.startStrongBlock(stmt.getLabel(), _END, _LOOP);
					try {
						stmt.getThen().visit(owner, builder);
						builder.emitGoto(_LOOP);
					}
					finally {
						block.endBlock();
					}
					// else
					block.startStrongBlock(stmt.getLabel(), _END, _END);
					try {
						builder.emitLabel(_ELSE);
						stmt.getElse().visit(owner, builder);
					}
					finally {
						block.endBlock();
					}
					// end
					builder.emitLabel(_END);
				}
			}.emit(builder);
		}
		finally {
			builder.freeLv();
		}
	}
	
	public void emit(CodeBuilder builder, IfStatement stmt) {
		Label _ELSE = builder.newLabel();
		Label _END = builder.newLabel();
		// cond
		stmt.getCondition().visit(owner, builder);
		builder.emitInvoke(Operator.class, "isTrue", AnubisObject.class);
		builder.emitIfFalse(_ELSE);
		// then
		block.startWeakBlock(stmt.getLabel(), _END, _END);
		try {
			stmt.getThen().visit(owner, builder);
			builder.emitGoto(_END);
		}
		finally {
			block.endBlock();
		}
		// else
		block.startWeakBlock(stmt.getLabel(), _END, _END);
		try {
			builder.emitLabel(_ELSE);
			stmt.getElse().visit(owner, builder);
		}
		finally {
			block.endBlock();
		}
		builder.emitLabel(_END);
	}
	
	public void emit(CodeBuilder builder, final LockStatement stmt) {
		final int var = builder.allocLv();
		try {
			// expr
			stmt.getExpression().visit(owner, builder);
			// TODO null チェック
			builder.emitDup();
			builder.emitStoreLocalVar(var);
			builder.emitMonitorEnter();
			// body
			new TryFinallyEmitter(block, stmt.getLabel()) {
				@Override
				public void emitFinally(CodeBuilder builder) {
					builder.pushLocalVar(var);
					builder.emitMonitorExit();
				}
				
				@Override
				public void emitTry(CodeBuilder builder) {
					stmt.getBody().visit(owner, builder);
				}
			}.emit(builder);
		}
		finally {
			builder.freeLv();
		}
	}
	
	public void emit(CodeBuilder builder, ReturnStatement stmt) {
		Expression expr = stmt.getExpression();
		if (expr == null)
			builder.pushNull();
		else
			expr.visit(owner, builder);
		builder.emitReturn();
	}
	
	public void emit(CodeBuilder builder, final SwitchStatement stmt) {
		final int var = builder.allocLv();
		try {
			stmt.getCondition().visit(owner, builder);
			builder.emitStoreLocalVar(var);
			new TryFinallyEmitter(block, null) {
				@Override
				public void emitFinally(CodeBuilder builder) {
					builder.pushNull();
					builder.emitStoreLocalVar(var);
				}
				
				@Override
				public void emitTry(CodeBuilder builder) {
					Label _END = builder.newLabel();
					// body
					for (CaseElement elm: stmt.getCases()) {
						Label _ELSE = builder.newLabel();
						block.startWeakBlock(stmt.getLabel(), _END, _ELSE);
						try {
							// condition
							builder.pushLocalVar(var);
							elm.getValue().visit(owner, builder);
							builder.emitInvoke(Operator.class, "opEquals", AnubisObject.class, AnubisObject.class);
							builder.emitIfFalse(_ELSE);
							// then
							elm.getThen().visit(owner, builder);
							builder.emitGoto(_END);
							builder.emitLabel(_ELSE);
						}
						finally {
							block.endBlock();
						}
					}
					// else
					block.startWeakBlock(stmt.getLabel(), _END, _END);
					try {
						stmt.getElse().visit(owner, builder);
					}
					finally {
						block.endBlock();
					}
					builder.emitLabel(_END);
				}
			}.emit(builder);
		}
		finally {
			builder.freeLv();
		}
	}
	
	public void emit(CodeBuilder builder, ThrowStatement stmt) {
		Expression expr = stmt.getExpression();
		if (expr == null)
			builder.pushNull();
		else
			expr.visit(owner, builder);
		builder.emitInvoke(Operator.class, "wrapException", AnubisObject.class);
		builder.emitThrow();
	}
	
	public void emit(CodeBuilder builder, TryCatchStatement stmt) {
		Label _TRY = builder.newLabel();
		Label _TRY_END = builder.newLabel();
		Label _CATCH = builder.newLabel();
		Label _END = builder.newLabel();
		
		// try
		block.startWeakBlock(stmt.getLabel(), _END, _END);
		try {
			builder.emitLabel(_TRY);
			stmt.getTry().visit(owner, builder);
			builder.emitLabel(_TRY_END);
			builder.emitGoto(_END);
		}
		finally {
			block.endBlock();
		}
		// catch
		block.startWeakBlock(stmt.getLabel(), _END, _END);
		try {
			builder.emitLabel(_CATCH);
			// ex, local
			builder.pushLocal();
			// local, ex
			builder.emitSwap();
			// local, obj
			builder.emitCheckCast(Throwable.class);
			builder.emitInvoke(Operator.class, "unwrapException", Throwable.class);
			// local, obj, name
			builder.pushString(stmt.getVarname());
			// local, name, obj
			builder.emitSwap();
			// 
			builder.emitInvoke(AnubisObject.class, "setSlot", String.class, AnubisObject.class);
			stmt.getCatch().visit(owner, builder);
		}
		finally {
			block.endBlock();
		}
		// end
		builder.emitLabel(_END);
		builder.emitTryBlock(_TRY, _TRY_END, _CATCH, Exception.class);
	}
	
	public void emit(CodeBuilder builder, final TryFinallyStatement stmt) {
		new TryFinallyEmitter(block, stmt.getLabel()) {
			@Override
			public void emitFinally(CodeBuilder builder) {
				stmt.getFinally().visit(owner, builder);
			}
			
			@Override
			public void emitTry(CodeBuilder builder) {
				stmt.getTry().visit(owner, builder);
			}
		}.emit(builder);
	}
	
	public void emit(CodeBuilder builder, final UsingStatement stmt) {
		final int var = builder.allocLv();
		try {
			// expr
			stmt.getExpression().visit(owner, builder);
			// TODO null チェック
			builder.emitStoreLocalVar(var);
			// body
			new TryFinallyEmitter(block, stmt.getLabel()) {
				@Override
				public void emitFinally(CodeBuilder builder) {
					builder.pushLocalVar(var);
					builder.emitInvoke(Operator.class, "opClose", AnubisObject.class);
					builder.emitPop();
				}
				
				@Override
				public void emitTry(CodeBuilder builder) {
					stmt.getBody().visit(owner, builder);
				}
			}.emit(builder);
		}
		finally {
			builder.freeLv();
		}
	}
	
	public void emit(CodeBuilder builder, WhileStatement stmt) {
		Label _ELSE = builder.newLabel();
		Label _LOOP = builder.newLabel();
		Label _END = builder.newLabel();
		
		builder.emitLabel(_LOOP);
		// cond
		stmt.getCondition().visit(owner, builder);
		builder.emitInvoke(Operator.class, "isTrue", AnubisObject.class);
		builder.emitIfFalse(_ELSE);
		// then
		block.startStrongBlock(stmt.getLabel(), _END, _LOOP);
		try {
			stmt.getThen().visit(owner, builder);
			builder.emitGoto(_LOOP);
		}
		finally {
			block.endBlock();
		}
		// else
		block.startStrongBlock(stmt.getLabel(), _END, _END);
		try {
			builder.emitLabel(_ELSE);
			stmt.getElse().visit(owner, builder);
		}
		finally {
			block.endBlock();
		}
		// end
		builder.emitLabel(_END);
	}
}
