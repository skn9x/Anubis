package anubis.runtime;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.script.ScriptContext;
import anubis.AnubisObject;
import anubis.SpecialSlot;
import anubis.TypeName;
import anubis.code.CodeBlock;
import anubis.runtime.java.ArrayAdapter;
import anubis.runtime.java.JFieldSlotTable;
import anubis.runtime.java.ListAdapter;
import anubis.runtime.java.MapAdapter;
import anubis.runtime.java.ScriptContextSlotTable;
import anubis.runtime.java.SetAdapter;
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
	
	private final TraitsFactory traits = newTraitsFactory();
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
			JClass result = JClass.valueOf(cls);
			if (cls.getSuperclass() != null)
				result.setSlot(SpecialSlot.SUPER, getJClass(cls.getSuperclass()));
			else
				getTraitsFactory().attach(result);
			return result;
		}
	};
	private final Initializer<ScriptContext, AObject> INITIALIZER_SCRIPTCONTEXT = new Initializer<ScriptContext, AObject>() {
		@Override
		public AObject initialize(ScriptContext context) {
			return traits.attach(new ALobby(new ScriptContextSlotTable(context)));
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
	private final Initializer<Pattern, ARegex> INITIALIZER_REGEX = new Initializer<Pattern, ARegex>() {
		@Override
		public ARegex initialize(Pattern value) {
			return traits.attach(ARegex.valueOf(value));
		}
	};
	
	@Override
	public AFalseObject getFalse() {
		return FALSE;
	}
	
	@Override
	public ANullObject getNull() {
		return NULL;
	}
	
	@Override
	public ANumber getNumber(Number value) {
		return getObject(numberCache, Utils.asBigNumber(value), INITIALIZER_ANUMBER);
	}
	
	@SuppressWarnings("unchecked")
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
		if (obj instanceof Pattern) {
			return getRegex((Pattern) obj);
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
		if (obj.getClass().isArray()) {
			return traits.attach(new ArrayAdapter(obj).toAList());
		}
		if (obj instanceof List<?>) {
			return traits.attach(new ListAdapter<Object>((List<Object>) obj, Object.class).toAList());
		}
		if (obj instanceof Map<?, ?>) {
			return traits.attach(new MapAdapter<Object, Object>((Map<Object, Object>) obj, Object.class, Object.class).toAMap());
		}
		if (obj instanceof Set<?>) {
			return traits.attach(new SetAdapter<Object>((Set<Object>) obj, Object.class).toASet());
		}
		return getObject(mutableObjectCache, obj, INITIALIZER_JOBJECT);
	}
	
	@Override
	public ARegex getRegex(Pattern pattern) {
		return getObject(immutableObjectCache, pattern, INITIALIZER_REGEX);
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
	public ATrueObject getTrue() {
		return TRUE;
	}
	
	/**
	 * Context オブジェクトを作成します。
	 * @param _this 新しいコンテキストでの this オブジェクト (null可)
	 * @param outer 新しいコンテキストでの outer オブジェクト (null可)
	 * @return Context オブジェクト
	 */
	@Override
	public AnubisObject newContext(AnubisObject _this, AnubisObject outer) {
		return new AContext(_this, outer);
	}
	
	/**
	 * Function オブジェクトを作成します。
	 * @param body ユーザーコード
	 * @param outer 外側のコンテキストオブジェクト (null可)
	 * @param args 引数
	 * @return Function オブジェクト
	 */
	@Override
	public AFunction newFunction(CodeBlock body, AnubisObject outer, String... args) {
		AFunction result = traits.attach(new AUserFunction(body, args));
		result.setSlot(SpecialSlot.OUTER, outer);
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
	public AList newList(List<AnubisObject> list) {
		return traits.attach(new AList(list));
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
		return traits.attach(new ARange(Utils.cast(ANumber.class, start), Utils.as(ANumber.class, end), Utils.as(
				ANumber.class, step)));
	}
	
	@Override
	public ASet newSet() {
		return traits.attach(new ASet());
	}
	
	protected StandardTraitsFactory newTraitsFactory() {
		return new StandardTraitsFactory();
	}
	
	private JClass getJClass(Class<?> cls) {
		if (cls == null)
			return null;
		return getObject(immutableObjectCache, cls, INITIALIZER_JCLASS);
	}
	
	private static <T, R> R getObject(Cache<? super T, ? super R> cache, T object, Initializer<T, R> init) {
		if (object == null) {
			return null;
		}
		else {
			synchronized (cache) {
				@SuppressWarnings("unchecked")
				R result = (R) cache.get(object); // ここだけは警告を回避できない
				if (result == null) {
					result = init.initialize(object);
					cache.put(object, result);
				}
				return result;
			}
		}
	}
	
	@TypeName(ObjectType.LOBBY)
	private static class ALobby extends AObject {
		private ALobby(SlotTable slots) {
			super(slots);
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
