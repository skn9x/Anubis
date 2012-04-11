package anubis.code.asm;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Test;
import anubis.AbstractTest;
import anubis.except.AssertionException;

public class StatementEmitterTest extends AbstractTest {
	@Test(expected = AssertionException.class)
	public void testEmitAssertStatement¸”s‚P() throws Exception {
		exec("assert false;");
	}
	
	@Test
	public void testEmitAssertStatement¸”s‚Q() throws Exception {
		assertAEquals(1, exec("assert false else x = 1; end return x;"));
	}
	
	@Test
	public void testEmitAssertStatement¬Œ÷() throws Exception {
		exec("assert true;");
		assertNull(exec("assert true else x = 1; end return x;"));
	}
	
	@Test
	public void testEmitBlockStatement() throws Exception {
		exec("1; 2;");
	}
	
	@Test
	public void testEmitBreakStatement() {
		fail("‚Ü‚¾À‘•‚³‚ê‚Ä‚¢‚Ü‚¹‚ñ");
	}
	
	@Test
	public void testEmitConditionStatement() {
		fail("‚Ü‚¾À‘•‚³‚ê‚Ä‚¢‚Ü‚¹‚ñ");
	}
	
	@Test
	public void testEmitContinueStatement() {
		fail("‚Ü‚¾À‘•‚³‚ê‚Ä‚¢‚Ü‚¹‚ñ");
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
	public void testEmitLoopStatement() {
		fail("‚Ü‚¾À‘•‚³‚ê‚Ä‚¢‚Ü‚¹‚ñ");
	}
	
	@Test
	public void testEmitMapStatement() {
		fail("‚Ü‚¾À‘•‚³‚ê‚Ä‚¢‚Ü‚¹‚ñ");
	}
	
	@Test
	public void testEmitReturnStatement() throws Exception {
		assertAEquals(true, exec("return true;"));
		assertNull(exec("return;"));
	}
	
	@Test
	public void testEmitSwitchStatement() {
		fail("‚Ü‚¾À‘•‚³‚ê‚Ä‚¢‚Ü‚¹‚ñ");
	}
	
	@Test
	public void testEmitThrowStatement() {
		fail("‚Ü‚¾À‘•‚³‚ê‚Ä‚¢‚Ü‚¹‚ñ");
	}
	
	@Test
	public void testEmitTryCatchStatement() {
		fail("‚Ü‚¾À‘•‚³‚ê‚Ä‚¢‚Ü‚¹‚ñ");
	}
	
	@Test
	public void testEmitTryFinallyStatement() {
		fail("‚Ü‚¾À‘•‚³‚ê‚Ä‚¢‚Ü‚¹‚ñ");
	}
	
}
