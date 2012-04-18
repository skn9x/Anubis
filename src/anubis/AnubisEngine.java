package anubis;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import anubis.ast.CompilationUnit;
import anubis.code.CodeBlock;
import anubis.code.asm.AsmCodeBlockFactory;
import anubis.except.ExceptionProvider;
import anubis.parser.Parser;
import anubis.runtime.AObjects;
import anubis.runtime.ObjectFactory;
import anubis.runtime.StandardObjectFactory;
import anubis.runtime.java.JCaster;

/**
 * @author SiroKuro
 */
public class AnubisEngine extends AbstractScriptEngine implements Invocable, Compilable {
	private final AnubisEngineFactory owner;
	
	private final ObjectFactory factory;
	
	public AnubisEngine(AnubisEngineFactory owner) {
		this(owner, null);
	}
	
	public AnubisEngine(AnubisEngineFactory owner, ObjectFactory factory) {
		if (factory == null)
			factory = new StandardObjectFactory();
		this.owner = owner;
		this.factory = factory;
		
		setBindings(owner.getGlobalBindings(), ScriptContext.GLOBAL_SCOPE);
	}
	
	/**
	 * @param obj
	 * @return
	 * @see anubis.runtime.ObjectFactory#getObject(java.lang.Object)
	 */
	public AnubisObject asAnubis(Object obj) {
		return factory.getObject(obj);
	}
	
	/**
	 * @param obj
	 * @return
	 * @see anubis.runtime.java.JCaster#cast(anubis.AnubisObject)
	 */
	public Object asJava(AnubisObject obj) {
		return JCaster.cast(obj);
	}
	
	@Override
	public CompiledScript compile(Reader reader) throws ScriptException {
		return compileInternal(reader);
	}
	
	@Override
	public CompiledScript compile(String code) throws ScriptException {
		return compileInternal(new StringReader(code));
	}
	
	@Override
	public Bindings createBindings() {
		return new SimpleBindings();
	}
	
	@Override
	public Object eval(Reader code, ScriptContext context) throws ScriptException {
		AnubisCompiledScript script = compileInternal(code);
		return script.eval(context);
	}
	
	@Override
	public Object eval(String code, ScriptContext context) throws ScriptException {
		return eval(new StringReader(code), context);
	}
	
	public AnubisObject evalForRepl(String code) throws ScriptException {
		AnubisCompiledScript script = compileInternal(new StringReader(code));
		return script.exec(getContext());
	}
	
	@Override
	public ScriptEngineFactory getFactory() {
		return owner;
	}
	
	@Override
	public <T> T getInterface(Class<T> class1) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public <T> T getInterface(Object obj, Class<T> class1) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object invokeFunction(String s, Object... aobj) throws ScriptException, NoSuchMethodException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object invokeMethod(Object obj, String s, Object... aobj) throws ScriptException, NoSuchMethodException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private AnubisCompiledScript compileInternal(Reader reader) throws ScriptException {
		CodeBlock block = newCodeBlock(reader, "anubis", null); // TODO クラス名を調整する
		return new AnubisCompiledScript(this, block);
	}
	
	private CodeBlock newCodeBlock(Reader code, String className, File dir) {
		ObjectFactory oldFactory = AObjects.setCurrent(factory);
		try {
			CompilationUnit node = new Parser().parse(code);
			AsmCodeBlockFactory codeFactory = new AsmCodeBlockFactory(className, false);
			CodeBlock result = codeFactory.newCodeBlock(node);
			if (dir != null) {
				try {
					codeFactory.saveClassFiles(dir);
				}
				catch (IOException ex) {
					ExceptionProvider.newParseException(ex);
				}
			}
			return result;
		}
		finally {
			AObjects.setCurrent(oldFactory);
		}
	}
	
	private AnubisObject newLocal(ScriptContext context) {
		Bindings bind_global = context.getBindings(ScriptContext.GLOBAL_SCOPE);
		AnubisObject global = bind_global == null ? null : AObjects.getObject(bind_global);
		AnubisObject local = AObjects.getObject(context.getBindings(ScriptContext.ENGINE_SCOPE));
		local.setSlot(SpecialSlot.OUTER, global);
		return local;
	}
	
	private static class AnubisCompiledScript extends CompiledScript {
		private final AnubisEngine owner;
		private final CodeBlock block;
		
		private AnubisCompiledScript(AnubisEngine owner, CodeBlock block) {
			this.owner = owner;
			this.block = block;
		}
		
		@Override
		public Object eval(ScriptContext context) throws ScriptException {
			return owner.asJava(exec(context));
		}
		
		public AnubisObject exec(ScriptContext context) throws ScriptException {
			ObjectFactory oldFactory = AObjects.setCurrent(owner.factory);
			try {
				AnubisObject local = owner.newLocal(context);
				return block.exec(local);
			}
			catch (Exception ex) {
				throw new ScriptException(ex);
			}
			finally {
				AObjects.setCurrent(oldFactory);
			}
		}
		
		@Override
		public ScriptEngine getEngine() {
			return owner;
		}
	}
}
