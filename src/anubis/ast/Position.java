package anubis.ast;

public class Position {
	public static final Position UNKNOWN = new Position(-1, -1);
	
	public final String filename;
	public final int line;
	public final int column;
	
	public Position(int line, int column) {
		this("<unknown>", line, column);
	}
	
	public Position(String filename, int line, int column) {
		assert filename != null;
		this.filename = filename;
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
