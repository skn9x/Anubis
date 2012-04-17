package anubis.code.asm;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import anubis.ast.CompilationUnit;
import anubis.code.CodeBlock;
import anubis.except.CompileException;

public class AsmCodeBlockFactory {
	private final CustomCodeClassLoader loader;
	private final String pname;
	private final boolean debug;
	
	private int clsCount = 0;
	
	public AsmCodeBlockFactory(String pname, boolean debug) {
		this.loader = AccessController.doPrivileged(new PrivilegedAction<CustomCodeClassLoader>() {
			@Override
			public CustomCodeClassLoader run() {
				return new CustomCodeClassLoader();
			}
		});
		this.pname = pname;
		this.debug = debug;
	}
	
	public CodeBlock newCodeBlock(CompilationUnit node) {
		try {
			return (CodeBlock) newCodeBlockClass(node).getField("INSTANCE").get(null);
		}
		catch (NoSuchFieldException ex) {
			throw new CompileException(ex);
		}
		catch (IllegalAccessException ex) {
			throw new CompileException(ex);
		}
	}
	
	public Class<?> newCodeBlockClass(CompilationUnit node) {
		String className = newClassName();
		byte[] data = new CodeGenerator(this, debug).generate(node, className);
		loader.putClassData(className, data);
		try {
			return loader.findClass(className);
		}
		catch (ClassNotFoundException ex) {
			throw new CompileException(ex);
		}
	}
	
	public void saveClassFiles(File dir) throws IOException {
		loader.saveClassFiles(dir);
	}
	
	private String getProgramName() {
		return pname;
	}
	
	private String newClassName() {
		int count;
		synchronized (this) {
			count = clsCount++;
		}
		return getProgramName() + (count == 0 ? "" : "$" + count);
	}
}
