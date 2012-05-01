package anubis.runtime;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import anubis.runtime.java.ConstructorInvocation;
import anubis.runtime.java.FunctionAccessor;
import anubis.runtime.java.JFieldSlotTable;
import anubis.runtime.java.JUtils;
import anubis.runtime.java.MethodInvocation;

/**
 * @author SiroKuro
 */
public class JClass extends AObject {
	private final Class<?> cls;
	private final Map<String, Field> fields;
	
	public JClass(Class<?> cls, SlotTable table) {
		super(table);
		assert cls != null;
		this.cls = cls;
		this.fields = Collections.unmodifiableMap(JUtils.getInstanceFields(cls));
		newClassTable(cls);
	}
	
	public Map<String, Field> getInstanceFields() {
		return fields;
	}
	
	@Override
	public String getType() {
		return ObjectType.JCLASS;
	}
	
	public Class<?> getValue() {
		return cls;
	}
	
	@Override
	public String toString() {
		return super.toString() + "(" + cls.getName() + ")";
	}
	
	public static JClass valueOf(Class<?> cls) {
		if (cls.isInterface())
			return new JClass(cls, newInterfaceTable(cls));
		else
			return new JClass(cls, newClassTable(cls));
	}
	
	protected static SlotTable newClassTable(Class<?> cls) {
		Map<String, FunctionAccessor> accessors = new HashMap<String, FunctionAccessor>();
		
		// コンストラクタ作成
		FunctionAccessor _new = newConstructorAccessor(cls);
		if (_new != null) {
			accessors.put("new", _new);
		}
		// メソッド作成
		accessors.putAll(newMethodAccessors(cls, accessors));
		
		// traits へ変換
		SlotTable result = new SimpleSlotTable();
		for (Entry<String, FunctionAccessor> ent: accessors.entrySet()) {
			result.put(ent.getKey(), AObjects.attachTraits(JFunction.valueOf(ent.getValue())), true);
		}
		return result;
	}
	
	protected static SlotTable newInterfaceTable(Class<?> cls) {
		return new JFieldSlotTable(JUtils.getDeclaredStaticFields(cls));
	}
	
	private static FunctionAccessor newConstructorAccessor(Class<?> cls) {
		if (cls.isInterface() || Modifier.isAbstract(cls.getModifiers())) {
			return null;
		}
		else {
			FunctionAccessor _new = new FunctionAccessor();
			for (Constructor<?> cc: cls.getDeclaredConstructors()) {
				if (!Modifier.isPrivate(cc.getModifiers())) {
					cc.setAccessible(true);
					_new.add(new ConstructorInvocation(cc));
				}
			}
			return _new;
		}
	}
	
	private static Map<String, FunctionAccessor> newMethodAccessors(Class<?> cls, Map<String, FunctionAccessor> accessors) {
		Map<String, FunctionAccessor> methods = new HashMap<String, FunctionAccessor>();
		for (Method mm: cls.getDeclaredMethods()) {
			if (!Modifier.isPrivate(mm.getModifiers()) && !Modifier.isAbstract(mm.getModifiers())) {
				mm.setAccessible(true);
				FunctionAccessor acc = accessors.get(mm.getName());
				if (acc == null) {
					acc = new FunctionAccessor();
					methods.put(mm.getName(), acc);
				}
				acc.add(new MethodInvocation(mm));
			}
		}
		return methods;
	}
}
