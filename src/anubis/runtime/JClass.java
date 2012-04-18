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
import anubis.runtime.java.JUtils;
import anubis.runtime.java.MethodInvocation;

/**
 * @author SiroKuro
 */
public class JClass extends AObject {
	private final Class<?> cls;
	private final Map<String, Field> fields;
	
	public JClass(Class<?> cls) {
		assert cls != null;
		this.cls = cls;
		this.fields = Collections.unmodifiableMap(JUtils.getFields(cls));
		initMethods(cls);
	}
	
	public Map<String, Field> getFields() {
		return fields;
	}
	
	@Override
	public String getType() {
		return ObjectType.JCLASS;
	}
	
	public Class<?> getValue() {
		return cls;
	}
	
	protected void initMethods(Class<?> cls) {
		Map<String, FunctionAccessor> accessors = new HashMap<String, FunctionAccessor>();
		// コンストラクタ作成
		if (!Modifier.isAbstract(cls.getModifiers())) {
			for (Constructor<?> cc: cls.getDeclaredConstructors()) {
				if (!Modifier.isPrivate(cc.getModifiers())) {
					FunctionAccessor acc = accessors.get("new");
					if (acc == null) {
						acc = new FunctionAccessor();
						accessors.put("new", acc);
					}
					acc.add(new ConstructorInvocation(cc));
				}
			}
		}
		// メソッド作成
		for (Method mm: cls.getDeclaredMethods()) {
			if (!Modifier.isPrivate(mm.getModifiers())) {
				FunctionAccessor acc = accessors.get(mm.getName());
				if (acc == null) {
					acc = new FunctionAccessor();
					accessors.put(mm.getName(), acc);
				}
				acc.add(new MethodInvocation(mm));
			}
		}
		// traits へ変換
		for (Entry<String, FunctionAccessor> ent: accessors.entrySet()) {
			this.setSlot(ent.getKey(), JFunction.valueOf(ent.getValue()), true);
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "(" + cls.getName() + ")";
	}
}
