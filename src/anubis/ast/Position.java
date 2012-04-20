package anubis.ast;

public class Position {
	public final String filename;
	public final int line;
	public final int column;
	
	public Position(int line, int column) {
		this(null, line, column);
	}
	
	public Position(String filename, int line, int column) {
		this.filename = filename == null ? "[unknown]" : filename;
		this.line = line;
		this.column = column;
	}
	
	public int getColumn() {
		return column;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public int getLine() {
		return line;
	}
}
