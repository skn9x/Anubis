package anubis.runtime;

public abstract class ATrait extends ANamedObject { // TypeName は付けない。root もこれを使うから
	protected ATrait(String name) {
		super(name);
	}
	
	protected abstract void initSlots(TraitsFactory factory);
}
