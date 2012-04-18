package anubis.except;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import anubis.AnubisObject;
import anubis.runtime.AString;

/**
 * @author SiroKuro
 */
public class ExceptionProviderTest {
	private final AnubisObject object = AString.valueOf("ABC");
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * {@link anubis.except.ExceptionProvider#newFactoryNotReady()} のためのテスト・メソッド。
	 */
	@Test
	public void testNewFactoryNotReady() {
		FactoryNotReadyException result = ExceptionProvider.newFactoryNotReady();
		assertNotNull(result);
		System.out.println(result.getMessage());
	}
	
	/**
	 * {@link anubis.except.ExceptionProvider#newJOverloadMismatch()} のためのテスト・メソッド。
	 */
	@Test
	public void testNewJOverloadMismatch() {
		JOverloadMismatchException result = ExceptionProvider.newJOverloadMismatch();
		assertNotNull(result);
		System.out.println(result.getMessage());
	}
	
	/**
	 * {@link anubis.except.ExceptionProvider#newJOverloadMismatch(java.lang.ClassCastException)} のためのテスト・メソッド。
	 */
	@Test
	public void testNewJOverloadMismatch2() {
		JOverloadMismatchException result = ExceptionProvider.newJOverloadMismatch(new ClassCastException("cast test"));
		assertNotNull(result);
		System.out.println(result.getMessage());
	}
	
	/**
	 * {@link anubis.except.ExceptionProvider#newNotCallable(anubis.AnubisObject)} のためのテスト・メソッド。
	 */
	@Test
	public void testNewNotCallable() {
		NotCallableException result = ExceptionProvider.newNotCallable(object);
		assertNotNull(result);
		System.out.println(result.getMessage());
	}
	
	/**
	 * {@link anubis.except.ExceptionProvider#newParseException(java.lang.Throwable)} のためのテスト・メソッド。
	 */
	@Test
	public void testNewParseException() {
		AnubisParserException result = ExceptionProvider.newParseException(new Exception("parse test"));
		assertNotNull(result);
		System.out.println(result.getMessage());
	}
	
	/**
	 * {@link anubis.except.ExceptionProvider#newParseExceptionByInvalidChars(java.lang.String, java.lang.String, int, int)} のためのテスト・メソッド。
	 */
	@Test
	public void testNewParseExceptionByInvalidChars() {
		AnubisParserException result = ExceptionProvider.newParseExceptionByInvalidChars("a", "64", 1, 2);
		assertNotNull(result);
		System.out.println(result.getMessage());
	}
	
	/**
	 * {@link anubis.except.ExceptionProvider#newParseExceptionByStringNotTerminated(java.lang.String, int, int)} のためのテスト・メソッド。
	 */
	@Test
	public void testNewParseExceptionByStringNotTerminated() {
		AnubisParserException result = ExceptionProvider.newParseExceptionByStringNotTerminated("string", 1, 2);
		assertNotNull(result);
		System.out.println(result.getMessage());
	}
	
	/**
	 * {@link anubis.except.ExceptionProvider#newSlotNotFound(anubis.AnubisObject, java.lang.String)} のためのテスト・メソッド。
	 */
	@Test
	public void testNewSlotNotFound() {
		SlotNotFoundException result = ExceptionProvider.newSlotNotFound(object, "abc");
		assertNotNull(result);
		System.out.println(result.getMessage());
	}
	
	/**
	 * {@link anubis.except.ExceptionProvider#newSlotReadonly(java.lang.String)} のためのテスト・メソッド。
	 */
	@Test
	public void testNewSlotReadonly1() {
		SlotReadonlyException result = ExceptionProvider.newSlotReadonly("abc");
		assertNotNull(result);
		System.out.println(result.getMessage());
	}
	
	/**
	 * {@link anubis.except.ExceptionProvider#newSlotReadonly(anubis.AnubisObject, java.lang.String)} のためのテスト・メソッド。
	 */
	@Test
	public void testNewSlotReadonly2() {
		SlotReadonlyException result = ExceptionProvider.newSlotReadonly(object, "abc");
		assertNotNull(result);
		System.out.println(result.getMessage());
	}
	
	/**
	 * {@link anubis.except.ExceptionProvider#newSlotReadonly(java.lang.Throwable, anubis.AnubisObject, java.lang.String)} のためのテスト・メソッド。
	 */
	@Test
	public void testNewSlotReadonly3() {
		Exception cause = new Exception("sample exception");
		SlotReadonlyException result = ExceptionProvider.newSlotReadonly(cause, object, "abc");
		assertNotNull(result);
		assertEquals(cause, result.getCause());
		System.out.println(result.getMessage());
	}
	
	/**
	 * {@link anubis.except.ExceptionProvider#newVoidOperation()} のためのテスト・メソッド。
	 */
	@Test
	public void testNewVoidOperation() {
		VoidOperationException result = ExceptionProvider.newVoidOperation();
		assertNotNull(result);
		System.out.println(result.getMessage());
	}
	
	/**
	 * {@link anubis.except.ExceptionProvider#wrapRuntimeException(java.lang.Throwable)} のためのテスト・メソッド。
	 */
	@Test
	public void testWrapRuntimeException() {
		Exception ex = new Exception("sample exception");
		AnubisRuntimeException result = ExceptionProvider.wrapRuntimeException(ex);
		assertNotNull(result);
		System.out.println(result.getMessage());
	}
}
