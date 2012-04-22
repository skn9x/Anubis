package anubis.runtime;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.script.Bindings;
import javax.script.ScriptContext;
import anubis.AnubisObject;
import anubis.SpecialSlot;
import anubis.code.CodeBlock;
import anubis.runtime.java.BindingsSlotTable;
import anubis.runtime.java.JFieldSlotTable;
import anubis.runtime.java.ScriptContextSlotTable;
import anubis.runtime.util.Cache;

/**
 * @author SiroKuro
 */
public class StandardObjectFactory implements ObjectFactory {
	private final Cache<Class<?>, JClass> cacheJClass = new Cache<Class<?>, JClass>();
	private final Cache<Object, JObject> cacheJObject = new Cache<Object, JObject>();
	private final Cache<Number, ANumber> cacheNumber = new Cache<Number, ANumber>();
	private final Cache<Bindings, AObject> cacheBindings = new Cache<Bindings, AObject>();
	private final Cache<ScriptContext, AObject> cacheScriptContext = new Cache<ScriptContext, AObject>();
	
	private final TraitsFactory traits = new StandardTraitsFactory();
	private final ATrueObject TRUE = traits.attach(new ATrueObject());
	private final AFalseObject FALSE = traits.attach(new AFalseObject());
	private final ANullObject NULL = traits.attach(new ANullObject());
	
	private final Initializer<Number, ANumber> INITIALIZER_ANUMBER = new Initializer<Number, ANumber>() {
		@Override
		public ANumber initialize(Number value) {
			if (value instanceof BigInteger)
				return traits.attach(AInteger.valueOf((BigInteger) value));
			if (value instanceof BigDecimal)
				return traits.attach(ADecimal.valueOf((BigDecimal) value));
			throw new IllegalArgumentException();
		}
	};
	private final Initializer<Object, JObject> INITIALIZER_JOBJECT = new Initializer<Object, JObject>() {
		@Override
		public JObject initialize(Object obj) {
			return newJObject(obj);
		}
	};
	private final Initializer<Class<?>, JClass> INITIALIZER_JCLASS = new Initializer<Class<?>, JClass>() {
		@Override
		public JClass initialize(Class<?> cls) {
			return newJClass(cls);
		}
	};
	private final Initializer<Bindings, AObject> INITIALIZER_BINDINGS = new Initializer<Bindings, AObject>() {
		@Override
		public AObject initialize(Bindings bindings) {
			return traits.attach(new AObject(new BindingsSlotTable(bindings)));
		}
	};
	private final Initializer<ScriptContext, AObject> INITIALIZER_SCRIPTCONTEXT = new Initializer<ScriptContext, AObject>() {
		@Override
		public AObject initialize(ScriptContext context) {
			return traits.attach(new ANamedObject(ObjectType.LOBBY, new ScriptContextSlotTable(context)));
		}
	};
	
	@Override
	public APrimitive getFalse() {
		return FALSE;
	}
	
	@Override
	public JClass getJClass(Class<?> cls) {
		return getObject(cacheJClass, cls, INITIALIZER_JCLASS);
	}
	
	@Override
	public JObject getJObject(Object object) {
		return getObject(cacheJObject, object, INITIALIZER_JOBJECT);
	}
	
	@Override
	public APrimitive getNull() {
		return NULL;
	}
	
	@Override
	public ANumber getNumber(Number value) {
		return getObject(cacheNumber, Utils.asBigNumber(value), INITIALIZER_ANUMBER);
	}
	
	@Override
	public AnubisObject getObject(Object obj) {
		if (obj == null) {
			return getNull();
		}
		if (obj instanceof AnubisObject) {
			return (AnubisObject) obj;
		}
		if (obj instanceof Boolean) {
			return (Boolean) obj ? getTrue() : getFalse();
		}
		if (obj instanceof Number) {
			return getNumber((Number) obj);
		}
		if (obj instanceof String) {
			return getString((String) obj);
		}
		if (obj instanceof ScriptContext) {
			return getObject(cacheScriptContext, (ScriptContext) obj, INITIALIZER_SCRIPTCONTEXT);
		}
		if (obj instanceof Bindings) { // TODO 後で Map と統合する
			return getObject(cacheBindings, (Bindings) obj, INITIALIZER_BINDINGS);
		}
		if (obj instanceof Class<?>) {
			return getJClass((Class<?>) obj);
		}
		// TODO 他にも追加
		return getJObject(obj);
	}
	
	@Override
	public AString getString(String value) {
		return traits.attach(AString.valueOf(value));
	}
	
	@Override
	public TraitsFactory getTraitsFactory() {
		return traits;
	}
	
	@Override
	public APrimitive getTrue() {
		return TRUE;
	}
	
	@Override
	public AnubisObject newContext(AnubisObject _this, AnubisObject outer) {
		return new AContext(_this, outer);
	}
	
	@Override
	public AFunction newFunction(CodeBlock body, AnubisObject outer, String... args) {
		return traits.attach(new AUserFunction(body, outer, args));
	}
	
	public JClass newJClass(Class<?> cls) {
		JClass result = new JClass(cls);
		result.setSlot(SpecialSlot.SUPER, getJClass(cls.getSuperclass()));
		return result;
	}
	
	@Override
	public JObject newJObject(Object object) {
		Class<?> cls = object.getClass();
		JClass jcls = getJClass(cls);
		JObject result = new JObject(object, new JFieldSlotTable(object, jcls.getFields()));
		result.setSlot(SpecialSlot.SUPER, getJClass(cls));
		return result;
	}
	
	@Override
	public AList newList() {
		return traits.attach(new AList());
	}
	
	@Override
	public AMap newMap() {
		return traits.attach(new AMap());
	}
	
	@Override
	public AnubisObject newObject(AnubisObject outer) {
		AnubisObject result = traits.attach(new AObject());
		result.setSlot(SpecialSlot.OUTER, outer);
		return result;
	}
	
	@Override
	public ARange newRange(AnubisObject start, AnubisObject end, AnubisObject step) {
		return traits.attach(new ARange(Utils.cast(start, ANumber.class), Utils.cast(end, ANumber.class), Utils.cast(
				step, ANumber.class)));
	}
	
	@Override
	public ASet newSet() {
		return traits.attach(new ASet());
	}
	
	private static <T, R> R getObject(Cache<T, R> cache, T object, Initializer<T, R> init) {
		if (object == null) {
			return null;
		}
		else {
			synchronized (cache) {
				R result = cache.get(object);
				if (result == null) {
					result = init.initialize(object);
					cache.put(object, result);
				}
				return result;
			}
		}
	}
	
	private interface Initializer<T, R> {
		/**
		 * @param key
		 * @return
		 */
		R initialize(T key);
	}
}
