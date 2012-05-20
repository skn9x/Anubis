package anubis.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import anubis.AnubisObject;
import anubis.SpecialSlot;
import anubis.runtime.traits.AFunctionTrait;
import anubis.runtime.traits.AListTrait;
import anubis.runtime.traits.AMapTrait;
import anubis.runtime.traits.ANumberTrait;
import anubis.runtime.traits.ARegexTrait;
import anubis.runtime.traits.ARoot;
import anubis.runtime.traits.ASetTrait;
import anubis.runtime.traits.AStringTrait;
import anubis.runtime.traits.func.ALobbyTrait;

/**
 * @author SiroKuro
 */
public class StandardTraitsFactory implements TraitsFactory {
	/**
	 * Root Traits
	 */
	private final ATrait root;
	/**
	 * Other Traits
	 */
	private final Map<String, ATrait> traits = new HashMap<String, ATrait>();
	/**
	 * 
	 */
	private final Map<Class<?>, AnubisObject> cache = new ConcurrentHashMap<Class<?>, AnubisObject>();
	
	public StandardTraitsFactory() {
		this.root = newRoot();
		setupTraits();
		
		initRoot(this.root);
		for (ATrait trait: traits.values()) {
			trait.initSlots(this);
		}
	}
	
	public <T extends AnubisObject> T attach(T object) {
		if (object != null) {
			AnubisObject trait = getTrait(object);
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
	
	protected void addTrait(String typename, ATrait trait) {
		traits.put(typename, trait);
	}
	
	protected void initRoot(ATrait root) {
		root.initSlots(this);
	}
	
	protected ATrait newRoot() {
		return new ARoot();
	}
	
	protected void setupTraits() {
		addTrait(ObjectType.FUNCTION, new AFunctionTrait());
		addTrait(ObjectType.NUMBER, new ANumberTrait());
		addTrait(ObjectType.REGEX, new ARegexTrait());
		addTrait(ObjectType.STRING, new AStringTrait());
		addTrait(ObjectType.LOBBY, new ALobbyTrait());
		addTrait(ObjectType.LIST, new AListTrait());
		addTrait(ObjectType.MAP, new AMapTrait());
		addTrait(ObjectType.SET, new ASetTrait());
	}
	
	private AnubisObject getTrait(AnubisObject object) {
		Class<?> cls = object.getClass();
		AnubisObject trait = cache.get(cls);
		if (trait == null) {
			trait = traits.get(ObjectType.get(cls));
			if (trait != null) {
				cache.put(cls, trait);
			}
		}
		return trait;
	}
}
