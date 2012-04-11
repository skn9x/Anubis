package anubis.runtime;

import anubis.AnubisObject;
import anubis.code.CodeBlock;

/**
 * @author SiroKuro
 */
public interface ObjectFactory {
	/**
	 * false オブジェクトを返します
	 * @return the false
	 */
	public abstract APrimitive getFalse();
	
	/**
	 * Class に対応する JClass オブジェクトを返します。
	 * @param cls クラスオブジェクト
	 * @return JClass オブジェクト / 引数が null の場合は null
	 */
	public abstract JClass getJClass(Class<?> cls);
	
	/**
	 * Object に対応する JObject オブジェクトを返します。
	 * @param object オブジェクト
	 * @return JObject オブジェクト / 引数が null の場合は null
	 */
	public abstract JObject getJObject(Object object);
	
	/**
	 * null オブジェクトを返します。
	 * @return the null
	 */
	public abstract APrimitive getNull();
	
	/**
	 * Number に対応する ANumber オブジェクトを返します。
	 * @param value Number オブジェクト
	 * @return ANumber オブジェクト
	 */
	public abstract ANumber getNumber(Number value);
	
	/**
	 * オブジェクトに対応する AnubisObject を返します。
	 * @param obj オブジェクト
	 * @return AnubisObject インスタンス
	 */
	public abstract AnubisObject getObject(Object obj);
	
	/**
	 * String に対応する AString オブジェクトを返します。
	 * @param value String オブジェクト
	 * @return AString オブジェクト
	 */
	public abstract AString getString(String value);
	
	/**
	 * 現在のスレッドに関連付けられた {@link TraitsFactory} を返します。
	 * @return TraitsFactory オブジェクト
	 */
	public abstract TraitsFactory getTraitsFactory();
	
	/**
	 * true オブジェクトを返します。
	 * @return the true
	 */
	public abstract APrimitive getTrue();
	
	/**
	 * Context オブジェクトを作成します。
	 * @param outer 外側のコンテキストオブジェクト
	 * @return Lobby オブジェクト
	 */
	public abstract AnubisObject newContext(AnubisObject _this, AnubisObject outer);
	
	public abstract AFunction newFunction(CodeBlock body, AnubisObject outer, String... args);
	
	/**
	 * Object に対応する JObject オブジェクトを作成します。
	 * @param object オブジェクト
	 * @return JObject オブジェクト
	 */
	public abstract JObject newJObject(Object object);
	
	public abstract AList newList();
	
	public abstract AMap newMap();
	
	public abstract AnubisObject newObject(AnubisObject outer);
	
	public abstract ARange newRange(AnubisObject start, AnubisObject end, AnubisObject step);
	
	public abstract ASet newSet();
}
