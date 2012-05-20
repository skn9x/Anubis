package anubis.runtime.traits.func;

import java.io.PrintWriter;
import javax.script.ScriptContext;
import anubis.AnubisObject;

public class ALobbyPrintError extends AbstractConsolePrintFunction {
	public ALobbyPrintError(AnubisObject owner, String name, boolean newline) {
		super(owner, name, newline);
	}
	
	@Override
	protected PrintWriter getPrintWriter(ScriptContext context) {
		return getStderr(context);
	}
}
