package anubis.parser;

public interface yyDebug {
	
	void accept(Object yyVal);
	
	void discard(int yyState, int yyToken, String yyName, Object token);
	
	void error(String message);
	
	void lex(int yyState, int yyToken, String yyName, Object value);
	
	void pop(int yyState);
	
	void push(int yyState, Object yyVal);
	
	void reduce(int yyState, int topState, int yyN, String rule, short length);
	
	void reject();
	
	void shift(int yyState, int yyfinal);
	
	void shift(int yyState, short nextState, int yyErrorFlag);
	
}
