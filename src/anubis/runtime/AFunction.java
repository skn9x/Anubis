package anubis.runtime;

import anubis.ACallable;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public abstract class AFunction extends AObject implements ACallable {
	
	@Override
	public abstract AnubisObject call(AnubisObject _this, AnubisObject... args);
	
	@Override
	public String getType() {
		return ObjectType.FUNCTION;
	}
	
	/**
	 * @param val
	 * @param valueOf
	 * @return
	 */
	public static ACallable partialApply(AnubisObject val, AString... args) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @param val
	 * @param valueOf
	 * @return
	 */
	public static ACallable partialApply(AnubisObject val, String args) {
		// TODO Auto-generated method stub
		return null;
	}
}
