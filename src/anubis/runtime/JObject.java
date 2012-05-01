package anubis.runtime;

import anubis.ACastable;

/**
 * @author SiroKuro
 */
public class JObject extends AObject implements ACastable {
	private final Object object;
	
	public JObject(Object object, SlotTable slots) {
		super(slots);
		this.object = object;
	}
	
	@Override
	public Object asJava() {
		return object;
	}
	
	// TODO AObject 配下は全て valueOf でインスタンス生成するように変更 →　valueOf の箇所を全て attachTraits する
}
