package anubis.code.asm;

import static org.objectweb.asm.Opcodes.AASTORE;
import static org.objectweb.asm.Opcodes.ACC_FINAL;
import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.ACONST_NULL;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ANEWARRAY;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.ATHROW;
import static org.objectweb.asm.Opcodes.BIPUSH;
import static org.objectweb.asm.Opcodes.CHECKCAST;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.DUP_X1;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.ICONST_0;
import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.ICONST_2;
import static org.objectweb.asm.Opcodes.ICONST_3;
import static org.objectweb.asm.Opcodes.ICONST_4;
import static org.objectweb.asm.Opcodes.ICONST_5;
import static org.objectweb.asm.Opcodes.ICONST_M1;
import static org.objectweb.asm.Opcodes.IFEQ;
import static org.objectweb.asm.Opcodes.IFNE;
import static org.objectweb.asm.Opcodes.IFNULL;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.MONITORENTER;
import static org.objectweb.asm.Opcodes.MONITOREXIT;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.POP;
import static org.objectweb.asm.Opcodes.PUTSTATIC;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.SIPUSH;
import static org.objectweb.asm.Opcodes.SWAP;
import static org.objectweb.asm.Opcodes.V1_5;
import static org.objectweb.asm.Type.getConstructorDescriptor;
import static org.objectweb.asm.Type.getDescriptor;
import static org.objectweb.asm.Type.getInternalName;
import static org.objectweb.asm.Type.getMethodDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import anubis.AnubisObject;
import anubis.code.CodeBlock;
import anubis.except.ExceptionProvider;

public class CodeBuilder {
	private static final Class<?> CN_SUPERCLASS = CompiledCodeBlock.class;
	private static final String LCN_CODEBLOCK = Type.getDescriptor(CodeBlock.class);//"Lanubis/code/CodeBlock;";
	private static final String ICN_SUPERCLASS = getInternalName(CN_SUPERCLASS);
	private static final int VAR_LOCAL = 1; // exec １番目の引数 = local
	private static final int VAR_FREESPACE = 2; // ローカル変数用フリースペース
	
	private final String internalClassName;
	private final ClassWriter cw;
	private final MethodVisitor mv;
	private byte[] result = null;
	private int var = VAR_FREESPACE;
	
	public CodeBuilder(String className) {
		this(className, null);
	}
	
	public CodeBuilder(String className, String filename) {
		this.internalClassName = className.replace('.', '/');
		this.cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		
		// public [ClassName] extends [BASE_CLASS] {
		cw.visit(V1_5, ACC_PUBLIC, internalClassName, null, ICN_SUPERCLASS, new String[0]);
		cw.visitSource(filename != null ? filename : "[unknown]", null);
		
		// public AnubisObject exec(AnubisObject local) {
		try {
			Method mm = CodeBlock.class.getMethod("exec", AnubisObject.class);
			this.mv = cw.visitMethod(ACC_PUBLIC, mm.getName(), getMethodDescriptor(mm), null, null);
		}
		catch (NoSuchMethodException ex) {
			throw ExceptionProvider.newInternalCompileExeption(ex);
		}
	}
	
	public int allocLv() {
		return var++;
	}
	
	public void emitAAStore() {
		mv.visitInsn(AASTORE);
	}
	
	public void emitCheckCast(Class<?> clazz) {
		mv.visitTypeInsn(CHECKCAST, getInternalName(clazz));
	}
	
	public void emitDup() {
		mv.visitInsn(DUP);
	}
	
	public void emitDupX1() {
		mv.visitInsn(DUP_X1);
	}
	
	public void emitGoto(Label label) {
		mv.visitJumpInsn(GOTO, unwrap(label));
	}
	
	public void emitIfFalse(Label label) {
		mv.visitJumpInsn(IFEQ, unwrap(label));
	}
	
	public void emitIfNull(Label label) {
		mv.visitJumpInsn(IFNULL, unwrap(label));
	}
	
