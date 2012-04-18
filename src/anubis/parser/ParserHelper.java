package anubis.parser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ParserHelper {
	private static final String[] keywords = { // TODO 随時見直し
		"object",
		"record",
		"function",
		"end",
		"def",
		"if",
		"then",
		"else",
		"for",
		"while",
		"do",
		"try",
		"catch",
		"finally",
		"switch",
		"case",
		"assert",
		"break",
		"continue",
		"throw",
		"return",
		"or",
		"xor",
		"and",
		"not",
		"isnull",
		"this",
		"super",
		"outer",
		"local",
		"null",
		"void",
		"true",
		"false",
	};
	
	private static final Pattern identifier = Pattern.compile("^[A-Za-z\\$_][0-9A-Za-z\\$_]*$");
	
	static {
		Arrays.sort(keywords);
	}
	
	private ParserHelper() {
	}
	
	/**
	 * 文字列内の特殊文字をエスケープします
	 * @param str 特殊文字を含む文字列
	 * @return エスケープ文字を含む文字列
	 */
	public static String escape(String str) {
		StringBuilder sb = new StringBuilder();
		if (str != null) {
			for (char c: str.toCharArray()) {
				switch (c) {
					case '\n':
						sb.append("\\n");
						break;
					case '\t':
						sb.append("\\t");
						break;
					case '\b':
						sb.append("\\b");
						break;
					case '\r':
						sb.append("\\r");
						break;
					case '\f':
						sb.append("\\f");
						break;
					case '\\':
						sb.append("\\\\");
						break;
					case '\"':
						sb.append("\\\"");
						break;
					case '\'':
						sb.append("\\\'");
						break;
					case '`':
						sb.append("\\`");
						break;
					default:
						sb.append(c);
						break;
				}
			}
		}
		return sb.toString();
	}
	
	public static String getHexCode(String text) {
		StringBuilder code = new StringBuilder();
		if (text != null) {
			for (char c: text.toCharArray()) {
				code.append(String.format("%04X", (int) c));
			}
		}
		return code.toString();
	}
	
	/**
	 * str に識別子として不適切な文字を含むときは `` で囲みます。
	 * @param str
	 * @return
	 */
	public static String mayQuoteIdentifier(String str) {
		if (str == null) {
			return "";
		}
		else {
			if (Arrays.binarySearch(keywords, str) < 0 && identifier.matcher(str).find())
				return str;
			return "`" + escape(str) + "`";
		}
	}
	
	/**
	 * 数値トークンを Number オブジェクトへパースします。
	 * @param token 数値トークン
	 * @return　Number オブジェクト
	 */
	public static Number parseNumber(String token) {
		boolean negate = token.startsWith("-");
		if (token.startsWith("+") || token.startsWith("-")) {
			token = token.substring(1);
		}
		
		if (token.startsWith("0x")) {
			BigInteger result = new BigInteger(token.substring(2), 16);
			return negate ? result.negate() : result;
		}
		else if (token.startsWith("0b")) {
			BigInteger result = new BigInteger(token.substring(2), 2);
			return negate ? result.negate() : result;
		}
		else {
			if (0 <= token.indexOf('.')) {
				BigDecimal result = new BigDecimal(token, MathContext.UNLIMITED);
				return negate ? result.negate() : result;
			}
			else {
				BigInteger result = new BigInteger(token, 10);
				return negate ? result.negate() : result;
			}
		}
	}
}
