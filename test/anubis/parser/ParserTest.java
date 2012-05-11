package anubis.parser;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import anubis.ast.BinaryExpression;
import anubis.ast.CallExpression;
import anubis.ast.CompilationUnit;
import anubis.ast.GetSlotExpression;
import anubis.ast.GetSpecialExpression;
import anubis.ast.PrimitiveExpression;
import anubis.ast.ReturnStatement;
import anubis.ast.TernaryExpression;
import anubis.ast.UnaryExpression;
import anubis.except.AnubisParserException;
import anubis.runtime.AObjects;
import anubis.runtime.APrimitive;
import anubis.runtime.StandardObjectFactory;

/**
 * @author SiroKuro
 */
public class ParserTest {
	
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
	}
	
	@Test
	public void testParse() throws Exception {
		parse("");
		parse(";");
		parse(";;");
	}
	
	@Test
	public void testParseExpression() throws Exception {
		assertThat(parse("(123)"), instanceOf(PrimitiveExpression.class));
		assertThat(parse("+ 123"), instanceOf(CallExpression.class));
		assertThat(parse("- 123"), instanceOf(CallExpression.class));
		assertThat(parse("not 1"), instanceOf(UnaryExpression.class));
		assertThat(parse("1 isnull"), instanceOf(UnaryExpression.class));
		assertThat(parse("1 * 2"), instanceOf(CallExpression.class));
		assertThat(parse("1 / 2"), instanceOf(CallExpression.class));
		assertThat(parse("1 % 2"), instanceOf(CallExpression.class));
		assertThat(parse("1 \\ 2"), instanceOf(CallExpression.class));
		assertThat(parse("1 + 2"), instanceOf(CallExpression.class));
		assertThat(parse("1 - 2"), instanceOf(CallExpression.class));
		assertThat(parse("1 ?? 2"), instanceOf(BinaryExpression.class));
		assertThat(parse("123 ~= 456"), instanceOf(CallExpression.class));
		assertThat(parse("123 ^= 456 -> 789"), instanceOf(CallExpression.class));
		assertThat(parse("1 == 2"), instanceOf(BinaryExpression.class));
		assertThat(parse("1 <> 2"), instanceOf(BinaryExpression.class));
		assertThat(parse("1 != 2"), instanceOf(BinaryExpression.class));
		assertThat(parse("1 < 2"), instanceOf(CallExpression.class));
		assertThat(parse("1 <= 2"), instanceOf(CallExpression.class));
		assertThat(parse("1 > 2"), instanceOf(CallExpression.class));
		assertThat(parse("1 >= 2"), instanceOf(CallExpression.class));
		assertThat(parse("true and true"), instanceOf(BinaryExpression.class));
		assertThat(parse("true or true"), instanceOf(BinaryExpression.class));
		assertThat(parse("true xor true"), instanceOf(BinaryExpression.class));
		assertThat(parse("true ? 1 : 2"), instanceOf(TernaryExpression.class));
		assertThat(parse("x := 1"), instanceOf(CallExpression.class));
		assertThat(parse("x = 1"), instanceOf(CallExpression.class));
		assertThat(parse("super = 1"), instanceOf(CallExpression.class));
		assertThat(parse("outer = 1"), instanceOf(CallExpression.class));
		assertThat(parse("this.x += 1"), instanceOf(CallExpression.class));
		assertThat(parse("this.super -= 1"), instanceOf(CallExpression.class));
		assertThat(parse("this.outer *= 1"), instanceOf(CallExpression.class));
		assertThat(parse("123.x /= 1"), instanceOf(CallExpression.class));
		assertThat(parse("123[456] %= 1"), instanceOf(CallExpression.class));
		assertThat(parse("123.value"), instanceOf(GetSlotExpression.class));
		assertThat(parse("123.this"), instanceOf(GetSpecialExpression.class));
		assertThat(parse("123.super"), instanceOf(GetSpecialExpression.class));
		assertThat(parse("123.outer"), instanceOf(GetSpecialExpression.class));
		assertThat(parse("sample()"), instanceOf(CallExpression.class));
		assertThat(parse("sample(123)"), instanceOf(CallExpression.class));
		assertThat(parse("sample(123, abc)"), instanceOf(CallExpression.class));
		assertThat(parse("sample(@this)"), instanceOf(CallExpression.class));
		assertThat(parse("sample(@this, 123, abc)"), instanceOf(CallExpression.class));
		assertThat(parse("123.sample()"), instanceOf(CallExpression.class));
		//assertThat(parse("this::sample()"), instanceOf(UnaryExpression.class));
		//assertThat(parse("super::sample()"), instanceOf(UnaryExpression.class));
		//assertThat(parse("outer::sample()"), instanceOf(UnaryExpression.class));
		//assertThat(parse("local::sample()"), instanceOf(UnaryExpression.class));
		assertThat(parse("123[2 + 3]"), instanceOf(BinaryExpression.class));
		assertThat(parse("\"abcdef\"[2]"), instanceOf(BinaryExpression.class));
	}
	
	@Test
	public void testParsePrimitive() throws Exception {
		assertNull(getPrimitive(parse("void")));
		assertEquals(AObjects.getNull(), getPrimitive(parse("null")));
		assertEquals(AObjects.getTrue(), getPrimitive(parse("true")));
		assertEquals(AObjects.getFalse(), getPrimitive(parse("false")));
		assertEquals(AObjects.getNumber(123), getPrimitive(parse("123")));
		assertEquals(AObjects.getNumber(123.456), getPrimitive(parse("123.456")));
		assertEquals(AObjects.getString("abc"), getPrimitive(parse("\"abc\"")));
		assertEquals(AObjects.getString("abc"), getPrimitive(parse("\'abc\'")));
		assertEquals(AObjects.getObject(Pattern.compile("abc")), getPrimitive(parse("$\"abc\"")));
	}
	
	@Test
	public void testParseStatement() throws Exception {
		parse(";");
		parse(";;");
		parse("x = 1 + 2 + 3;");
		parse("return;");
		parse("return 123;");
		parse("break;");
		parse("break LABEL;");
		parse("continue;");
		parse("continue LABEL;");
		parse("throw null;");
		parse("assert x == 0;");
		parse("x = 1 + 2 + 3 @if x == 1;");
		parse("return 123 @if x == 1;");
		parse("break LABEL @if x == 1;");
		parse("continue LABEL @if x == 1;");
		parse("throw null @if x == 1;");
		parse("x = 1 + 2 + 3 @for x: [1..5];");
		parse("x = 1 + 2 + 3 @while abc;");
		parse("if x == 0 then y = 1; end");
		parse("if x == 0 then y = 1; else z = 1; end");
		parse("for x: [1..5] do println(); end");
		parse("for x: [1..5] do println(); else println(); end");
		parse("while abc do println(); end");
		parse("while abc do println(); else println(); end");
		parse("try abc; catch ex do println(ex); end");
		parse("try abc; finally println(fin); end");
		parse("try abc; catch ex do println(ex); finally println(fin); end");
		parse("switch x case 1 then println(); end");
		parse("switch x case 1 then println(); case 2 then println(); end");
		parse("switch x case 1 then println(); case 2 then println(); else println(); end");
		parse("assert x == 0 else throw void; end");
		parse("LABEL: if x == 0 then y = 1; end");
		parse("LABEL: for x: [1..5] do println(); end");
		parse("LABEL: while abc do println(); end");
		parse("LABEL: try abc; catch ex do println(ex); finally println(fin); end");
		parse("LABEL: switch x case 1 then println(); case 2 then println(); else println(); end");
	}
	
	@Test
	public void testParseTerm() throws Exception {
		parse("var");
		parse("this");
		parse("super");
		parse("outer");
		parse("local");
		parse("[]");
		parse("[1]");
		parse("[1, 2]");
		parse("[1..]");
		parse("[1..9]");
		parse("[1..;2]");
		parse("[1..9;2]");
		parse("%[]");
		parse("%[1 -> 2]");
		parse("%[1 -> 2, 3 -> 4]");
		parse("$[]");
		parse("$[1]");
		parse("$[1, 2]");
		parse("object{}");
		parse("object{ 1; 2; 3; }");
		parse("function(){}");
		parse("function(a, b, c){ 1; 2; 3; }");
		parse("{-> 123}");
		parse("{a, b -> a + b}");
		parse("do{ }");
		parse("do{ 1; 2; 3; }");
	}
	
	@Test(expected = AnubisParserException.class)
	public void testParseWithError() throws Exception {
		parse("for");
	}
	
	private static APrimitive getPrimitive(Object obj) {
		if (obj instanceof APrimitive)
			return (APrimitive) obj;
		else if (obj instanceof PrimitiveExpression)
			return ((PrimitiveExpression) obj).getValue();
		else
			return null;
	}
	
	private static Object parse(String code) throws IOException, Parser.yyException {
		CompilationUnit node = new Parser().parse(new StringReader(code));
		if (node instanceof ReturnStatement) {
			return ((ReturnStatement) node).getExpression();
		}
		return node;
	}
}
