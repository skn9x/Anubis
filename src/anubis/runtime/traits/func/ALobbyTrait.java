package anubis.runtime.traits.func;

import anubis.runtime.ANop;
import anubis.runtime.AObject;
import anubis.runtime.ATrait;
import anubis.runtime.JPackage;
import anubis.runtime.TraitsFactory;

public class ALobbyTrait extends ATrait {
	public ALobbyTrait() {
		super("FunctionTrait");
	}
	
	@Override
	protected void initSlots(TraitsFactory factory) {
		factory.attach(this);
		AObject console = factory.attach(new AObject());
		this.setSlot("console", console);
		{
			factory.attach(new ALobbyPrint(console, "print", false));
			factory.attach(new ALobbyPrintError(console, "printerr", false));
			factory.attach(new ALobbyPrint(console, "println", true));
			factory.attach(new ALobbyPrintError(console, "printlnerr", true));
			factory.attach(new ALobbyPrint(console, "puts", true));
		}
		factory.attach(new ALobbyExit(this, "exit"));
		factory.attach(new ALobbyUse(this, "use"));
		this.setSlot("nop", factory.attach(new ANop()));
		this.setSlot("java", factory.attach(new JPackage("java")));
		this.setSlot("javax", factory.attach(new JPackage("javax")));
	}
}
