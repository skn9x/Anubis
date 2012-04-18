package anubis.code.asm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.Test;
import anubis.AbstractTest;
import anubis.SpecialSlot;
import anubis.runtime.AList;
import anubis.runtime.AMap;
import anubis.runtime.AObjects;
import anubis.runtime.ARange;
import anubis.runtime.ASet;
import anubis.runtime.AUserFunction;

public class ExpressionEmitterTest extends AbstractTest {
	@Test
	public void testEmitAssignExpression() throws Exception {
		assertAEquals(1, exec("x = 1"));
		assertAEquals(1, exec("x = 1; return x;"));
		assertAEquals(9, exec("x = 1; x = 9; return x;"));
		assertAEquals(3, exec("x = 1; return x += 2;"));
		assertAEquals(3, exec("x = 1; x += 2; return x;"));
		assertAEquals(-1, exec("x = 1; return x -= 2;"));
		assertAEquals(-1, exec("x = 1; x -= 2; return x;"));
		assertAEquals(2, exec("x = 1; return x *= 2;"));
		assertAEquals(2, exec("x = 1; x *= 2; return x;"));
		assertAEquals(0, exec("x = 1; return x /= 2;"));
		assertAEquals(0, exec("x = 1; x /= 2; return x;"));
		assertAEquals(0.5, exec("x = 1; return x \\= 2;"));
		assertAEquals(0.5, exec("x = 1; x \\= 2; return x;"));
		assertAEquals(4, exec("x = 9; return x %= 5;"));
		assertAEquals(4, exec("x = 9; x %= 5; return x;"));
	}
	
	@Test
	public void testEmitBinaryExpression() throws Exception {
		// and
		{
			assertAEquals(true, exec("true and true"));
			assertAEquals(false, exec("false and true"));
			assertAEquals(false, exec("true and false"));
			assertAEquals(false, exec("false and false"));
		}
		// or
		{
			assertAEquals(true, exec("true or true"));
			assertAEquals(true, exec("false or true"));
			assertAEquals(true, exec("true or false"));
			assertAEquals(false, exec("false or false"));
		}
		// xor
		{
			assertAEquals(false, exec("true xor true"));
			assertAEquals(true, exec("false xor true"));
			assertAEquals(true, exec("true xor false"));
			assertAEquals(false, exec("false xor false"));
		}
		// equals
		{
			assertAEquals(true, exec("void == void"));
			assertAEquals(true, exec("1234 == 1234"));
			assertAEquals(false, exec("void == 1234"));
			assertAEquals(false, exec("1234 == void"));
		}
		// notequals
		{
			assertAEquals(false, exec("void != void"));
			assertAEquals(false, exec("1234 != 1234"));
			assertAEquals(true, exec("void != 1234"));
			assertAEquals(true, exec("1234 != void"));
		}
		// ifnull
		{
			assertNull(exec("void ?? void"));
			assertAEquals(null, exec("void ?? null"));
			assertAEquals(5678, exec("void ?? 5678"));
			assertAEquals(5678, exec("null ?? 5678"));
			assertAEquals(1234, exec("1234 ?? void"));
			assertAEquals(1234, exec("1234 ?? 5678"));
		}
		// index
		{
			assertNull(exec("[1, 2, 3][-1]"));
			assertAEquals(1, exec("[1, 2, 3][0]"));
			assertAEquals(2, exec("[1, 2, 3][1]"));
			assertAEquals(3, exec("[1, 2, 3][2]"));
			assertNull(exec("[1, 2, 3][3]"));
			
			assertAEquals(2, exec("%[1 -> 2, 3 -> 4][1]"));
			assertAEquals(4, exec("%[1 -> 2, 3 -> 4][3]"));
			assertNull(exec("%[1 -> 2, 3 -> 4][5]"));
		}
	}
	
	@Test
	public void testEmitBlockExpression() throws Exception {
		assertNull(exec("do{}"));
		assertAEquals(1, exec("do { x = 1; return x; }"));
	}
	
	@Test
	public void testEmitCallExpression() throws Exception {
		// 0 引数
		assertAEquals(1, exec("1.`+p`()"));
		assertAEquals(-2, exec("2.`-n`()"));
		assertAEquals(3, exec("1.`+p`(@3)"));
		assertAEquals(-4, exec("2.`-n`(@4)"));
		
		// 1 引数
		assertAEquals(3, exec("1.`+`(2)"));
		assertAEquals(5, exec("1.`+`(@3, 2)"));
		assertAEquals(-1, exec("1.`-`(2)"));
		assertAEquals(1, exec("1.`-`(@3, 2)"));
	}
	
	@Test
	public void testEmitGetSlotExpression() throws Exception {
		assertEquals(AObjects.getNumber(1).findSlot("+"), exec("1.`+`"));
		assertNull(exec("1.foobar"));
	}
	
	@Test
	public void testEmitGetSpecialExpression() throws Exception {
		assertEquals(_this, exec("local.this"));
		assertEquals(AObjects.getTraitsFactory().getRoot(), exec("local.super"));
		assertEquals(outer, exec("local.outer"));
		
		assertAEquals(1, exec("1.this"));
		assertEquals(AObjects.getNumber(1).getSlot(SpecialSlot.SUPER), exec("1.super"));
		assertNull(exec("1.outer"));
	}
	
