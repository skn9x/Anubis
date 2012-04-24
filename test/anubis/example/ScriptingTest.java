package anubis.example;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.junit.Before;
import org.junit.Test;

public class ScriptingTest {
	private ScriptEngine engine;
	
	@Before
	public void setUp() throws Exception {
		ScriptEngineManager manager = new ScriptEngineManager();
		engine = manager.getEngineByName("anubis");
	}
	
	@Test
	public void test01Eval() throws ScriptException {
		engine.eval(" console.puts('Hello world'); ");
	}
	
	@Test
	public void test02Invocable() throws ScriptException, NoSuchMethodException {
		Invocable invocable = (Invocable) engine;
		engine.eval(" hello = { text -> console.puts(text) }; ");
		invocable.invokeFunction("hello", "Hello world");
	}
	
	@Test
	public void test03Compilable() throws ScriptException, NoSuchMethodException {
		Compilable compilable = (Compilable) engine;
		CompiledScript code = compilable.compile(" console.puts('Hello Anubis!'); ");
		code.eval();
	}
}
