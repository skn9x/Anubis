package anubis.ast;

/**
 * @author SiroKuro
 */
public abstract class Node {
	private Position pos = null;
	
	public Position getPosition() {
		return pos;
	}
	
	public void setPosition(Position pos) {
		this.pos = pos;
	}
}
