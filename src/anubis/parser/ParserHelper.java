package anubis.parser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ParserHelper {
	private static final String[] keywords = { // TODO あとで直す
		"do",
		"end",
		"if",
		"elsif",
		"then",
		"else",
		"for",
		"in",
		"while",
		"break",
		"continue",
		"case",
		"of",
		"return",
		"let",
		"try",
		"catch",
		"finally",
		"throw",
		"assert",
		"lock",
		"this",
		"local",
		"super",
		"outer",
		"true",
		"false",
		"null",
		"void",
		"not",
		"and",
		"or",
		"xor",
	};
	
	private static final Pattern identifier = Pattern.compile("^[A-Za-z\\$_][A-Za-z0-9\\$_]*$");
	private static final Pattern trimBlockString = Pattern.compile("^(\r\n|\r|\n)|(\r\n|\r|\n)$");
	private static final Pattern unescapeBlockString = Pattern.compile("^(.)", Pattern.MULTILINE);
	
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
		if (Arrays.binarySearch(keywords, str) < 0 && identifier.matcher(str).find())
			return str;
		return "`" + escape(str) + "`";
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
	
	/**
	 * 文字列を " で囲みます
	 * @param str
	 * @return
	 */
	public static String quote(String str) {
		return "\"" + escape(str) + "\"";
	}
	
	/**
	 * 文字列内のエスケープ文字を特殊文字に置き換えます
	 * @param str エスケープ文字を含む文字列
	 * @return 特殊文字を含む文字列
	 */
	public static String unescape(String str) {
		int index = 0, point = str.indexOf('\\');
		if (0 <= point) {
			// \n, \N, \r, \R, \t, \b, \f, \\, \", \', \`
			StringBuilder sb = new StringBuilder();
			do {
				sb.append(str.substring(index, point));
				if (point + 1 < str.length()) {
					char c = str.charAt(point + 1);
					switch (c) {
						case 'n':
							sb.append('\n');
							break;
						case 'N':
							sb.append(System.getProperty("line.separator", "\r\n"));
							break;
						case 'r':
							sb.append('\r');
							break;
						case 'R':
							sb.append(System.getProperty("line.separator", "\r\n"));
							break;
						case 'b':
							sb.append('\b');
							break;
						case 't':
							sb.append('\t');
							break;
						case 'f':
							sb.append('\f');
							break;
						case '\\':
							sb.append('\\');
							break;
						case '\"':
							sb.append('\"');
							break;
						case '\'':
							sb.append('\'');
							break;
						case '`':
							sb.append('`');
							break;
						default:
							sb.append(c);
							break;
					}
				}
				index = point + 2;
				point = str.indexOf('\\', index);
			} while (0 <= point);
			sb.append(str.substring(index));
			str = sb.toString();
		}
		return str;
	}
	
	/**
	 * 文字列内の '' を ' に戻します
	 * @param str '' を含む文字列
	 * @return 置換された文字列
	 */
	public static String unescapeSimple(String str) {
		return str.replace("\'\'", "\'");
	}
	
	/**
	 * "～", '～', {"～"}, {'～'}, `～` で囲まれた文字列を unquote します。
	 * @param text
	 * @return
	 */
	public static String unquote(String text) {
		switch (text.charAt(0)) {
			case '\'':
				return unescapeSimple(text.substring(1, text.length() - 1));
			case '`':
			case '\"':
				return unescape(text.substring(1, text.length() - 1));
			case '{':
				switch (text.charAt(1)) {
					case '\"':
						text = text.substring(2, text.length() - 2); // {" と "} を削除
						text = trimBlockString.matcher(text).replaceAll("");
						text = unescapeBlockString.matcher(text).replaceAll("");
						return text;
				}
				break;
		}
		return ParserHelper.unescape(text);
	}
}
