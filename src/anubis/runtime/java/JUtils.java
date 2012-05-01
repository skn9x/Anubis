package anubis.runtime.java;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SiroKuro
 */
public class JUtils {
	public static Map<String, Field> getDeclaredStaticFields(Class<?> cls) {
		Map<String, Field> result = new HashMap<String, Field>();
		getDeclaredFields(result, cls, true);
		return result;
	}
	
	public static Map<String, Field> getInstanceFields(Class<?> cls) {
		Map<String, Field> result = new HashMap<String, Field>();
		getInstanceFields(result, cls);
		return result;
	}
	
	private static void getDeclaredFields(Map<String, Field> result, Class<?> cls, boolean isstatic) {
		assert cls != null;
		for (Field ff: cls.getDeclaredFields()) {
			ff.setAccessible(true);
			if (Modifier.isStatic(ff.getModifiers()) == isstatic) {
				result.put(ff.getName(), ff);
			}
		}
	}
	
	private static void getInstanceFields(Map<String, Field> result, Class<?> cls) {
		if (cls != null) {
			getDeclaredFields(result, cls, false);
			getInstanceFields(result, cls.getSuperclass());
		}
	}
}
