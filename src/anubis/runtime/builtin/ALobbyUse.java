package anubis.runtime.builtin;

import anubis.AnubisObject;
import anubis.runtime.ABuiltinFunction._1;
import anubis.runtime.AObjects;
import anubis.runtime.AString;
import anubis.runtime.JPackage;
import anubis.runtime.Utils;

public class ALobbyUse extends _1 {
	
	public ALobbyUse(AnubisObject owner, String name) {
		super(owner, name);
	}
	
	@Override
	protected AnubisObject exec(AnubisObject this1, AnubisObject x) {
		AString name = Utils.cast(x, AString.class);
		return AObjects.attachTraits(new JPackage(name.getValue()));
	}
}
