package anubis.code.asm;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import anubis.ast.CompilationUnit;
import anubis.code.CodeBlock;
import anubis.code.CodeBlockFactory;
import anubis.code.Option;
import anubis.except.ExceptionProvider;

public class AsmCodeBlockFactory extends CodeBlockFactory {
	private final Map<String, CustomCodeClassLoader> loaders = new HashMap<String, CustomCodeClassLoader>();
	
	@Override
	public CodeBlock newCodeBlock(CompilationUnit node, Option option) {
		try {
			return (CodeBlock) newCodeBlockClass(node, option).getField("INSTANCE").get(null);
		}
		catch (NoSuchFieldException ex) {
			throw ExceptionProvider.newInternalCompileExeption(ex);
		}
		catch (IllegalAccessException ex) {
			throw ExceptionProvider.newInternalCompileExeption(ex);
		}
	}
	
	public Class<?> newCodeBlockClass(CompilationUnit node, Option option) {
		CustomCodeClassLoader loader = getClassLoader(option.getProgramName());
		String className = loader.newClassName();
		byte[] data = new CodeGenerator(this, option).generate(node, className);
		loader.putClassData(className, data);
		try {
			return loader.findClass(className);
		}
		catch (ClassNotFoundException ex) {
			throw ExceptionProvider.newInternalCompileExeption(ex);
		}
	}
	
	@Override
	public void saveClassFiles(File dir) throws IOException {
		for (CustomCodeClassLoader loader: loaders.values()) {
			loader.saveClassFiles(dir);
		}
	}
	
	private synchronized CustomCodeClassLoader getClassLoader(final String programName) {
		CustomCodeClassLoader result = loaders.get(programName);
		if (result == null) {
			result = AccessController.doPrivileged(new PrivilegedAction<CustomCodeClassLoader>() {
				@Override
				public CustomCodeClassLoader run() {
					return new CustomCodeClassLoader(programName);
				}
			});
			loaders.put(programName, result);
		}
		return result;
	}
}
