package anubis.runtime.java;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.TreeMap;
import anubis.AnubisObject;
import anubis.except.ExceptionProvider;
import anubis.runtime.AObjects;
import anubis.runtime.AbstractSlotTable;

/**
 * @author SiroKuro
 */
public class JFieldSlotTable extends AbstractSlotTable {
	private final Object object;
	private final Map<String, Field> fields;
	
	/**
	 * インタフェイス用コンストラクタ
	 * @param fields
	 */
	public JFieldSlotTable(Map<String, Field> fields) {
		assert fields != null;
		this.object = null;
		this.fields = fields;
	}
	
	/**
	 * オブジェクト用コンストラクタ
	 * @param object
	 * @param fields
	 */
	public JFieldSlotTable(Object object, Map<String, Field> fields) {
		assert object != null;
		assert fields != null;
		this.object = object;
		this.fields = fields;
	}
	
	@Override
	public AnubisObject get(String name) {
		Field ff = fields.get(name);
		if (ff != null) {
			try {
				return AObjects.getObject(ff.get(object));
			}
			catch (IllegalAccessException ex) {
				throw ExceptionProvider.wrapRuntimeException(ex);
			}
		}
		else {
			return null;
		}
	}
	
	@Override
	public Map<String, AnubisObject> getSnap() {
		Map<String, AnubisObject> result = new TreeMap<String, AnubisObject>();
		for (Field ff: fields.values()) {
			try {
				result.put(ff.getName(), AObjects.getObject(ff.get(object)));
			}
			catch (IllegalAccessException ex) {
				throw ExceptionProvider.wrapRuntimeException(ex);
			}
		}
		return result;
	}
	
	@Override
	public void put(String name, AnubisObject value, boolean setReadonly) {
		assertNotReadonly(name, setReadonly);
		Field ff = fields.get(name);
		if (ff != null && Modifier.isFinal(ff.getModifiers()) == false) {
			try {
				ff.set(object, JCaster.cast(ff.getType(), value));
			}
			catch (IllegalAccessException ex) {
				throw ExceptionProvider.wrapRuntimeException(ex);
			}
		}
		else {
			throw ExceptionProvider.newSlotReadonly(name);
		}
	}
}
