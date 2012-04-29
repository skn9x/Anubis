package anubis.runtime.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import anubis.AnubisObject;
import anubis.except.JOverloadMismatchException;
import anubis.runtime.ADecimal;
import anubis.runtime.AInteger;
import anubis.runtime.ANumber;
import anubis.runtime.AObjects;
import anubis.runtime.JObject;
import anubis.runtime.StandardObjectFactory;

/**
 * @author SiroKuro
 */
public class InvocationTest {
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		AObjects.setCurrent(new StandardObjectFactory());
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		AObjects.setCurrent(null);
	}
	
	/**
	 * {@link anubis.runtime.java.Invocation#equals(anubis.runtime.java.Invocation)} のためのテスト・メソッド。
	 */
	@Test
	public void testEquals() throws NoSuchMethodException {
		Invocation i1 = method(Sample.class, "foo", BigDecimal.class, BigInteger.class);
		Invocation i2 = method(Sample.class, "foo", BigDecimal.class, BigInteger.class);
		Invocation i3 = method(Sample.class, "foo");
		
		assertTrue(i1.equals(i1));
		assertTrue(i1.equals(i2));
		assertFalse(i1.equals(null));
		assertFalse(i1.equals("abc"));
		assertFalse(i1.equals(i3));
	}
	
	/**
	 * {@link anubis.runtime.java.Invocation#getArgsScore()} のためのテスト・メソッド。
	 */
	@Test
	public void testGetArgsScore() throws NoSuchMethodException {
		{
			Invocation i1 = method(Sample.class, "foo");
			assertEquals("", i1.getArgsScore());
		}
		{
			Invocation i1 = method(Sample.class, "foo", AnubisObject.class);
			assertEquals("0", i1.getArgsScore());
		}
		{
			Invocation i1 = method(Sample.class, "foo", ANumber.class);
			assertEquals("0", i1.getArgsScore());
		}
		{
			Invocation i1 = method(Sample.class, "foo", BigDecimal.class);
			assertEquals("1", i1.getArgsScore());
		}
		{
			Invocation i1 = method(Sample.class, "foo", BigInteger.class);
			assertEquals("6", i1.getArgsScore());
		}
		{
			Invocation i1 = method(Sample.class, "foo", Integer.TYPE);
			assertEquals("9", i1.getArgsScore());
		}
		{
			Invocation i1 = method(Sample.class, "foo", Integer.class);
			assertEquals("a", i1.getArgsScore());
		}
		{
			Invocation i1 = method(Sample.class, "foo", String.class);
			assertEquals("f", i1.getArgsScore());
		}
		{
			Invocation i1 = method(Sample.class, "foo", Boolean.TYPE);
			assertEquals("h", i1.getArgsScore());
		}
		{
			Invocation i1 = method(Sample.class, "foo", Boolean.class);
			assertEquals("i", i1.getArgsScore());
		}
		{
			Invocation i1 = method(Sample.class, "foo", Object.class);
			assertEquals("j", i1.getArgsScore());
		}
		{
			Invocation i1 = method(Sample.class, "foo", BigDecimal.class, BigInteger.class);
			assertEquals("16", i1.getArgsScore());
		}
	}
	
	/**
	 * {@link anubis.runtime.java.Invocation#invoke(anubis.AnubisObject, anubis.AnubisObject[])} のためのテスト・メソッド。
	 */
	@Test
	public void testInvoke() throws Exception {
		JObject _this = (JObject) AObjects.getObject(new Sample());
		{
			Invocation i1 = method(Sample.class, "foo");
			assertEquals(AInteger.valueOf(123), i1.invoke(_this));
		}
		{
			Invocation i1 = method(Sample.class, "foo", Integer.TYPE);
			assertEquals(AInteger.valueOf(124), i1.invoke(_this, AInteger.valueOf(124)));
		}
		{
			Invocation i1 = method(Sample.class, "foo", Integer.class);
			assertEquals(AInteger.valueOf(125), i1.invoke(_this, AInteger.valueOf(125)));
		}
		{
			Invocation i1 = method(Sample.class, "foo", BigDecimal.class, BigInteger.class);
			assertTrue(i1.match(ADecimal.valueOf(BigDecimal.ZERO), AInteger.valueOf(BigInteger.ZERO)));
			assertEquals(AInteger.valueOf(123), i1.invoke(_this, ADecimal.valueOf(125.0), AInteger.valueOf(125)));
		}
		{
			Invocation i1 = method(Sample.class, "foo", Double.TYPE);
			assertEquals(AInteger.valueOf(123), i1.invoke(_this, AInteger.valueOf(125)));
		}
	}
	
	/**
	 * {@link anubis.runtime.java.Invocation#invoke(anubis.AnubisObject, anubis.AnubisObject[])} のためのテスト・メソッド。
	 */
	@Test(expected = JOverloadMismatchException.class)
	public void testInvoke02() throws Exception {
		JObject _this = (JObject) AObjects.getObject(new Sample());
		{
			Invocation i1 = method(Sample.class, "foo", Integer.TYPE);
			assertEquals(AInteger.valueOf(124), i1.invoke(_this, ADecimal.valueOf(124)));
		}
	}
	
	/**
	 * {@link anubis.runtime.java.Invocation#match(anubis.AnubisObject[])} のためのテスト・メソッド。
	 */
	@Test
	public void testMatch() throws Exception {
		{
			Invocation i1 = method(Sample.class, "foo");
			assertTrue(i1.match());
			assertFalse(i1.match(ADecimal.valueOf(BigDecimal.ZERO)));
		}
		{
			Invocation i1 = method(Sample.class, "foo", BigDecimal.class, BigInteger.class);
			assertTrue(i1.match(ADecimal.valueOf(BigDecimal.ZERO), AInteger.valueOf(BigInteger.ZERO)));
			assertFalse(i1.match(ADecimal.valueOf(BigDecimal.ZERO), ADecimal.valueOf(BigDecimal.ZERO)));
		}
	}
	
	private static MethodInvocation method(Class<?> cls, String name, Class<?>... args) throws NoSuchMethodException {
		return new MethodInvocation(cls.getDeclaredMethod(name, args));
	}
	
	private static class Sample {
		@SuppressWarnings("unused")
		public int foo() {
			return 123;
		}
		
		@SuppressWarnings("unused")
		public int foo(AnubisObject a) {
			return 123;
		}
		
		@SuppressWarnings("unused")
		public int foo(ANumber a) {
			return 123;
		}
		
		@SuppressWarnings("unused")
		public int foo(BigDecimal a) {
			return 123;
		}
		
		@SuppressWarnings("unused")
		public int foo(BigDecimal a, BigInteger b) {
			return 123;
		}
		
		@SuppressWarnings("unused")
		public int foo(BigInteger a) {
			return 123;
		}
		
		@SuppressWarnings("unused")
		public int foo(boolean a) {
			return 123;
		}
		
		@SuppressWarnings("unused")
		public int foo(Boolean a) {
			return 123;
		}
		
		@SuppressWarnings("unused")
		public int foo(double a) {
			return 123;
		}
		
		@SuppressWarnings("unused")
		public int foo(int a) {
			return a;
		}
		
		@SuppressWarnings("unused")
		public int foo(Integer a) {
			return a;
		}
		
		@SuppressWarnings("unused")
		public int foo(Object a) {
			return 123;
		}
		
		@SuppressWarnings("unused")
		public int foo(String a) {
			return 123;
		}
	}
}
