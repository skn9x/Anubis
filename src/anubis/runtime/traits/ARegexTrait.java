package anubis.runtime.traits;

import anubis.runtime.ATrait;
import anubis.runtime.TraitsFactory;

public class ARegexTrait extends ATrait {
	public ARegexTrait() {
		super("RegexTrait");
	}
	
	@Override
	protected void initSlots(TraitsFactory factory) {
		factory.attach(this);
	}
}
