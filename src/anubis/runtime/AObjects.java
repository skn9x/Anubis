package anubis.runtime;

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
	 * bool オブジェクトを返します。
	 * @param bool bool値
	 * @return true または false オブジェクト
	 */
	public static APrimitive getBool(boolean bool) {
		return bool ? getCurrent().getTrue() : getCurrent().getFalse();
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
	public static APrimitive getFalse() {
		return getCurrent().getFalse();
	}
	
	/**
	 * Class に対応する JClass オブジェクトを返します。
	 * @param cls クラスオブジェクト
	 * @return JClass オブジェクト / 引数が null の場合は null
	 */
	public static JClass getJClass(Class<?> cls) {
		return getCurrent().getJClass(cls);
	}
	
	/**
	 * Object に対応する JObject オブジェクトを返します。
	 * @param object オブジェクト
	 * @return JObject オブジェクト / 引数が null の場合は null
	 */
	public static JObject getJObject(Object object) {
		return getCurrent().getJObject(object);
	}
	
	/**
	 * null オブジェクトを返します。
	 * @return the null
	 */
	public static APrimitive getNull() {
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
	public static APrimitive getTrue() {
		return getCurrent().getTrue();
	}
	
	/**
	 * Context オブジェクトを作成します。
	 * @param outer 外側のコンテキストオブジェクト
	 * @return Context オブジェクト
	 */
	public static AnubisObject newContext(AnubisObject outer) {
		return getCurrent().newContext(outer.getSlot(SpecialSlot.THIS), outer);
	}
	
	/**
	 * Context オブジェクトを作成します。
	 * @param outer 外側のコンテキストオブジェクト
	 * @return Context オブジェクト
	 */
	public static AnubisObject newContext(AnubisObject _this, AnubisObject outer) {
		return getCurrent().newContext(_this, outer);
	}
	
	/**
	 * Function オブジェクトを作成します。
	 * @param body ユーザーコード
	 * @param outer 外側のコンテキストオブジェクト
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
