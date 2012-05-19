package anubis.runtime;

import java.util.List;
import java.util.regex.Pattern;
import anubis.AnubisObject;
import anubis.SpecialSlot;
import anubis.code.CodeBlock;
import anubis.except.ExceptionProvider;
import anubis.except.FactoryNotReadyException;

public class AObjects {
	/**
	 * カレント Factory
	 */
	private static final ThreadLocal<ObjectFactory> current = new ThreadLocal<ObjectFactory>();
	
	/**
	 * オブジェクトに Trait を紐付けます。
	 * @param object オブジェクト
	 * @return object そのもの
	 */
	public static <T extends AnubisObject> T attachTraits(T object) {
		getTraitsFactory().attach(object);
		return object;
	}
	
	/**
	 * 現在のスレッドに関連付けられた Factory を返します。
	 * @return Factory インスタンス
	 * @throws FactoryNotReadyException Factory が関連付けられていないとき
	 */
	public static ObjectFactory getCurrent() {
		ObjectFactory result = current.get();
		if (result != null)
			return result;
		else
			throw ExceptionProvider.newFactoryNotReady();
	}
	
	/**
	 * false オブジェクトを返します。
	 * @return false オブジェクト
	 */
	public static AFalseObject getFalse() {
		return getCurrent().getFalse();
	}
	
	/**
	 * null オブジェクトを返します。
	 * @return the null
	 */
	public static ANullObject getNull() {
		return getCurrent().getNull();
	}
	
	/**
	 * Number に対応する ANumber オブジェクトを返します。
	 * @param value Number オブジェクト
	 * @return ANumber オブジェクト
	 */
	public static ANumber getNumber(Number value) {
		return getCurrent().getNumber(value);
	}
	
	/**
	 * オブジェクトに対応する AnubisObject を返します。
	 * @param obj オブジェクト
	 * @return AnubisObject インスタンス
	 */
	public static AnubisObject getObject(Object obj) {
		return getCurrent().getObject(obj);
	}
	
	public static AnubisObject[] getObjects(Object... objs) {
		AnubisObject[] result = new AnubisObject[objs.length];
		ObjectFactory factory = getCurrent();
		for (int i = 0; i < result.length; i++) {
			result[i] = factory.getObject(objs[i]);
		}
		return result;
	}
	
	/**
	 * 正規表現オブジェクトに対応する ARegex オブジェクトを返します。
	 * @param pattern 正規表現オブジェクト
	 * @return ARegex オブジェクト
	 */
	public static ARegex getRegex(Pattern pattern) {
		return getCurrent().getRegex(pattern);
	}
	
	/**
	 * String に対応する AString オブジェクトを返します。
	 * @param value String オブジェクト
	 * @return AString オブジェクト
	 */
	public static AString getString(String value) {
		return getCurrent().getString(value);
	}
	
	/**
	 * 現在のスレッドに関連付けられた {@link TraitsFactory} を返します。
	 * @return TraitsFactory オブジェクト
	 */
	public static TraitsFactory getTraitsFactory() {
		return getCurrent().getTraitsFactory();
	}
	
	/**
	 * true オブジェクトを返します。
	 * @return the true
	 */
	public static ATrueObject getTrue() {
		return getCurrent().getTrue();
	}
	
	/**
	 * Context オブジェクトを作成します。
	 * @param outer 新しいコンテキストでの outer オブジェクト (null可)
	 * @return Context オブジェクト
	 */
	public static AnubisObject newContext(AnubisObject outer) {
		return getCurrent().newContext(outer == null ? null : outer.getSlot(SpecialSlot.THIS), outer);
	}
	
	/**
	 * Context オブジェクトを作成します。
	 * @param _this 新しいコンテキストでの this オブジェクト (null可)
	 * @param outer 新しいコンテキストでの outer オブジェクト (null可)
	 * @return Context オブジェクト
	 */
	public static AnubisObject newContext(AnubisObject _this, AnubisObject outer) {
		return getCurrent().newContext(_this, outer);
	}
	
	/**
	 * Function オブジェクトを作成します。
	 * @param body ユーザーコード
	 * @param outer 外側のコンテキストオブジェクト (null可)
	 * @param args 引数
	 * @return Function オブジェクト
	 */
	public static AFunction newFunction(CodeBlock body, AnubisObject outer, String... args) {
		return getCurrent().newFunction(body, outer, args);
	}
	
	/**
	 * Object に対応する JObject オブジェクトを作成します。
	 * @param object オブジェクト
	 * @return JObject オブジェクト
	 */
	public static JObject newJObject(Object object) {
		return getCurrent().newJObject(object);
	}
	
	/**
	 * 空の List オブジェクトを作成します。
	 * @return List オブジェクト
	 */
	public static AList newList() {
		return getCurrent().newList();
	}
	
	public static AList newList(List<AnubisObject> list) {
		return getCurrent().newList(list);
	}
	
	/**
	 * 空の Map オブジェクトを作成します。
	 * @return Map オブジェクト
	 */
	public static AMap newMap() {
		return getCurrent().newMap();
	}
	
	/**
	 * ユーザオブジェクトを作成します。
	 * @param outer 外側のコンテキストオブジェクト
	 * @return オブジェクト
	 */
	public static AnubisObject newObject(AnubisObject outer) {
		return getCurrent().newObject(outer);
	}
	
	/**
	 * Range オブジェクトを作成します。
	 * @param start 開始値
	 * @param end 終了値
	 * @param step ステップ値
	 * @return Range オブジェクト
	 */
	public static ARange newRange(AnubisObject start, AnubisObject end, AnubisObject step) {
		return getCurrent().newRange(start, end, step);
	}
	
	/**
	 * 空の Set オブジェクトを作成します。
	 * @return Set オブジェクト
	 */
	public static ASet newSet() {
		return getCurrent().newSet();
	}
	
	/**
	 * 現在のスレッドに Factory を関連付けます。
	 * @param factory 関連付ける Factory インスタンス / null の時は関連付けを削除
	 * @return 直前に関連付けられていた Factory インスタンス
	 */
	public static ObjectFactory setCurrent(ObjectFactory factory) {
		ObjectFactory result = current.get();
		if (factory != null)
			current.set(factory);
		else
			current.remove();
		return result;
	}
}
