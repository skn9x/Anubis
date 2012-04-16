package anubis.code.asm;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Test;
import anubis.AbstractTest;
import anubis.except.AnubisUserException;
import anubis.except.AssertionException;

public class StatementEmitterTest extends AbstractTest {
	@Test(expected = AssertionException.class)
	public void testEmitAssertStatementé∏îsÇP() throws Exception {
		exec("assert false;");
	}
	
	@Test
	public void testEmitAssertStatementé∏îsÇQ() throws Exception {
		assertAEquals(1, exec("assert false else x = 1; end return x;"));
	}
	
	@Test
	public void testEmitAssertStatementê¨å˜() throws Exception {
		exec("assert true;");
		assertNull(exec("assert true else x = 1; end return x;"));
	}
	
	@Test
	public void testEmitBlockStatement() throws Exception {
		exec("1; 2;");
	}
	
	@Test
	public void testEmitBreakStatement() throws Exception {
		assertNull(exec("break; return 1;"));
		assertNull(exec("LABEL: assert false else break LABEL; return 1; end"));
		assertAEquals(1, exec("for x: [1..3] do break; else x = 4; end return x;"));
		assertAEquals(1, exec("x = 0; while true do x += 1; break; end return x;"));
		assertAEquals(1, exec("IF: if true then x = 1; break IF; x = 2; end return x;"));
		exec("try break; finally end");
	}
	
	@Test(expected = RuntimeException.class)
	// TODO êÍópÇÃó·äOÇ÷
	public void testEmitBreakStatementNG() throws Exception {
		exec("try finally break; end");
	}
	
	@Test
	public void testEmitContinueStatement() throws Exception {
		assertNull(exec("continue; return 1;"));
		assertNull(exec("LABEL: assert false else continue LABEL; return 1; end"));
		assertAEquals(4, exec("for x: [1..3] do continue; else x = 4; end return x;"));
		assertAEquals(4, exec("x = 0; while x < 4 do x += 1; continue; x += 1; end return x;"));
		assertAEquals(1, exec("IF: if true then x = 1; continue IF; x = 2; end return x;"));
		exec("try continue; finally end");
	}
	
	@Test(expected = RuntimeException.class)
	// TODO êÍópÇÃó·äOÇ÷
	public void testEmitContinueStatementNG() throws Exception {
		exec("try finally continue; end");
	}
	
	@Test
	public void testEmitEmptyStatement() throws Exception {
		assertNull(exec(""));
	}
	
	@Test
	public void testEmitExpressionStatement() throws Exception {
		assertNull(exec("1 + 2 + 3;"));
	}
	
	@Test
	public void testEmitForStatement() throws Exception {
		assertAEquals(10, exec("sum = 0; for x: [1, 2, 3] do sum += x; else sum += 4; end return sum;"));
	}
	
	@Test
	public void testEmitIfStatement() throws Exception {
		assertAEquals(3, exec("x = 1; x += 2 @if true; return x;"));
		assertAEquals(1, exec("if true then x = 1; else x = 2; end return x;"));
		assertAEquals(2, exec("if false then x = 1; else x = 2; end return x;"));
	}
	
	@Test
	public void testEmitReturnStatement() throws Exception {
		assertAEquals(true, exec("return true;"));
		assertNull(exec("return;"));
	}
	
	@Test
	public void testEmitSwitchStatement() {
		fail("Ç‹Çæé¿ëïÇ≥ÇÍÇƒÇ¢Ç‹ÇπÇÒ");
	}
	
	@Test(expected = AnubisUserException.class)
	public void testEmitThrowStatement() throws Exception {
		exec("throw;");
	}
	
	@Test
	public void testEmitTryCatchStatement() throws Exception {
		assertAEquals(3, exec("try throw 3; catch ex do return ex; end"));
	}
	
	@Test(expected = AnubisUserException.class)
	public void testEmitTryFinallyStatementàŸèÌån() throws Exception {
		assertNull(exec("try throw; assert false; finally x = 1; end return x;"));
	}
	
	@Test
	public void testEmitTryFinallyStatementê≥èÌån() throws Exception {
		assertAEquals(3, exec("try x = 1; finally x += 2; end return x;"));
	}
	
	@Test(timeout = 1000)
	public void testEmitWhileStatement() throws Exception {
		assertAEquals(10, exec("x = 1; while x < 10 do x += 1; end return x;"));
		assertAEquals(-1, exec("x = 1; while x < 10 do x += 1; else x = -1; end return x;"));
	}
}
