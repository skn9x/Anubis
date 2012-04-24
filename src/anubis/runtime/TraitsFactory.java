package anubis.runtime;

import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public interface TraitsFactory {
	
	public <T extends AnubisObject> T attach(T object);
	
	/**
	 * @return the root
	 */
	public AnubisObject getRoot();

	public abstract AnubisObject getTraits(String name);
	
}
