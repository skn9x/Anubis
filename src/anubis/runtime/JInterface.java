package anubis.runtime;

import anubis.runtime.java.JFieldSlotTable;
import anubis.runtime.java.JUtils;

/**
 * @author SiroKuro
 */
public class JInterface extends AObject {
	private final Class<?> cls;
	
	public JInterface(Class<?> cls) {
		super(new JFieldSlotTable(JUtils.getDeclaredFields(cls)));
		assert cls != null && cls.isInterface();
		this.cls = cls;
	}
	
	@Override
	public String getType() {
		return ObjectType.JCLASS;
	}
	
	public Class<?> getValue() {
		return cls;
	}
}
