package anubis;

import static org.junit.Assert.assertEquals;
import java.math.BigInteger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class AnubisEngineTest {
	private AnubisEngine engine;
	
	@Before
	public void setUp() throws Exception {
		engine = (AnubisEngine) new AnubisEngineFactory().getScriptEngine();
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
	}
	
	/**
	 * 繰り返し eval してみて PermGen が溢れないことを確認してみる
	 * @throws Exception
	 */
	@Test
	@Ignore("長いしあまり重要ではないテスト")
	public void testEvalLoop() throws Exception {
		for (int i = 0; i < 100000; i++) {
			assertEquals(BigInteger.ONE, engine.eval("1"));
		}
	}
	
	@Test
	public void testGetInterface01() throws Exception {
		engine.eval("add = { x -> x + 11};");
		Calc calc = engine.getInterface(Calc.class);
		assertEquals(24, calc.add(13));
	}
	
	@Test
	public void testGetInterface02() throws Exception {
		Object obj = engine.eval("return object{ add = { x -> x + 11}; };");
		Calc calc = engine.getInterface(obj, Calc.class);
		assertEquals(24, calc.add(13));
	}
	
	@Test
	public void testInvokeFunction() throws Exception {
		engine.eval("add1 = { x -> x + 1};");
		assertEquals(BigInteger.valueOf(3), engine.invokeFunction("add1", 2));
	}
	
	@Test
	public void testInvokeMethod() throws Exception {
		Object obj = engine.eval("return object{ add1 = { x -> x + 1 }; };");
		assertEquals(BigInteger.valueOf(3), engine.invokeMethod(obj, "add1", 2));
		
		Object obj2 = new SampleCalc(3);
		assertEquals(BigInteger.valueOf(8), engine.invokeMethod(obj2, "add", 5));
	}
	
	private interface Calc {
		public int add(int x);
	}
	
	private static class SampleCalc implements Calc {
		private final int value;
		
		public SampleCalc(int value) {
			this.value = value;
		}
		
		public int add(int x) {
			return value + x;
		}
	}
}
