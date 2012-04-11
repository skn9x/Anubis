package anubis.parser;
import java.io.*;
import anubis.except.*;

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
	private String value;
	private final StringBuilder string = new StringBuilder();
	
	public boolean advance() throws java.io.IOException {
		token = yylex();
		return token != YYEOF;
	}
	
	public int token() {
		return token;
	}
	
	public String value() {
		return value;
	}
	
	private void errorInvalidChars(String text) {
		throw ExceptionProvider.newParseExceptionByInvalidChars(text, ParserHelper.getHexCode(text), yyline, yycolumn);
	}
	
	private void errorStringNotTerminated(String name) {
		throw ExceptionProvider.newParseExceptionByStringNotTerminated(name, yyline, yycolumn);
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
	"object"	{ return Parser.OBJECT; }
	"record"	{ return Parser.RECORD; }
	"function"	{ return Parser.FUNCTION; }
	"end"		{ return Parser.END; }
	"def"		{ return Parser.DEF; }
	"if"		{ return Parser.IF; }
	"then"		{ return Parser.THEN; }
	"else"		{ return Parser.ELSE; }
	"for"		{ return Parser.FOR; }
	"while"		{ return Parser.WHILE; }
	"do"		{ return Parser.DO; }
	"try"		{ return Parser.TRY; }
	"catch"		{ return Parser.CATCH; }
	"finally"	{ return Parser.FINALLY; }
	"switch"	{ return Parser.SWITCH; }
	"case"		{ return Parser.CASE; }
	"assert"	{ return Parser.ASSERT; }
	"break"		{ return Parser.BREAK; }
	"continue"	{ return Parser.CONTINUE; }
	"throw"		{ return Parser.THROW; }
	"return"	{ return Parser.RETURN; }
	"@"			{ return Parser.ATMARK; }
	":="		{ return Parser.NEWSLOT; }
	"="			{ return Parser.ASSIGN; }
	"+="		{ return Parser.ASSIGN_ADD; }
	"-="		{ return Parser.ASSIGN_SUB; }
	"*="		{ return Parser.ASSIGN_MUL; }
	"/="		{ return Parser.ASSIGN_DIV; }
	"\\="		{ return Parser.ASSIGN_TRUEDIV; }
	"%="		{ return Parser.ASSIGN_MOD; }
	"?"			{ return Parser.QUESTION; }
	":"			{ return Parser.COLON; }
	"or"		{ return Parser.OR; }
	"xor"		{ return Parser.XOR; }
	"and"		{ return Parser.AND; }
	"=="		{ return Parser.EQ; }
	"!="		{ return Parser.NEQ; }
	"<>"		{ return Parser.NEQ; }
	"<"			{ return Parser.LT; }
	"<="		{ return Parser.LTEQ; }
	">"			{ return Parser.GT; }
	">="		{ return Parser.GTEQ; }
	"??"		{ return Parser.IFNULL; }
	"+"			{ return Parser.PLUS; }
	"-"			{ return Parser.MINUS; }
	"*"			{ return Parser.MUL; }
	"/"			{ return Parser.DIV; }
	"%"			{ return Parser.MOD; }
	"\\"		{ return Parser.TRUEDIV; }
	"not"		{ return Parser.NOT; }
	"isnull"	{ return Parser.ISNULL; }
	"."			{ return Parser.DOT; }
	"=~"		{ return Parser.RFIND; }
	"=^"		{ return Parser.RREPL; }
	"->"		{ return Parser.RIGHT_ARROW; }
	"=>"		{ return Parser.RIGHT_DARROW; }
	"("			{ return Parser.LP; }
	")"			{ return Parser.RP; }
	"{"			{ return Parser.LB; }
	"}"			{ return Parser.RB; }
	"["			{ return Parser.LBT; }
	"]"			{ return Parser.RBT; }
	"%["		{ return Parser.MAP_LBT; }
	"$["		{ return Parser.SET_LBT; }
	","			{ return Parser.COMMA; }
	";"			{ return Parser.SEMICOLON; }
	".."		{ return Parser.DOT2; }
	"::"		{ return Parser.COLON2; }
	"this"		{ return Parser.THIS; }
	"super"		{ return Parser.SUPER; }
	"outer"		{ return Parser.OUTER; }
	"local"		{ return Parser.LOCAL; }
	"null"		{ return Parser.NULL; }
	"void"		{ return Parser.VOID; }
	"true"		{ return Parser.TRUE; }
	"false"		{ return Parser.FALSE; }
	
	{INTEGER}	{ value = yytext(); return Parser.INTEGER; }
	{DECIMAL}	{ value = yytext(); return Parser.DECIMAL; }
	
	{NAME}		{ value = yytext(); return Parser.IDENTIFIER; }
	
	"/*"		{ yybegin(BLOCK_COMMENT); }
	"#"			{ yybegin(LINE_COMMENT); }
	"//"		{ yybegin(LINE_COMMENT); }
	"\""		{ string.setLength(0); yybegin(LINE_STRING); }
	"\"\"\""	{ string.setLength(0); yybegin(BLOCK_STRING); }
	"'"			{ string.setLength(0); yybegin(SIMPLE_STRING); }
	"`"			{ string.setLength(0); yybegin(LINE_SYMBOL); }
	
	{EOL}		{}
	{WSP}		{}
	.			{ errorInvalidChars(yytext()); }
}

<LINE_STRING>{
	"\""				{ value = string.toString(); yybegin(YYINITIAL); return Parser.STRING; }
	[^\n\r\"\'`\\]+		{ string.append( yytext() ); }
	<<EOF>>				{ errorStringNotTerminated("string"); }
}
<LINE_SYMBOL>{
	"`"					{ value = string.toString(); yybegin(YYINITIAL); return Parser.IDENTIFIER; }
	[^\n\r\"\'`\\]+		{ string.append( yytext() ); }
	<<EOF>>				{ errorStringNotTerminated("symbol"); }
}
<LINE_STRING, LINE_SYMBOL>{
	"\\t"		{ string.append('\t'); }
	"\\n"		{ string.append('\n'); }
	"\\r"		{ string.append('\r'); }
	"\\\""		{ string.append('\"'); }
	"\\\'"		{ string.append('\''); }
	"\\`"		{ string.append('`'); }
	"\\\\"		{ string.append('\\'); }
}
<BLOCK_STRING>{
	"\"\"\""		{ value = string.toString(); yybegin(YYINITIAL); return Parser.STRING; }
	"^{WSP}.*$"		{ string.append(1 <= yytext().length() ? yytext().substring(1) : yytext()); }
	{EOL}			{ string.append( lineSeparator ); }
	<<EOF>>			{ errorStringNotTerminated("string"); }
	.				{ errorInvalidChars(yytext()); }
}
<SIMPLE_STRING> {
	"\'"			{ value = string.toString(); yybegin(YYINITIAL); return Parser.STRING; }
	"\'\'"			{ string.append( "\'" ); }
	[^\n\r\']+		{ string.append( yytext() ); }
	{EOL}			{ string.append( lineSeparator ); }
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
