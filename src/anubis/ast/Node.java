package anubis.ast;

/**
 * @author SiroKuro
 */
public abstract class Node {
	private Position pos = null;
	
	public Position getPos() {
		return pos;
	}
	
	public Node setPos(Position pos) {
		this.pos = pos;
		return this;
	}
}
