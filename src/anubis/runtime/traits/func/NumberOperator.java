package anubis.runtime.traits.func;

import anubis.AnubisObject;

public class NumberOperator {
	public static AnubisObject add(AnubisObject x, AnubisObject y) {
		return ANumberAdd.INSTANCE.call(x, y);
	}
	
	public static AnubisObject div(AnubisObject x, AnubisObject y) {
		return ANumberDivide.INSTANCE.call(x, y);
	}
	
	public static AnubisObject lessThan(AnubisObject x, AnubisObject y) {
		return ANumberLessThan.INSTANCE.call(x, y);
	}
	
	public static AnubisObject minus(AnubisObject x) {
		return ANumberIsMinus.INSTANCE.call(x);
	}
	
	public static AnubisObject multiply(AnubisObject x, AnubisObject y) {
		return ANumberMultiply.INSTANCE.call(x, y);
	}
	
	public static AnubisObject negative(AnubisObject x) {
		return ANumberNegative.INSTANCE.call(x);
	}
	
	public static AnubisObject subtract(AnubisObject x, AnubisObject y) {
		return ANumberSubtract.INSTANCE.call(x, y);
	}
}
