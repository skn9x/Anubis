package anubis.code.asm;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Test;
import anubis.AbstractTest;
import anubis.except.AssertionException;

public class StatementEmitterTest extends AbstractTest {
	@Test(expected = AssertionException.class)
	public void testEmitAssertStatement失敗１() throws Exception {
		exec("assert false;");
	}
	
	@Test
	public void testEmitAssertStatement失敗２() throws Exception {
		assertAEquals(1, exec("assert false else x = 1; end return x;"));
	}
	
	@Test
	public void testEmitAssertStatement成功() throws Exception {
		exec("assert true;");
		assertNull(exec("assert true else x = 1; end return x;"));
	}
	
	@Test
	public void testEmitBlockStatement() throws Exception {
		exec("1; 2;");
	}
	
	@Test
	public void testEmitBreakStatement() {
		fail("まだ実装されていません");
	}
	
	@Test
	public void testEmitConditionStatement() {
		fail("まだ実装されていません");
	}
	
	@Test
	public void testEmitContinueStatement() {
		fail("まだ実装されていません");
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
		fail("まだ実装されていません");
	}
	
	@Test
	public void testEmitMapStatement() {
		fail("まだ実装されていません");
	}
	
	@Test
	public void testEmitReturnStatement() throws Exception {
		assertAEquals(true, exec("return true;"));
		assertNull(exec("return;"));
	}
	
	@Test
	public void testEmitSwitchStatement() {
		fail("まだ実装されていません");
	}
	
	@Test
	public void testEmitThrowStatement() {
		fail("まだ実装されていません");
	}
	
	@Test
	public void testEmitTryCatchStatement() {
		fail("まだ実装されていません");
	}
	
	@Test
	public void testEmitTryFinallyStatement() {
		fail("まだ実装されていません");
	}
	
}
