package anubis.runtime.java;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SiroKuro
 */
public class JUtils {
	public static Map<String, Field> getDeclaredFields(Class<?> cls) {
		Map<String, Field> result = new HashMap<String, Field>();
		for (Field ff: cls.getDeclaredFields()) {
			result.put(ff.getName(), ff);
		}
		return result;
	}
	
	public static Map<String, Field> getFields(Class<?> cls) {
		Map<String, Field> result = new HashMap<String, Field>();
		for (Field ff: cls.getFields()) {
			result.put(ff.getName(), ff);
		}
		return result;
	}
}
