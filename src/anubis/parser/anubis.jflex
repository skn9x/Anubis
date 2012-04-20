package anubis.parser;
import java.io.*;
import anubis.except.*;
import anubis.ast.Position;

@SuppressWarnings("unused")
%%

%class Scanner
%implements Parser.yyInput

//%debug
%unicode
%line
%column
%integer
%eofclose
%pack

%{
	private static final String lineSeparator = "\n";
	private int token;
	private Token value = null;
	
	public boolean advance() throws java.io.IOException {
		token = yylex();
		return token != YYEOF;
	}
	
	public int token() {
		return token;
	}
	
	public Token value() {
		return value;
	}
	
	private void errorInvalidChars(String text) {
		throw ExceptionProvider.newParseExceptionByInvalidChars(text, ParserHelper.getHexCode(text), yyline, yycolumn);
	}
	
	private void errorStringNotTerminated(String name) {
		throw ExceptionProvider.newParseExceptionByStringNotTerminated(name, yyline, yycolumn);
	}
	
	private int tk(int token) {
		value = new Token(token, yytext(), new Position(yyline, yycolumn));
		return token;
	}
	
	private void btk(int token) {
		value = new Token(token, new Position(yyline, yycolumn));
	}
%}

%eofval{
	return YYEOF;
%eofval}

%state BLOCK_COMMENT, LINE_COMMENT, LINE_STRING, BLOCK_STRING, SIMPLE_STRING, LINE_SYMBOL

DIGIT		= [0-9]
LETTER		= [A-Za-z$_]
NAME		= {LETTER}({LETTER}|{DIGIT})*

DEC			= {DIGIT}+
HEX			= (0x|0X)[0-9a-fA-F]+
BIN			= (0b|0B)[0-1]+
INTEGER		= [+-]?({DEC}|{HEX}|{BIN})
DECIMAL		= [+-]?({DEC}\.{DEC}|0\.{DEC}|{DEC}[eE]{DEC})

EOL			= \r\n|\n|\r
WSP			= [\ \t]

%%

<YYINITIAL>{
	"object"	{ return tk(Parser.OBJECT); }
	"record"	{ return tk(Parser.RECORD); }
	"function"	{ return tk(Parser.FUNCTION); }
	"end"		{ return tk(Parser.END); }
	"def"		{ return tk(Parser.DEF); }
	"if"		{ return tk(Parser.IF); }
	"then"		{ return tk(Parser.THEN); }
	"else"		{ return tk(Parser.ELSE); }
	"for"		{ return tk(Parser.FOR); }
	"while"		{ return tk(Parser.WHILE); }
	"do"		{ return tk(Parser.DO); }
	"try"		{ return tk(Parser.TRY); }
	"catch"		{ return tk(Parser.CATCH); }
	"finally"	{ return tk(Parser.FINALLY); }
	"switch"	{ return tk(Parser.SWITCH); }
	"case"		{ return tk(Parser.CASE); }
	"assert"	{ return tk(Parser.ASSERT); }
	"using"		{ return tk(Parser.USING); }
	"lock"		{ return tk(Parser.LOCK); }
	"break"		{ return tk(Parser.BREAK); }
	"continue"	{ return tk(Parser.CONTINUE); }
	"throw"		{ return tk(Parser.THROW); }
	"return"	{ return tk(Parser.RETURN); }
	"or"		{ return tk(Parser.OR); }
	"xor"		{ return tk(Parser.XOR); }
	"and"		{ return tk(Parser.AND); }
	"not"		{ return tk(Parser.NOT); }
	"istrue"	{ return tk(Parser.ISTRUE); }
	"isfalse"	{ return tk(Parser.ISFALSE); }
	"isnull"	{ return tk(Parser.ISNULL); }
	"this"		{ return tk(Parser.THIS); }
	"super"		{ return tk(Parser.SUPER); }
	"outer"		{ return tk(Parser.OUTER); }
	"local"		{ return tk(Parser.LOCAL); }
	"null"		{ return tk(Parser.NULL); }
	"void"		{ return tk(Parser.VOID); }
	"true"		{ return tk(Parser.TRUE); }
	"false"		{ return tk(Parser.FALSE); }
	"@"			{ return tk(Parser.ATMARK); }
	":="		{ return tk(Parser.NEWSLOT); }
	"="			{ return tk(Parser.ASSIGN); }
	"+="		{ return tk(Parser.ASSIGN_ADD); }
	"-="		{ return tk(Parser.ASSIGN_SUB); }
	"*="		{ return tk(Parser.ASSIGN_MUL); }
	"/="		{ return tk(Parser.ASSIGN_DIV); }
	"\\="		{ return tk(Parser.ASSIGN_TRUEDIV); }
	"%="		{ return tk(Parser.ASSIGN_MOD); }
	"?"			{ return tk(Parser.QUESTION); }
	":"			{ return tk(Parser.COLON); }
	"=="		{ return tk(Parser.EQ); }
	"!="		{ return tk(Parser.NEQ); }
	"<>"		{ return tk(Parser.NEQ); }
	"<"			{ return tk(Parser.LT); }
	"<="		{ return tk(Parser.LTEQ); }
	">"			{ return tk(Parser.GT); }
	">="		{ return tk(Parser.GTEQ); }
	"??"		{ return tk(Parser.IFNULL); }
	"+"			{ return tk(Parser.PLUS); }
	"-"			{ return tk(Parser.MINUS); }
	"*"			{ return tk(Parser.MUL); }
	"/"			{ return tk(Parser.DIV); }
	"%"			{ return tk(Parser.MOD); }
	"\\"		{ return tk(Parser.TRUEDIV); }
	"."			{ return tk(Parser.DOT); }
	"=~"		{ return tk(Parser.RFIND); }
	"=^"		{ return tk(Parser.RREPL); }
	"->"		{ return tk(Parser.RIGHT_ARROW); }
	"=>"		{ return tk(Parser.RIGHT_DARROW); }
	"("			{ return tk(Parser.LP); }
	")"			{ return tk(Parser.RP); }
	"{"			{ return tk(Parser.LB); }
	"}"			{ return tk(Parser.RB); }
	"["			{ return tk(Parser.LBT); }
	"]"			{ return tk(Parser.RBT); }
	"%["		{ return tk(Parser.MAP_LBT); }
	"$["		{ return tk(Parser.SET_LBT); }
	","			{ return tk(Parser.COMMA); }
	";"			{ return tk(Parser.SEMICOLON); }
	".."		{ return tk(Parser.DOT2); }
	"::"		{ return tk(Parser.COLON2); }

	{INTEGER}	{ return tk(Parser.INTEGER); }
	{DECIMAL}	{ return tk(Parser.DECIMAL); }
	
	{NAME}		{ return tk(Parser.IDENTIFIER); }
	
	"/*"		{ yybegin(BLOCK_COMMENT); }
	"#"			{ yybegin(LINE_COMMENT); }
	"//"		{ yybegin(LINE_COMMENT); }
	"\""		{ btk(Parser.STRING); yybegin(LINE_STRING); }
	"\"\"\""	{ btk(Parser.STRING); yybegin(BLOCK_STRING); }
	"'"			{ btk(Parser.STRING); yybegin(SIMPLE_STRING); }
	"`"			{ btk(Parser.IDENTIFIER); yybegin(LINE_SYMBOL); }
	
	{EOL}		{}
	{WSP}		{}
	.			{ errorInvalidChars(yytext()); }
}

