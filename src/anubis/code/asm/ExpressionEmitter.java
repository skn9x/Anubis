package anubis.code.asm;

import java.util.List;
import anubis.AnubisObject;
import anubis.SpecialSlot;
import anubis.ast.BinaryExpression;
import anubis.ast.BlockExpression;
import anubis.ast.CallArgument;
import anubis.ast.CallExpression;
import anubis.ast.Expression;
import anubis.ast.GetSlotExpression;
import anubis.ast.GetSpecialExpression;
import anubis.ast.LocalExpression;
import anubis.ast.NewFunctionExpression;
import anubis.ast.NewListExpression;
import anubis.ast.NewMapExpression;
import anubis.ast.NewObjectExpression;
import anubis.ast.NewRangeExpression;
import anubis.ast.NewSetExpression;
import anubis.ast.PrimitiveExpression;
import anubis.ast.TernaryExpression;
import anubis.ast.UnaryExpression;
import anubis.code.CodeBlock;
import anubis.except.BugIsHereException;
import anubis.runtime.AFalseObject;
import anubis.runtime.AList;
import anubis.runtime.AMap;
import anubis.runtime.ANullObject;
import anubis.runtime.ANumber;
import anubis.runtime.AObjects;
import anubis.runtime.APrimitive;
import anubis.runtime.ASet;
import anubis.runtime.AString;
import anubis.runtime.ATrueObject;
import anubis.runtime.Operator;
import anubis.runtime.util.Pair;

public class ExpressionEmitter {
	private final CodeGenerator owner;
	
	public ExpressionEmitter(CodeGenerator owner) {
		this.owner = owner;
	}
	
	public void emit(CodeBuilder builder, BinaryExpression expr) {
		switch (expr.getOperator()) {
			case AND: {
				Label L01 = builder.newLabel();
				Label L02 = builder.newLabel();
				expr.getLeft().visit(owner, builder);
				builder.emitInvoke(Operator.class, "isTrue", AnubisObject.class);
				builder.emitIfFalse(L01);
				expr.getRight().visit(owner, builder);
				builder.emitInvoke(Operator.class, "isTrue", AnubisObject.class);
				builder.emitIfFalse(L01);
				builder.emitInvoke(AObjects.class, "getTrue");
				builder.emitGoto(L02);
				builder.emitLabel(L01);
				builder.emitInvoke(AObjects.class, "getFalse");
				builder.emitLabel(L02);
				break;
			}
			case EQUALS: {
				expr.getLeft().visit(owner, builder);
				expr.getRight().visit(owner, builder);
				builder.emitInvoke(Operator.class, "opEquals", AnubisObject.class, AnubisObject.class);
				break;
			}
			case IFNULL: {
				Label L01 = builder.newLabel();
				expr.getLeft().visit(owner, builder);
				builder.emitDup();
				builder.emitInvoke(Operator.class, "isNull", AnubisObject.class);
				builder.emitIfFalse(L01);
				builder.emitPop();
				expr.getRight().visit(owner, builder);
				builder.emitLabel(L01);
				break;
			}
			case INDEX: {
				expr.getLeft().visit(owner, builder);
				expr.getRight().visit(owner, builder);
				builder.emitInvoke(Operator.class, "opIndex", AnubisObject.class, AnubisObject.class);
				break;
			}
			case NOTEQUALS: {
				expr.getLeft().visit(owner, builder);
				expr.getRight().visit(owner, builder);
				builder.emitInvoke(Operator.class, "opNotEquals", AnubisObject.class, AnubisObject.class);
				break;
			}
			case OR: {
				Label L01 = builder.newLabel();
				Label L02 = builder.newLabel();
				expr.getLeft().visit(owner, builder);
				builder.emitInvoke(Operator.class, "isTrue", AnubisObject.class);
				builder.emitIfTrue(L01);
				expr.getRight().visit(owner, builder);
				builder.emitInvoke(Operator.class, "isTrue", AnubisObject.class);
				builder.emitIfTrue(L01);
				builder.emitInvoke(AObjects.class, "getFalse");
				builder.emitGoto(L02);
				builder.emitLabel(L01);
				builder.emitInvoke(AObjects.class, "getTrue");
				builder.emitLabel(L02);
				break;
			}
			case XOR: {
				expr.getLeft().visit(owner, builder);
				expr.getRight().visit(owner, builder);
				builder.emitInvoke(Operator.class, "opXor", AnubisObject.class, AnubisObject.class);
				break;
			}
			default:
				throw new BugIsHereException();
		}
	}
	
