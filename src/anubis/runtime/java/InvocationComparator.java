package anubis.runtime.java;

import java.util.Comparator;
import java.util.List;

/**
 * @author SiroKuro
 */
public class InvocationComparator implements Comparator<Invocation> {
	public static final InvocationComparator INSTANCE = new InvocationComparator();
	
	@Override
	public int compare(Invocation x, Invocation y) {
		int result;
		result = compare(x.getArgLength(), y.getArgLength());
		if (result != 0) {
			return result;
		}
		result = x.getArgsScore().compareTo(y.getArgsScore());
		if (result != 0) {
			return result;
		}
		result = compare(x.getArgTypes(), y.getArgTypes());
		if (result != 0) {
			return result;
		}
		result = x.getClass().toString().compareTo(y.getClass().toString());
		if (result != 0) {
			return result;
		}
		return 0;
	}
	
	private static int compare(Class<?> cls1, Class<?> cls2) {
		// サブクラスのほうがより小さい
		if (cls2.isAssignableFrom(cls1))
			return -1;
		if (cls1.isAssignableFrom(cls2))
			return 1;
		return 0;
	}
	
	private static int compare(int x, int y) {
		// 小さい値のほうがより小さい
		return Integer.valueOf(x).compareTo(y);
	}
	
	private static int compare(List<Class<?>> cls1, List<Class<?>> cls2) {
		int max = Math.min(cls1.size(), cls2.size());
		for (int i = 0; i < max; i++) {
			int result = compare(cls1.get(i), cls2.get(i));
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}
	
}
