package anubis.runtime.builtin;

import anubis.AnubisObject;
import anubis.runtime.ABuiltinFunction._1;
import anubis.runtime.AObjects;
import anubis.runtime.AString;
import anubis.runtime.Utils;
import anubis.runtime.java.JCaster;

public class AStringAdd extends _1 {
	
	public AStringAdd(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	protected AnubisObject exec(AnubisObject this1, AnubisObject x) {
		AString text = Utils.cast(AString.class, this1);
		Object obj = JCaster.cast(x);
		if (obj == null) {
			obj = "";
		}
		return AObjects.getString(text.getValue() + obj);
	}
	
}
