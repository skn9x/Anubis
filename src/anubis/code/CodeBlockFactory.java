package anubis.code;

import java.io.File;
import java.io.IOException;
import anubis.ast.CompilationUnit;

public abstract class CodeBlockFactory {
	public abstract CodeBlock newCodeBlock(CompilationUnit node, Option option);
	
	public abstract void saveClassFiles(File dir) throws IOException;
	
}
