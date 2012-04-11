package anubis;

/**
 * @author SiroKuro
 */
public interface AIndexable extends AnubisObject {
	public AnubisObject getItem(AnubisObject index);
	
	public AnubisObject setItem(AnubisObject index, AnubisObject value);
}
