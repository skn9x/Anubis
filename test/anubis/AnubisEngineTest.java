package anubis;

import static org.junit.Assert.assertEquals;
import java.math.BigInteger;
import javax.script.ScriptEngine;
import org.junit.Before;
import org.junit.Test;

public class AnubisEngineTest {
	private ScriptEngine engine;
	
	@Before
	public void setUp() throws Exception {
		engine = new AnubisEngineFactory().getScriptEngine();
	}
	
	@Test
	public void testEval() throws Exception {
		assertEquals(BigInteger.valueOf(1234), engine.eval("1234"));
		assertEquals(BigInteger.valueOf(1234), engine.eval("return 1234;"));
		assertEquals(BigInteger.valueOf(3), engine.eval("1 + 2"));
		assertEquals(BigInteger.valueOf(5), engine.eval("return 3 + 4 / 2;"));
		assertEquals("abc", engine.eval("return 'abc' + void;"));
		assertEquals("abc", engine.eval("return 'abc' + null;"));
		assertEquals("abcdef", engine.eval("return 'abc' + 'def';"));
		assertEquals("abc123", engine.eval("return 'abc' + 123;"));
		assertEquals("abc", engine.eval("x = 'abc'"));
		assertEquals("abc", engine.eval("x"));
		assertEquals("abcdef", engine.eval("x += 'def'"));
		assertEquals("abcdef", engine.eval("x"));
		assertEquals("ABC", engine.eval("def text = 'ABC'; return text;"));
		assertEquals("ABC", engine.eval("text"));
	}
}
