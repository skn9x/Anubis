package anubis;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import anubis.ast.CompilationUnit;
import anubis.code.CodeBlock;
import anubis.code.Option;
import anubis.code.asm.AsmCodeBlockFactory;
import anubis.except.AnubisExitError;
import anubis.except.ExceptionProvider;
import anubis.parser.Parser;
import anubis.runtime.AObjects;
import anubis.runtime.ObjectFactory;
import anubis.runtime.Operator;
import anubis.runtime.java.JCaster;

/**
 * AnubisEngine の実際の実行を担うオブジェクトです。
 * @author SiroKuro
 */
public class EngineCore {
	private static final ThreadLocal<ScriptContext> currentContext = new ThreadLocal<ScriptContext>();
	private final AnubisEngine owner;
	private final ObjectFactory factory;
	
	public EngineCore(AnubisEngine owner, ObjectFactory factory) {
		this.owner = owner;
		this.factory = factory;
	}
	
	public Object asJava(AnubisObject obj) {
		return JCaster.cast(obj);
	}
	
	public ObjectFactory getObjectFactory() {
		return factory;
	}
	
	public AnubisCompiledScript internalCompile(Reader reader, ScriptContext context) throws ScriptException {
		CodeBlock block = newCodeBlock(reader, context, null);
		return new AnubisCompiledScript(block);
	}
	
	public Object internalInvoke(final Class<?> type, final Object obj, final String name, final Object... args) throws ScriptException, NoSuchMethodException {
		try {
			return run(factory, owner.getContext(), new Execution<Object>() {
				@Override
				public Object exec(ScriptContext context) {
					AnubisObject aobj = obj != null ? AObjects.getObject(obj) : newLocal(owner.getContext());
					// TODO NoSuchMethodException の対処
					AnubisObject result = Operator.opCall(aobj, name, aobj, AObjects.getObjects(args));
					return type == null ? asJava(result) : JCaster.cast(type, result);
				}
			});
		}
		catch (AnubisExitError exit) {
			return type == null ? asJava(exit.getValue()) : JCaster.cast(type, exit.getValue());
		}
		catch (RuntimeException ex) {
			throw new ScriptException(ex);
		}
	}
	
	private CodeBlock newCodeBlock(final Reader code, ScriptContext context, final File dir) {
		return run(factory, context, new Execution<CodeBlock>() {
			@Override
			public CodeBlock exec(ScriptContext context) {
				Option option = newOption(context);
				CompilationUnit node = new Parser().parse(code, option.getSrcFileName());
				AsmCodeBlockFactory codeFactory = new AsmCodeBlockFactory();
				CodeBlock result = codeFactory.newCodeBlock(node, option);
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
		});
	}
	
	public static ScriptContext getCurrentContext() {
		return currentContext.get();
	}
	
	private static AnubisObject newLocal(ScriptContext context) {
		return AObjects.getObject(context);
	}
	
	private static Option newOption(ScriptContext context) {
		Option result = new Option();
		result.setSrcFileName(Utils.asString(context.getAttribute(ScriptEngine.FILENAME)));
		result.setProgramName(Utils.getCnFromSn(result.getSrcFileName()));
		result.setDisableAssertion(Operator.isFalse(AObjects.getObject(context.getAttribute("anubis.disableAssertion"))));
		return result;
	}
	
	private static <T> T run(ObjectFactory factory, ScriptContext context, Execution<T> exec) {
		ObjectFactory oldFactory = AObjects.setCurrent(factory);
		ScriptContext oldContext = setCurrentContext(context);
		try {
			return exec.exec(context);
		}
		finally {
			setCurrentContext(oldContext);
			AObjects.setCurrent(oldFactory);
		}
	}
	
	private static ScriptContext setCurrentContext(ScriptContext context) {
		ScriptContext result = currentContext.get();
		currentContext.set(context);
		return result;
	}
	
	public class AnubisCompiledScript extends CompiledScript {
		private final CodeBlock block;
		
		private AnubisCompiledScript(CodeBlock block) {
			this.block = block;
		}
		
		@Override
		public Object eval(ScriptContext context) throws ScriptException {
			return asJava(exec(context));
		}
		
		public AnubisObject exec(ScriptContext context) throws ScriptException {
			try {
				return EngineCore.run(factory, context, new Execution<AnubisObject>() {
					@Override
					public AnubisObject exec(ScriptContext context) {
						AnubisObject local = newLocal(context);
						return block.exec(local);
					}
				});
			}
			catch (AnubisExitError exit) {
				return exit.getValue();
			}
			catch (RuntimeException ex) {
				throw new ScriptException(ex);
			}
		}
		
		@Override
		public ScriptEngine getEngine() {
			return owner;
		}
	}
	
	private interface Execution<T> {
		public T exec(ScriptContext context);
	}
}
