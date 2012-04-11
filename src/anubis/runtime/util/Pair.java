package anubis.runtime.util;

public class Pair<E, F> {
	private final E value1;
	private final F value2;
	
	public Pair(E value1, F value2) {
		this.value1 = value1;
		this.value2 = value2;
	}
	
	public E getValue1() {
		return value1;
	}
	
	public F getValue2() {
		return value2;
	}
}
