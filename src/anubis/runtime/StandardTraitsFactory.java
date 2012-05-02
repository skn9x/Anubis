package anubis.runtime;

import java.util.HashMap;
import java.util.Map;
import anubis.AnubisObject;
import anubis.SpecialSlot;
import anubis.runtime.builtin.AFunctionInvoke;
import anubis.runtime.builtin.AFunctionInvokeWith;
import anubis.runtime.builtin.AFunctionPartial;
import anubis.runtime.builtin.ALobbyExit;
import anubis.runtime.builtin.ALobbyPrint;
import anubis.runtime.builtin.ALobbyPrintError;
import anubis.runtime.builtin.ALobbyUse;
import anubis.runtime.builtin.ANumberAdd;
import anubis.runtime.builtin.ANumberDivide;
import anubis.runtime.builtin.ANumberGreaterThan;
import anubis.runtime.builtin.ANumberGreaterThanEquals;
import anubis.runtime.builtin.ANumberLessThan;
import anubis.runtime.builtin.ANumberLessThanEquals;
import anubis.runtime.builtin.ANumberMultiply;
import anubis.runtime.builtin.ANumberNegative;
import anubis.runtime.builtin.ANumberPositive;
import anubis.runtime.builtin.ANumberPower;
import anubis.runtime.builtin.ANumberRemainder;
import anubis.runtime.builtin.ANumberSubtract;
import anubis.runtime.builtin.ANumberTrueDivide;
import anubis.runtime.builtin.ARootDebugString;
import anubis.runtime.builtin.ARootDumpString;
import anubis.runtime.builtin.ARootEquals;
import anubis.runtime.builtin.ARootNewSlot;
import anubis.runtime.builtin.ARootNotEquals;
import anubis.runtime.builtin.ARootSetSlot;
import anubis.runtime.builtin.ARootSetSpecialSlot;
import anubis.runtime.builtin.ARootToString;
import anubis.runtime.builtin.AStringAdd;

/**
 * @author SiroKuro
 */
public class StandardTraitsFactory implements TraitsFactory {
	/**
	 * Root Traits
	 */
	private final AnubisObject root;
	/**
	 * Other Traits
	 */
	private final Map<String, AnubisObject> traits;
	
	public StandardTraitsFactory() {
		this.root = newRoot();
		this.traits = newTraits();
		initRoot(this.root);
		for (Map.Entry<String, Initializer> inits: setupInitializers().entrySet()) {
			AnubisObject trait = traits.get(inits.getKey());
			if (trait != null) {
				inits.getValue().init(trait);
			}
		}
	}
	
	public <T extends AnubisObject> T attach(T object) {
		if (object != null) {
			String name = object.getType();
			AnubisObject trait = traits.get(name);
			object.setSlot(SpecialSlot.SUPER, trait == null ? getRoot() : trait);
		}
		return object;
	}
	
	@Override
	public AnubisObject getRoot() {
		return root;
	}
	
	@Override
	public AnubisObject getTraits(String name) {
		return traits.get(name);
	}
	
	protected <T extends AnubisObject> T attachRoot(T object) {
		if (object != null) {
			object.setSlot(SpecialSlot.SUPER, getRoot());
		}
		return object;
	}
	
	protected void initRoot(AnubisObject root) {
		attach(new ARootEquals(root, "=="));
		attach(new ARootNotEquals(root, "!="));
		attach(new ARootNewSlot(root, "newSlot"));
		attach(new ARootSetSlot(root, "setSlot"));
		attach(new ARootSetSpecialSlot(root, "setSuper", SpecialSlot.SUPER));
		attach(new ARootSetSpecialSlot(root, "setOuter", SpecialSlot.OUTER));
		attach(new ARootToString(root, "toString"));
		attach(new ARootDebugString(root, "debugString"));
		attach(new ARootDumpString(root, "dumpString"));
	}
	
	protected AnubisObject newRoot() {
		return new ANamedObject("Root");
	}
	
	protected Map<String, AnubisObject> newTraits() {
		Map<String, AnubisObject> traits = new HashMap<String, AnubisObject>();
		traits.put(ObjectType.FUNCTION, attachRoot(new ANamedObject("FunctionTraits")));
		traits.put(ObjectType.NUMBER, attachRoot(new ANamedObject("NumberTraits")));
		traits.put(ObjectType.REGEX, attachRoot(new ANamedObject("RegexTraits")));
		traits.put(ObjectType.STRING, attachRoot(new ANamedObject("StringTraits")));
		traits.put(ObjectType.LOBBY, attachRoot(new ANamedObject("LobbyTraits")));
		return traits;
	}
	
	private Map<String, Initializer> setupInitializers() {
		Map<String, Initializer> result = new HashMap<String, StandardTraitsFactory.Initializer>();
		result.put(ObjectType.FUNCTION, new Initializer() {
			@Override
			public void init(AnubisObject trait) {
				attach(new AFunctionInvoke(trait, "invoke"));
				attach(new AFunctionInvokeWith(trait, "invokeWith"));
				attach(new AFunctionPartial(trait, "partial"));
			}
		});
		result.put(ObjectType.NUMBER, new Initializer() {
			@Override
			public void init(AnubisObject trait) {
				attach(new ANumberPositive(trait, "+p"));
				attach(new ANumberNegative(trait, "-n"));
				attach(new ANumberAdd(trait, "+"));
				attach(new ANumberSubtract(trait, "-"));
				attach(new ANumberMultiply(trait, "*"));
				attach(new ANumberDivide(trait, "/"));
				attach(new ANumberTrueDivide(trait, "\\"));
				attach(new ANumberRemainder(trait, "%"));
				attach(new ANumberPower(trait, "**"));
				attach(new ANumberGreaterThan(trait, ">"));
				attach(new ANumberGreaterThanEquals(trait, ">="));
				attach(new ANumberLessThan(trait, "<"));
				attach(new ANumberLessThanEquals(trait, "<="));
			}
		});
		result.put(ObjectType.REGEX, new Initializer() {
			@Override
			public void init(AnubisObject trait) {
			}
		});
		result.put(ObjectType.STRING, new Initializer() {
			@Override
			public void init(AnubisObject trait) {
				attach(new AStringAdd(trait, "+"));
			}
		});
		result.put(ObjectType.LOBBY, new Initializer() {
			@Override
			public void init(AnubisObject trait) {
				AObject console = attach(new ANamedObject("Console"));
				trait.setSlot("console", console);
				{
					attach(new ALobbyPrint(console, "print", false));
					attach(new ALobbyPrintError(console, "printerr", false));
					attach(new ALobbyPrint(console, "println", true));
					attach(new ALobbyPrintError(console, "printlnerr", true));
					attach(new ALobbyPrint(console, "puts", true));
				}
				attach(new ALobbyExit(trait, "exit"));
				attach(new ALobbyUse(trait, "use"));
				ANop nop = attach(new ANop());
				trait.setSlot("nop", nop);
			}
		});
		return result;
	}
	
	protected interface Initializer {
		public void init(AnubisObject trait);
	}
}
