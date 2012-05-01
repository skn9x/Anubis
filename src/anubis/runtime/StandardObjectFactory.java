package anubis.runtime;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.script.ScriptContext;
import anubis.AnubisObject;
import anubis.SpecialSlot;
import anubis.code.CodeBlock;
import anubis.runtime.java.JFieldSlotTable;
import anubis.runtime.java.ScriptContextSlotTable;
import anubis.runtime.util.Cache;
import anubis.runtime.util.EqualsCache;
import anubis.runtime.util.IdentityCache;

/**
 * @author SiroKuro
 */
public class StandardObjectFactory implements ObjectFactory {
	private final Cache<Number, ANumber> numberCache = new EqualsCache<Number, ANumber>();
	private final Cache<ScriptContext, AObject> cacheScriptContext = new IdentityCache<ScriptContext, AObject>();
	private final Cache<Object, AnubisObject> immutableObjectCache = new EqualsCache<Object, AnubisObject>();
	private final Cache<Object, AnubisObject> mutableObjectCache = new IdentityCache<Object, AnubisObject>();
	
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
	private final Initializer<ScriptContext, AObject> INITIALIZER_SCRIPTCONTEXT = new Initializer<ScriptContext, AObject>() {
		@Override
		public AObject initialize(ScriptContext context) {
			return traits.attach(new ANamedObject(ObjectType.LOBBY, new ScriptContextSlotTable(context)));
		}
	};
	private final Initializer<Package, JPackage> INITIALIZER_JPACKAGE = new Initializer<Package, JPackage>() {
		@Override
		public JPackage initialize(Package pack) {
			return traits.attach(new JPackage(pack));
		}
	};
	private final Initializer<String, AString> INITIALIZER_ASTRING = new Initializer<String, AString>() {
		@Override
		public AString initialize(String value) {
			return traits.attach(AString.valueOf(value));
		}
	};
	
	@Override
	public APrimitive getFalse() {
		return FALSE;
	}
	
	public JClass getJClass(Class<?> cls) {
		if (cls == null)
			return null;
		return getObject(immutableObjectCache, cls, INITIALIZER_JCLASS);
	}
	
	@Override
	public APrimitive getNull() {
		return NULL;
	}
	
	@Override
	public ANumber getNumber(Number value) {
		return getObject(numberCache, Utils.asBigNumber(value), INITIALIZER_ANUMBER);
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
		if (obj instanceof Class<?>) {
			return getJClass((Class<?>) obj);
		}
		if (obj instanceof Package) {
			return getObject(immutableObjectCache, (Package) obj, INITIALIZER_JPACKAGE);
		}
		if (obj instanceof ScriptContext) {
			return getObject(cacheScriptContext, (ScriptContext) obj, INITIALIZER_SCRIPTCONTEXT);
		}
		// TODO 他にも追加
		return getObject(mutableObjectCache, obj, INITIALIZER_JOBJECT);
	}
	
	@Override
	public AString getString(String value) {
		return getObject(immutableObjectCache, value, INITIALIZER_ASTRING);
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
		JClass result = JClass.valueOf(cls);
		if (cls.getSuperclass() != null)
			result.setSlot(SpecialSlot.SUPER, getJClass(cls.getSuperclass()));
		else
			getTraitsFactory().attach(result);
		return result;
	}
	
	@Override
	public JObject newJObject(Object object) {
		JClass jcls = getJClass(object.getClass());
		JObject result = new JObject(object, new JFieldSlotTable(object, jcls.getInstanceFields()));
		result.setSlot(SpecialSlot.SUPER, jcls);
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
	
	private static <T, R> R getObject(Cache<? super T, ? super R> cache, T object, Initializer<T, R> init) {
		if (object == null) {
			return null;
		}
		else {
			synchronized (cache) {
				// TODO あとで何とかしよう
				@SuppressWarnings("unchecked")
				R result = (R) cache.get(object);
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
