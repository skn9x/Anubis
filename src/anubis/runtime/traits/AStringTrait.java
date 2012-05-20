package anubis.runtime.traits;

import anubis.runtime.ATrait;
import anubis.runtime.TraitsFactory;
import anubis.runtime.traits.func.AStringAdd;

public class AStringTrait extends ATrait {
	public AStringTrait() {
		super("StringTrait");
	}
	
	@Override
	protected void initSlots(TraitsFactory factory) {
		factory.attach(this);
		factory.attach(new AStringAdd(this, "+"));
	}
}