	public void emitIfTrue(Label label) {
		mv.visitJumpInsn(IFNE, unwrap(label));
	}
	
	public void emitInvoke(Class<?> clazz, String name, Class<?>... parameterTypes) {
		try {
			if ("<init>".equals(name)) {
				Constructor<?> cc = clazz.getConstructor(parameterTypes);
				mv.visitMethodInsn(INVOKESPECIAL, getInternalName(clazz), name, getConstructorDescriptor(cc));
			}
			else {
				Method mm = clazz.getMethod(name, parameterTypes);
				if (clazz.isInterface()) {
					mv.visitMethodInsn(INVOKEINTERFACE, getInternalName(clazz), name, getMethodDescriptor(mm));
				}
				else if (Modifier.isStatic(mm.getModifiers())) {
					mv.visitMethodInsn(INVOKESTATIC, getInternalName(mm.getDeclaringClass()), name,
							getMethodDescriptor(mm));
				}
				else {
					mv.visitMethodInsn(INVOKEVIRTUAL, getInternalName(clazz), name, getMethodDescriptor(mm));
				}
			}
		}
		catch (NoSuchMethodException ex) {
			throw ExceptionProvider.newInternalCompileExeption(ex);
		}
	}
	
	public void emitLabel(Label label) {
		mv.visitLabel(unwrap(label));
	}
	
	public void emitLoadField(Class<?> clazz, String name) {
		try {
			Field ff = clazz.getField(name);
			if (Modifier.isStatic(ff.getModifiers())) {
				mv.visitFieldInsn(GETSTATIC, getInternalName(clazz), name, getDescriptor(ff.getType()));
			}
			else {
				mv.visitFieldInsn(GETFIELD, getInternalName(clazz), name, getDescriptor(ff.getType()));
			}
		}
		catch (NoSuchFieldException ex) {
			throw ExceptionProvider.newInternalCompileExeption(ex);
		}
	}
	
	public void emitMonitorEnter() {
		mv.visitInsn(MONITORENTER);
	}
	
	public void emitMonitorExit() {
		mv.visitInsn(MONITOREXIT);
	}
	
	public void emitNew(Class<?> clazz) {
		mv.visitTypeInsn(NEW, getInternalName(clazz));
	}
	
	public void emitNewArray(Class<?> clazz, int size) {
		pushIntValue(size);
		mv.visitTypeInsn(ANEWARRAY, getInternalName(clazz));
	}
	
	public void emitPop() {
		mv.visitInsn(POP);
	}
	
	public void emitReturn() {
		mv.visitInsn(ARETURN);
	}
	
	public void emitStoreLocalVar(int var) {
		mv.visitVarInsn(ASTORE, var);
	}
	
	public void emitSwap() {
		mv.visitInsn(SWAP);
	}
	
	public void emitThrow() {
		mv.visitInsn(ATHROW);
	}
	
	public void emitTryBlock(Label _try, Label _end, Label _catch, Class<? extends Throwable> ex) {
		mv.visitTryCatchBlock(unwrap(_try), unwrap(_end), unwrap(_catch), ex != null ? getInternalName(ex) : null);
	}
	
