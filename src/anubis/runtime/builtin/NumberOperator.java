package anubis.runtime.builtin;

import anubis.AnubisObject;

public class NumberOperator {
	public static AnubisObject add(AnubisObject x, AnubisObject y) {
		return ANumberAddFunction.INSTANCE.call(x, y);
	}
	
	public static AnubisObject div(AnubisObject x, AnubisObject y) {
		return ANumberDivideFunction.INSTANCE.call(x, y);
	}
	
	public static AnubisObject lessThan(AnubisObject x, AnubisObject y) {
		return ANumberLessThanFunction.INSTANCE.call(x, y);
	}
	
	public static AnubisObject minus(AnubisObject x) {
		return ANumberIsMinusFunction.INSTANCE.call(x);
	}
	
	public static AnubisObject multiply(AnubisObject x, AnubisObject y) {
		return ANumberMultiplyFunction.INSTANCE.call(x, y);
	}
	
	public static AnubisObject subtract(AnubisObject x, AnubisObject y) {
		return ANumberSubtractFunction.INSTANCE.call(x, y);
	}
}
