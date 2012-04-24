package anubis;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import anubis.runtime.ObjectFactory;
import anubis.runtime.StandardObjectFactory;

/**
 * Anubis の ScriptEngine です。
 * このクラスは ScriptEngine の他に Invocable と Compilable を実装します。
 * @author SiroKuro
 */
public class AnubisEngine extends AbstractScriptEngine implements Invocable, Compilable {
	private final AnubisEngineFactory owner;
	private final EngineCore core;
	
	public AnubisEngine(AnubisEngineFactory owner) {
		this(owner, null);
	}
	
	public AnubisEngine(AnubisEngineFactory owner, ObjectFactory factory) {
		this.owner = owner;
		this.core = new EngineCore(this, factory != null ? factory : new StandardObjectFactory());
		setBindings(owner.getGlobalBindings(), ScriptContext.GLOBAL_SCOPE);
	}
	
	@Override
	public CompiledScript compile(Reader reader) throws ScriptException {
		return core.internalCompile(reader, getContext());
	}
	
	@Override
	public CompiledScript compile(String code) throws ScriptException {
		return core.internalCompile(new StringReader(code), getContext());
	}
	
	@Override
	public Bindings createBindings() {
		return new SimpleBindings();
	}
	
	@Override
	public Object eval(Reader code, ScriptContext context) throws ScriptException {
		EngineCore.AnubisCompiledScript script = core.internalCompile(code, context);
		return script.eval(context);
	}
	
	@Override
	public Object eval(String code, ScriptContext context) throws ScriptException {
		return eval(new StringReader(code), context);
	}
	
	public AnubisObject evalForRepl(String code) throws ScriptException {
		EngineCore.AnubisCompiledScript script = core.internalCompile(new StringReader(code), getContext());
		return script.exec(getContext());
	}
	
	@Override
	public ScriptEngineFactory getFactory() {
		return owner;
	}
	
	@Override
	public <T> T getInterface(Class<T> clazz) {
		return clazz.cast(Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{
			clazz
		}, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method mm, Object[] aobj) throws Throwable {
				return core.internalInvoke(mm.getReturnType(), null, mm.getName(), aobj);
			}
		}));
	}
	
	@Override
	public <T> T getInterface(final Object obj, Class<T> clazz) {
		return clazz.cast(Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{
			clazz
		}, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method mm, Object[] aobj) throws Throwable {
				return core.internalInvoke(mm.getReturnType(), obj, mm.getName(), aobj);
			}
		}));
	}
	
	public ObjectFactory getObjectFactory() {
		return core.getObjectFactory();
	}
	
	@Override
	public Object invokeFunction(String name, Object... args) throws ScriptException, NoSuchMethodException {
		return core.internalInvoke(null, null, name, args);
	}
	
	@Override
	public Object invokeMethod(Object obj, String name, Object... args) throws ScriptException, NoSuchMethodException {
		return core.internalInvoke(null, obj, name, args);
	}
}