	public void emit(CodeBuilder builder, BlockExpression expr) {
		Class<?> body = owner.newCodeBlockClass(expr.getStatement());
		
		builder.pushLocal();
		builder.emitInvoke(AObjects.class, "newContext", AnubisObject.class);
		// context
		{
			// context
			builder.emitLoadField(body, "INSTANCE");
			// context, code
			builder.emitSwap();
			// code, context
			builder.emitInvoke(CodeBlock.class, "exec", AnubisObject.class);
			// result
		}
	}
	
	public void emit(CodeBuilder builder, CallExpression expr) {
		String name = expr.getFuncName();
		CallArgument arg = expr.getArgs();
		
		// expr
		expr.getExpr().visit(owner, builder);
		if (arg.getThis() == null) {
			// expr, expr
			builder.emitDup();
		}
		else {
			// expr, this
			arg.getThis().visit(owner, builder);
		}
		
		// expr, this, name
		builder.pushString(name);
		// expr, name, this
		builder.emitSwap();
		
		// expr, name, this, args
		List<Expression> params = arg.getParams();
		builder.emitNewArray(AnubisObject.class, params.size());
		for (int index = 0; index < params.size(); index++) {
			builder.emitDup();
			builder.pushIntValue(index);
			params.get(index).visit(owner, builder);
			builder.emitAAStore();
		}
		
		// result
		builder.emitInvoke(Operator.class, "opCall", AnubisObject.class, String.class, AnubisObject.class,
				AnubisObject[].class);
	}
	
	public void emit(CodeBuilder builder, GetSlotExpression expr) {
		expr.getExpr().visit(owner, builder);
		builder.pushString(expr.getName());
		builder.emitInvoke(Operator.class, "findSlot", AnubisObject.class, String.class);
	}
	
	public void emit(CodeBuilder builder, GetSpecialExpression expr) {
		expr.getExpr().visit(owner, builder);
		builder.emitLoadField(SpecialSlot.class, expr.getName().toString());
		builder.emitInvoke(AnubisObject.class, "getSlot", SpecialSlot.class);
	}
	
	public void emit(CodeBuilder builder, LocalExpression expr) {
		builder.pushLocal();
	}
	
	public void emit(CodeBuilder builder, NewFunctionExpression expr) {
		Class<?> body = owner.newCodeBlockClass(expr.getStatement());
		
		// code
		builder.emitLoadField(body, "INSTANCE");
		// code, outer
		builder.pushLocal();
		// code, outer, args
		List<String> params = expr.getArgNames();
		builder.emitNewArray(String.class, params.size());
		for (int index = 0; index < params.size(); index++) {
			builder.emitDup();
			builder.pushIntValue(index);
			builder.pushString(params.get(index));
			builder.emitAAStore();
		}
		// func
		builder.emitInvoke(AObjects.class, "newFunction", CodeBlock.class, AnubisObject.class, String[].class);
	}
	
	public void emit(CodeBuilder builder, NewListExpression expr) {
		builder.emitInvoke(AObjects.class, "newList");
		for (Expression e2: expr.getExprs()) {
			builder.emitDup();
			e2.visit(owner, builder);
			builder.emitInvoke(AList.class, "add", AnubisObject.class);
		}
	}
	
	public void emit(CodeBuilder builder, NewMapExpression expr) {
		builder.emitInvoke(AObjects.class, "newMap");
		for (Pair<Expression, Expression> p: expr.getExprs()) {
			builder.emitDup();
			p.getValue1().visit(owner, builder);
			p.getValue2().visit(owner, builder);
			builder.emitInvoke(AMap.class, "add", AnubisObject.class, AnubisObject.class);
		}
	}
	
