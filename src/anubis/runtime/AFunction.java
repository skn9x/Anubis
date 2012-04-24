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
	public static ACallable partial(final ACallable func, final AnubisObject... pArgs) {
		return AObjects.getTraitsFactory().attach(new AFunction() {
			@Override
			public AnubisObject call(AnubisObject _this, AnubisObject... args) {
				AnubisObject[] newArgs = new AnubisObject[args.length + pArgs.length];
				System.arraycopy(pArgs, 0, newArgs, 0, pArgs.length);
				System.arraycopy(args, 0, newArgs, pArgs.length, args.length);
				return func.call(_this, newArgs);
			}
		});
	}
}
