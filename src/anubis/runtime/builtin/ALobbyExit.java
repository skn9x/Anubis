package anubis.runtime.builtin;

import anubis.AnubisObject;
import anubis.except.AnubisExitError;
import anubis.runtime.ABuiltinFunction._1;

public class ALobbyExit extends _1 {
	private final Callback callback;
	
	public ALobbyExit(AnubisObject owner, String name) {
		super(owner, name);
		this.callback = null;
	}
	
	public ALobbyExit(AnubisObject owner, String name, Callback callback) {
		super(owner, name);
		this.callback = callback;
	}
	
	public ALobbyExit(Callback callback) {
		this.callback = callback;
	}
	
	@Override
	protected AnubisObject exec(AnubisObject this1, AnubisObject x) {
		AnubisExitError exit = new AnubisExitError(x);
		if (callback != null) {
			callback.onExit(exit);
		}
		throw exit;
	}
	
	public interface Callback {
		public void onExit(AnubisExitError exit);
	}
}