	public void emit(CodeBuilder builder, NewObjectExpression expr) {
		Class<?> body = owner.newCodeBlockClass(expr.getStatement());
		
		builder.pushLocal();
		builder.emitInvoke(AObjects.class, "newObject", AnubisObject.class);
		// obj
		{
			// obj, obj
			builder.emitDup();
			// obj, obj, code
			builder.emitLoadField(body, "INSTANCE");
			// obj, code, obj
			builder.emitSwap();
			// obj, val
			builder.emitInvoke(CodeBlock.class, "exec", AnubisObject.class);
			// obj
			builder.emitPop();
		}
	}
	
	public void emit(CodeBuilder builder, NewRangeExpression expr) {
		expr.getStart().visit(owner, builder);
		if (expr.getEnd() != null)
			expr.getEnd().visit(owner, builder);
		else
			builder.pushNull();
		if (expr.getStep() != null)
			expr.getStep().visit(owner, builder);
		else
			builder.pushNull();
		builder.emitInvoke(AObjects.class, "newRange", AnubisObject.class, AnubisObject.class, AnubisObject.class);
	}
	
	public void emit(CodeBuilder builder, NewSetExpression expr) {
		builder.emitInvoke(AObjects.class, "newSet");
		for (Expression e2: expr.getExprs()) {
			builder.emitDup();
			e2.visit(owner, builder);
			builder.emitInvoke(ASet.class, "add", AnubisObject.class);
		}
	}
	
	public void emit(CodeBuilder builder, PrimitiveExpression expr) {
		APrimitive value = expr.getValue();
		if (value == null) {
			builder.pushNull();
			return;
		}
		if (value instanceof ANullObject) {
			builder.emitInvoke(AObjects.class, "getNull");
			return;
		}
		if (value instanceof ATrueObject) {
			builder.emitInvoke(AObjects.class, "getTrue");
			return;
		}
		if (value instanceof AFalseObject) {
			builder.emitInvoke(AObjects.class, "getFalse");
			return;
		}
		if (value instanceof ANumber) {
			builder.pushNumber(((ANumber) value).getNumber());
			builder.emitInvoke(AObjects.class, "getNumber", Number.class);
			return;
		}
		if (value instanceof AString) {
			builder.pushString(((AString) value).getValue());
			builder.emitInvoke(AObjects.class, "getString", String.class);
			return;
		}
		throw new BugIsHereException();
	}
	
	public void emit(CodeBuilder builder, TernaryExpression expr) {
		switch (expr.getOperator()) {
			case IFELSE: {
				Label L01 = builder.newLabel();
				Label L02 = builder.newLabel();
				expr.getLeft().visit(owner, builder);
				builder.emitInvoke(Operator.class, "isTrue", AnubisObject.class);
				builder.emitIfFalse(L01);
				expr.getMiddle().visit(owner, builder);
				builder.emitGoto(L02);
				builder.emitLabel(L01);
				expr.getRight().visit(owner, builder);
				builder.emitLabel(L02);
				break;
			}
			case SLICE: {
				expr.getLeft().visit(owner, builder);
				expr.getMiddle().visit(owner, builder);
				expr.getRight().visit(owner, builder);
				builder.emitInvoke(Operator.class, "opSlice", AnubisObject.class, AnubisObject.class,
						AnubisObject.class);
				break;
			}
			default: {
				throw new BugIsHereException();
			}
		}
	}
	
	public void emit(CodeBuilder builder, UnaryExpression expr) {
		switch (expr.getOperator()) {
			case NOT: {
				expr.getExpr().visit(owner, builder);
				builder.emitInvoke(Operator.class, "opNot", AnubisObject.class);
				break;
			}
			case ISNULL: {
				expr.getExpr().visit(owner, builder);
				builder.emitInvoke(Operator.class, "isNull", AnubisObject.class);
				builder.emitInvoke(Operator.class, "getBool", Boolean.TYPE);
				break;
			}
			case ISTRUE: {
				expr.getExpr().visit(owner, builder);
				builder.emitInvoke(Operator.class, "isTrue", AnubisObject.class);
				builder.emitInvoke(Operator.class, "getBool", Boolean.TYPE);
				break;
			}
			case ISFALSE: {
				expr.getExpr().visit(owner, builder);
				builder.emitInvoke(Operator.class, "isFalse", AnubisObject.class);
				builder.emitInvoke(Operator.class, "getBool", Boolean.TYPE);
				break;
			}
			default: {
				throw new BugIsHereException();
			}
		}
	}
}