	public byte[] finallize() {
		if (result == null) {
			// return null; }
			{
				pushNull();
				emitReturn();
				mv.visitMaxs(0, 0);
				mv.visitEnd();
			}
			
			// public static final CodeBlock INSTANCE;
			{
				cw.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "INSTANCE", LCN_CODEBLOCK, null, null);
			}
			
			// static { INSTANCE = new [ClassName](); }
			{
				MethodVisitor mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
				mv.visitCode();
				mv.visitTypeInsn(NEW, internalClassName);
				mv.visitInsn(DUP);
				mv.visitMethodInsn(INVOKESPECIAL, internalClassName, "<init>", "()V");
				mv.visitFieldInsn(PUTSTATIC, internalClassName, "INSTANCE", LCN_CODEBLOCK);
				mv.visitInsn(RETURN);
				mv.visitMaxs(0, 0);
				mv.visitEnd();
			}
			
			// private [ClassName]() { super(); }
			{
				MethodVisitor mv = cw.visitMethod(ACC_PRIVATE, "<init>", "()V", null, null);
				mv.visitCode();
				mv.visitVarInsn(ALOAD, 0);
				mv.visitMethodInsn(INVOKESPECIAL, ICN_SUPERCLASS, "<init>", "()V");
				mv.visitInsn(RETURN);
				mv.visitMaxs(0, 0);
				mv.visitEnd();
			}
			cw.visitEnd();
			result = cw.toByteArray();
		}
		return result;
	}
	
	public void freeLv() {
		var--;
		if (var < VAR_FREESPACE) {
			var = VAR_FREESPACE;
		}
	}
	
	public void mark(String filename, int line) {
		org.objectweb.asm.Label L01 = new org.objectweb.asm.Label();
		mv.visitLabel(L01); // visitLineNumber の前に visitLabel すること
		mv.visitLineNumber(line, L01);
	}
	
	public Label newLabel() {
		return new Label(new org.objectweb.asm.Label());
	}
	
	public void pushIntValue(int value) {
		switch (value) {
			case -1:
				mv.visitInsn(ICONST_M1);
				break;
			case 0:
				mv.visitInsn(ICONST_0);
				break;
			case 1:
				mv.visitInsn(ICONST_1);
				break;
			case 2:
				mv.visitInsn(ICONST_2);
				break;
			case 3:
				mv.visitInsn(ICONST_3);
				break;
			case 4:
				mv.visitInsn(ICONST_4);
				break;
			case 5:
				mv.visitInsn(ICONST_5);
				break;
			default:
				if (Byte.MIN_VALUE <= value && value <= Byte.MAX_VALUE)
					mv.visitIntInsn(BIPUSH, value);
				else if (Short.MIN_VALUE <= value && value <= Short.MAX_VALUE)
					mv.visitIntInsn(SIPUSH, value);
				else
					mv.visitLdcInsn(value);
				break;
		}
	}
	
	public void pushLocal() {
		mv.visitVarInsn(ALOAD, VAR_LOCAL);
	}
	
	public void pushLocalVar(int var) {
		mv.visitVarInsn(ALOAD, var);
	}
	
	public void pushNull() {
		mv.visitInsn(ACONST_NULL);
	}
	
	public void pushNumber(Number number) {
		if (number instanceof Integer) {
			pushIntValue(number.intValue());
			emitInvoke(Integer.class, "valueOf", Integer.TYPE);
		}
		else if (number instanceof Long) {
			mv.visitLdcInsn(number.longValue());
			emitInvoke(Long.class, "valueOf", Long.TYPE);
		}
		else if (number instanceof Float) {
			mv.visitLdcInsn(number.floatValue());
			emitInvoke(Float.class, "valueOf", Float.TYPE);
		}
		else if (number instanceof Double) {
			mv.visitLdcInsn(number.doubleValue());
			emitInvoke(Double.class, "valueOf", Double.TYPE);
		}
		else if (number instanceof BigInteger) {
			BigInteger bigint = (BigInteger) number;
			emitNew(BigInteger.class);
			emitDup();
			mv.visitLdcInsn(bigint.toString());
			emitInvoke(BigInteger.class, "<init>", String.class);
		}
		else if (number instanceof BigDecimal) {
			BigDecimal bigdec = (BigDecimal) number;
			emitNew(BigDecimal.class);
			emitDup();
			mv.visitLdcInsn(bigdec.toString());
			emitInvoke(BigDecimal.class, "<init>", String.class);
		}
		else {
			throw new IllegalArgumentException("unknown number: " + number);
		}
	}
	
	public void pushString(String value) {
		mv.visitLdcInsn(value);
	}
	
	private static org.objectweb.asm.Label unwrap(anubis.code.asm.Label label) {
		return (org.objectweb.asm.Label) label.getMarker();
	}
}
