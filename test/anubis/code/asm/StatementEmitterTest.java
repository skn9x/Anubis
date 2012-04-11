package anubis.code.asm;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Test;
import anubis.AbstractTest;
import anubis.except.AssertionException;

public class StatementEmitterTest extends AbstractTest {
	@Test(expected = AssertionException.class)
	public void testEmitAssertStatement���s�P() throws Exception {
		exec("assert false;");
	}
	
	@Test
	public void testEmitAssertStatement���s�Q() throws Exception {
		assertAEquals(1, exec("assert false else x = 1; end return x;"));
	}
	
	@Test
	public void testEmitAssertStatement����() throws Exception {
		exec("assert true;");
		assertNull(exec("assert true else x = 1; end return x;"));
	}
	
	@Test
	public void testEmitBlockStatement() throws Exception {
		exec("1; 2;");
	}
	
	@Test
	public void testEmitBreakStatement() {
		fail("�܂���������Ă��܂���");
	}
	
	@Test
	public void testEmitConditionStatement() {
		fail("�܂���������Ă��܂���");
	}
	
	@Test
	public void testEmitContinueStatement() {
		fail("�܂���������Ă��܂���");
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
		fail("�܂���������Ă��܂���");
	}
	
	@Test
	public void testEmitMapStatement() {
		fail("�܂���������Ă��܂���");
	}
	
	@Test
	public void testEmitReturnStatement() throws Exception {
		assertAEquals(true, exec("return true;"));
		assertNull(exec("return;"));
	}
	
	@Test
	public void testEmitSwitchStatement() {
		fail("�܂���������Ă��܂���");
	}
	
	@Test
	public void testEmitThrowStatement() {
		fail("�܂���������Ă��܂���");
	}
	
	@Test
	public void testEmitTryCatchStatement() {
		fail("�܂���������Ă��܂���");
	}
	
	@Test
	public void testEmitTryFinallyStatement() {
		fail("�܂���������Ă��܂���");
	}
	
}