	@Test
	public void testEmitLocalExpression() throws Exception {
		assertEquals(local, exec("local"));
	}
	
	@Test
	public void testEmitNewFunctionExpression() throws Exception {
		assertTrue(exec("function(){}") instanceof AUserFunction);
		assertTrue(exec("function(args){}") instanceof AUserFunction);
		assertTrue(exec("{->1234}") instanceof AUserFunction);
		assertTrue(exec("{args->1234}") instanceof AUserFunction);
		assertTrue(exec("{=>}") instanceof AUserFunction);
		assertTrue(exec("{args => return 123 + 456;}") instanceof AUserFunction);
	}
	
	@Test
	public void testEmitNewListExpression() throws Exception {
		{
			AList result = (AList) exec("[]");
			assertNotNull(result);
			assertEquals(0, result.size());
		}
		{
			AList result = (AList) exec("[1]");
			assertNotNull(result);
			assertEquals(1, result.size());
			assertAEquals(1, result.getItem(AObjects.getNumber(0)));
		}
	}
	
	@Test
	public void testEmitNewMapExpression() throws Exception {
		{
			AMap result = (AMap) exec("%[]");
			assertNotNull(result);
			assertEquals(0, result.size());
		}
		{
			AMap result = (AMap) exec("%[1->2]");
			assertNotNull(result);
			assertEquals(1, result.size());
			assertAEquals(2, result.getItem(AObjects.getNumber(1)));
		}
		{
			AMap result = (AMap) exec("%[1->2, 3->4]");
			assertNotNull(result);
			assertEquals(2, result.size());
			assertAEquals(2, result.getItem(AObjects.getNumber(1)));
			assertAEquals(4, result.getItem(AObjects.getNumber(3)));
		}
	}
	
	@Test
	public void testEmitNewObjectExpression() throws Exception {
		assertNotNull(exec("object{}"));
	}
	
	@Test
	public void testEmitNewRangeExpression() throws Exception {
		assertTrue(exec("[1..2; 3]") instanceof ARange);
		assertTrue(exec("[1..]") instanceof ARange);
	}
	
	@Test
	public void testEmitNewSetExpression() throws Exception {
		{
			ASet result = (ASet) exec("$[]");
			assertNotNull(result);
			assertEquals(0, result.size());
		}
		{
			ASet result = (ASet) exec("$[1, 1, 1, 1]");
			assertNotNull(result);
			assertEquals(1, result.size());
		}
	}
	
	@Test
	public void testEmitPrimitiveExpression() throws Exception {
		assertNull(exec("void"));
		assertAEquals(null, exec("null"));
		assertAEquals(true, exec("true"));
		assertAEquals(false, exec("false"));
		assertAEquals(1234, exec("1234"));
		assertAEquals(12.34, exec("12.34"));
		assertAEquals(new BigInteger("123456789012345678901234567890123456789012345678901234567890"),
				exec("123456789012345678901234567890123456789012345678901234567890"));
		assertAEquals(new BigDecimal("12345678901234567890.1234567890123456789012345678901234567890"),
				exec("12345678901234567890.1234567890123456789012345678901234567890"));
		assertAEquals("abc", exec("\"abc\""));
	}
	
	@Test
	public void testEmitTernaryExpression() throws Exception {
		// condition
		{
			assertAEquals(2, exec("true ? 2 : 3"));
			assertAEquals(3, exec("false ? 2 : 3"));
		}
		// slice
		{
			AList result = (AList) exec("[0, 1, 2, 3][1..2]");
			assertEquals(2, result.size());
			assertAEquals(1, result.getItem(AObjects.getNumber(0)));
			assertAEquals(2, result.getItem(AObjects.getNumber(1)));
		}
		{
			AList result = (AList) exec("[0, 1, 2, 3][..1]");
			assertEquals(2, result.size());
			assertAEquals(0, result.getItem(AObjects.getNumber(0)));
			assertAEquals(1, result.getItem(AObjects.getNumber(1)));
		}
		{
			AList result = (AList) exec("[0, 1, 2, 3][2..]");
			assertEquals(2, result.size());
			assertAEquals(2, result.getItem(AObjects.getNumber(0)));
			assertAEquals(3, result.getItem(AObjects.getNumber(1)));
		}
	}
	
	@Test
	public void testEmitUnaryExpression() throws Exception {
		assertAEquals(false, exec("not true"));
		assertAEquals(true, exec("not false"));
		
		assertAEquals(true, exec("void isnull"));
		assertAEquals(true, exec("null isnull"));
		assertAEquals(false, exec("1234 isnull"));
		
		assertAEquals(false, exec("void istrue"));
		assertAEquals(false, exec("null istrue"));
		assertAEquals(false, exec("false istrue"));
		assertAEquals(true, exec("true istrue"));
		assertAEquals(true, exec("1234 istrue"));
		
		assertAEquals(true, exec("void isfalse"));
		assertAEquals(true, exec("null isfalse"));
		assertAEquals(true, exec("false isfalse"));
		assertAEquals(false, exec("true isfalse"));
		assertAEquals(false, exec("1234 isfalse"));
	}
}
