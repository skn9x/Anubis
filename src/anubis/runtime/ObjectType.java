package anubis.runtime;

import anubis.TypeName;

/**
 * @author SiroKuro
 */
public class ObjectType {
	public static final String OBJECT = "object";
	public static final String FUNCTION = "function";
	public static final String BOOL = "bool";
	public static final String NULL = "null";
	public static final String NUMBER = "number";
	public static final String STRING = "string";
	public static final String REGEX = "regex";
	public static final String JPACKAGE = "jpackage";
	public static final String JCLASS = "jclass";
	public static final String JFUNCTION = "jfunction";
	public static final String LIST = "list";
	public static final String MAP = "map";
	public static final String SET = "set";
	public static final String CONTEXT = "context";
	public static final String LOBBY = "lobby";
	public static final String NOP = "nop";
	
	public static String get(Class<?> cls) {
		TypeName type = cls.getAnnotation(TypeName.class);
		if (type != null) {
			String result = type.value();
			if (result != null) {
				return result;
			}
		}
		return cls.getSimpleName();
	}
	
	public static String get(Object obj) {
		if (obj == null) {
			return OBJECT;
		}
		else {
			return get(obj.getClass());
		}
	}
}