<LINE_STRING>{
	"\""				{ yybegin(YYINITIAL); return value.getToken(); }
	[^\n\r\"\'`\\]+		{ value.append( yytext() ); }
	<<EOF>>				{ errorStringNotTerminated("string"); }
}
<LINE_SYMBOL>{
	"`"					{ yybegin(YYINITIAL); return value.getToken(); }
	[^\n\r\"\'`\\]+		{ value.append( yytext() ); }
	<<EOF>>				{ errorStringNotTerminated("symbol"); }
}
<LINE_STRING, LINE_SYMBOL>{
	"\\t"		{ value.append('\t'); }
	"\\n"		{ value.append('\n'); }
	"\\N"		{ value.append(System.getProperty("line.separator", "\n")); }
	"\\r"		{ value.append('\r'); }
	"\\R"		{ value.append(System.getProperty("line.separator", "\r")); }
	"\\b"		{ value.append('\b'); }
	"\\f"		{ value.append('\f'); }
	"\\\""		{ value.append('\"'); }
	"\\\'"		{ value.append('\''); }
	"\\`"		{ value.append('`'); }
	"\\\\"		{ value.append('\\'); }
}
<BLOCK_STRING>{
	"\"\"\""		{ yybegin(YYINITIAL); return value.getToken(); }
	"^{WSP}.*$"		{ value.append(1 <= yytext().length() ? yytext().substring(1) : yytext()); }
	{EOL}			{ value.append( lineSeparator ); }
	<<EOF>>			{ errorStringNotTerminated("string"); }
	.				{ errorInvalidChars(yytext()); }
}
<SIMPLE_STRING> {
	"\'"			{ yybegin(YYINITIAL); return value.getToken(); }
	"\'\'"			{ value.append( "\'" ); }
	[^\n\r\']+		{ value.append( yytext() ); }
	{EOL}			{ value.append( lineSeparator ); }
	<<EOF>>			{ errorStringNotTerminated("string"); }
}
<BLOCK_COMMENT>{
	"*/"				{ yybegin(YYINITIAL); }
	[^*\r\n]*			{}
	[^*\r\n]*\n			{}
	"*"+[^*/\r\n]*		{}
	"*"+[^*/\r\n]*\n	{}
	<<EOF>>				{ errorStringNotTerminated("comment"); }
	.					{}
}
<LINE_COMMENT>{
	{EOL}		{ yybegin(YYINITIAL); }
	[^\r\n]+	{}
}
