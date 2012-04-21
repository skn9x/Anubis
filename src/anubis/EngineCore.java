package anubis;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import anubis.ast.CompilationUnit;
import anubis.code.CodeBlock;
import anubis.code.Option;
import anubis.code.asm.AsmCodeBlockFactory;
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
class EngineCore {
	private final AnubisEngine owner;
	private final ObjectFactory factory;
	
	public EngineCore(AnubisEngine owner, ObjectFactory factory) {
		this.owner = owner;
		this.factory = factory;
	}
	
	public AnubisObject asAnubis(Object obj) {
		return factory.getObject(obj);
	}
	
	public Object asJava(AnubisObject obj) {
		return JCaster.cast(obj);
	}
	
	public AnubisCompiledScript internalCompile(Reader reader, ScriptContext context) throws ScriptException {
		CodeBlock block = newCodeBlock(reader, context, null);
		return new AnubisCompiledScript(block);
	}
	
	public Object internalInvoke(Class<?> type, Object obj, String name, Object... args) throws ScriptException, NoSuchMethodException {
		ObjectFactory oldFactory = AObjects.setCurrent(factory);
		try {
			AnubisObject aobj = obj != null ? AObjects.getObject(obj) : newLocal(owner.getContext());
			// TODO NoSuchMethodException の対処
			AnubisObject result = Operator.opCall(aobj, name, aobj, AObjects.getObjects(args));
			return type == null ? asJava(result) : JCaster.cast(type, result);
		}
		catch (Exception ex) {
			throw new ScriptException(ex);
		}
		finally {
			AObjects.setCurrent(oldFactory);
		}
	}
	
	private CodeBlock newCodeBlock(Reader code, ScriptContext context, File dir) {
		ObjectFactory oldFactory = AObjects.setCurrent(factory);
		try {
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
		finally {
			AObjects.setCurrent(oldFactory);
		}
	}
	
	private static AnubisObject newLocal(ScriptContext context) {
		Bindings bind_global = context.getBindings(ScriptContext.GLOBAL_SCOPE);
		AnubisObject global = bind_global == null ? null : AObjects.getObject(bind_global);
		AnubisObject local = AObjects.getObject(context.getBindings(ScriptContext.ENGINE_SCOPE));
		local.setSlot(SpecialSlot.OUTER, global);
		return local;
	}
	
	private static Option newOption(ScriptContext context) {
		Option result = new Option();
		result.setSrcFileName(Utils.asString(context.getAttribute(ScriptEngine.FILENAME)));
		result.setProgramName(Utils.getCnFromSn(result.getSrcFileName()));
		result.setDisableAssertion(Operator.isFalse(AObjects.getObject(context.getAttribute("anubis.disableAssertion"))));
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
			ObjectFactory oldFactory = AObjects.setCurrent(factory);
			try {
				AnubisObject local = newLocal(context);
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
