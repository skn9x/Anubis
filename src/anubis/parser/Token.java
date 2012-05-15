package anubis.parser;

import anubis.ast.Position;

public class Token {
	public final int token;
	public final Position pos;
	public final StringBuilder text;
	
	public Token(int token, Position pos) {
		assert pos != null;
		this.token = token;
		this.pos = pos;
		this.text = new StringBuilder();
	}
	
	public Token(int token, String text, Position pos) {
		assert text != null;
		assert pos != null;
		this.token = token;
		this.text = new StringBuilder(text);
		this.pos = pos;
	}
	
	public void append(char c) {
		this.text.append(c);
	}
	
	public void append(String text) {
		this.text.append(text);
	}
	
	public Position getPos() {
		return pos;
	}
	
	public String getText() {
		return text.toString();
	}
	
	public int getToken() {
		return token;
	}
	
	@Override
	public String toString() {
		return ParserHelper.quoteString(getText()) + "(" + pos.toString() + ")";
	}
}
