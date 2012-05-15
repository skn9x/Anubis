package anubis.parser;

public class ParseError {
	private final String message;
	private final Object value;
	private final String[] expected;
	
	public ParseError(String message, Object value, String[] expected) {
		this.message = message;
		this.value = value;
		this.expected = expected;
	}
	
	@Override
	public String toString() {
		String CRLF = System.getProperty("line.separator", "\r\n");
		StringBuilder sb = new StringBuilder();
		sb.append(message).append(CRLF);
		sb.append("    token: ").append(value).append(CRLF);
		if (expected != null && expected.length > 0) {
			sb.append("    expecting: ");
			for (String exp: expected) {
				sb.append(" ").append(exp);
			}
		}
		return sb.toString();
	}
	
}
