package anubis.runtime;

import java.util.HashMap;
import java.util.Map;
import anubis.AnubisObject;
import anubis.SpecialSlot;
import anubis.runtime.builtin.ANumberAddFunction;
import anubis.runtime.builtin.ANumberDivideFunction;
import anubis.runtime.builtin.ANumberGreaterThanEqualsFunction;
import anubis.runtime.builtin.ANumberGreaterThanFunction;
import anubis.runtime.builtin.ANumberLessThanEqualsFunction;
import anubis.runtime.builtin.ANumberLessThanFunction;
import anubis.runtime.builtin.ANumberMultiplyFunction;
import anubis.runtime.builtin.ANumberNegativeFunction;
import anubis.runtime.builtin.ANumberPositiveFunction;
import anubis.runtime.builtin.ANumberPowerFunction;
import anubis.runtime.builtin.ANumberRemainderFunction;
import anubis.runtime.builtin.ANumberSubtractFunction;
import anubis.runtime.builtin.ANumberTrueDivideFunction;
import anubis.runtime.builtin.ARootEqualsFunction;
import anubis.runtime.builtin.ARootNewSlotFunction;
import anubis.runtime.builtin.ARootNotEqualsFunction;
import anubis.runtime.builtin.ARootSetSlotFunction;
import anubis.runtime.builtin.AStringAddFunction;

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
	private final Map<String, AnubisObject> traits = new HashMap<String, AnubisObject>();
	
	public StandardTraitsFactory() {
		this.root = initRoot();
		setup(traits);
	}
	
	public <T extends AnubisObject> T attach(T object) {
		if (object != null) {
			String name = object.getType();
			AnubisObject trait = traits.get(name);
			object.setSlot(SpecialSlot.SUPER, trait == null ? getRoot() : trait);
		}
		return object;
	}
	
	/**
	 * @return the root
	 */
	public AnubisObject getRoot() {
		return root;
	}
	
	protected <T extends AnubisObject> T attachRoot(T object) {
		if (object != null) {
			object.setSlot(SpecialSlot.SUPER, getRoot());
		}
		return object;
	}
	
	protected AnubisObject initRoot() {
		AnubisObject trait = new AObject();
		new ARootEqualsFunction(trait, "==");
		new ARootNotEqualsFunction(trait, "!=");
		new ARootNewSlotFunction(trait, "newSlot");
		new ARootSetSlotFunction(trait, "setSlot");
		return trait;
	}
	
	protected AnubisObject newLobby() {
		AnubisObject trait = attachRoot(new AObject());
		return trait;
	}
	
	protected AnubisObject newNumber() {
		AnubisObject trait = attachRoot(new AObject());
		new ANumberPositiveFunction(trait, "+p");
		new ANumberNegativeFunction(trait, "-n");
		new ANumberAddFunction(trait, "+");
		new ANumberSubtractFunction(trait, "-");
		new ANumberMultiplyFunction(trait, "*");
		new ANumberDivideFunction(trait, "/");
		new ANumberTrueDivideFunction(trait, "\\");
		new ANumberRemainderFunction(trait, "%");
		new ANumberPowerFunction(trait, "**");
		new ANumberGreaterThanFunction(trait, "<");
		new ANumberGreaterThanEqualsFunction(trait, "<=");
		new ANumberLessThanFunction(trait, ">");
		new ANumberLessThanEqualsFunction(trait, ">=");
		return trait;
	}
	
	protected AnubisObject newRegex() {
		AnubisObject trait = attachRoot(new AObject());
		return trait;
	}
	
	protected AnubisObject newString() {
		AnubisObject trait = attachRoot(new AObject());
		new AStringAddFunction(trait, "+");
		return trait;
	}
	
	protected void setup(Map<String, AnubisObject> traits) {
		traits.put(ObjectType.CONTEXT, newLobby());
		traits.put(ObjectType.NUMBER, newNumber());
		traits.put(ObjectType.REGEX, newRegex());
		traits.put(ObjectType.STRING, newString());
	}
}
