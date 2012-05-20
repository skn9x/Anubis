package anubis.runtime.traits.func;

import anubis.AnubisObject;

public class Utils {

	public static AnubisObject[] cdr(AnubisObject[] args) {
		if (args.length == 0) {
			return new AnubisObject[0];
		}
		else {
			AnubisObject[] result = new AnubisObject[args.length - 1];
			System.arraycopy(args, 1, result, 0, result.length);
			return result;
		}
	}

	public static AnubisObject car(AnubisObject[] args) {
		if (args.length == 0) {
			return null;
		}
		else {
			return args[0];
		}
	}
	
}
