package anubis.parser;

import static anubis.parser.ParserHelper.getHexCode;
import static anubis.parser.ParserHelper.parseNumber;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.Test;

public class ParserHelperTest {
	
	@Test
	public void testEscape() {
		fail("‚Ü‚¾ŽÀ‘•‚³‚ê‚Ä‚¢‚Ü‚¹‚ñ");
	}
	
	@Test
	public void testGetHexCode() {
		assertEquals("", getHexCode(null));
		assertEquals("", getHexCode(""));
		assertEquals("0000", getHexCode("\u0000"));
		assertEquals("00FF", getHexCode("\u00FF"));
		assertEquals("0100", getHexCode("\u0100"));
		assertEquals("FFFF", getHexCode("\uFFFF"));
		assertEquals("006100620063304230443046", getHexCode("abc‚ ‚¢‚¤"));
	}
	
	@Test
	public void testMayQuoteIdentifier() {
		fail("‚Ü‚¾ŽÀ‘•‚³‚ê‚Ä‚¢‚Ü‚¹‚ñ");
	}
	
	@Test
	public void testParseNumber() {
		assertEquals(BigInteger.ZERO, parseNumber("00"));
		assertEquals(BigInteger.ONE, parseNumber("+01"));
		assertEquals(BigInteger.TEN.negate(), parseNumber("-10"));
		
		assertEquals(BigInteger.ZERO, parseNumber("0x00"));
		assertEquals(BigInteger.ONE, parseNumber("+0x01"));
		assertEquals(BigInteger.TEN.negate(), parseNumber("-0x0A"));
		
		assertEquals(BigInteger.ZERO, parseNumber("0b0"));
		assertEquals(BigInteger.ONE, parseNumber("+0b1"));
		assertEquals(BigInteger.TEN.negate(), parseNumber("-0b1010"));
		
		assertEquals(new BigDecimal("0.0"), parseNumber("0.0"));
		assertEquals(new BigDecimal("1.0"), parseNumber("+1.0"));
		assertEquals(new BigDecimal("-10.0"), parseNumber("-10.0"));
		
		assertEquals(new BigDecimal("-.2"), parseNumber("-.2"));
		assertEquals(new BigDecimal("-3."), parseNumber("-3."));
	}
	
	@Test
	public void testQuote() {
		fail("‚Ü‚¾ŽÀ‘•‚³‚ê‚Ä‚¢‚Ü‚¹‚ñ");
	}
	
	@Test
	public void testUnescape() {
		fail("‚Ü‚¾ŽÀ‘•‚³‚ê‚Ä‚¢‚Ü‚¹‚ñ");
	}
	
	@Test
	public void testUnescapeSimple() {
		fail("‚Ü‚¾ŽÀ‘•‚³‚ê‚Ä‚¢‚Ü‚¹‚ñ");
	}
	
	@Test
	public void testUnquote() {
		fail("‚Ü‚¾ŽÀ‘•‚³‚ê‚Ä‚¢‚Ü‚¹‚ñ");
	}
	
}
