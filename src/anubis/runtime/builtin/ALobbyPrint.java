package anubis.runtime.builtin;

import java.io.PrintWriter;
import javax.script.ScriptContext;
import anubis.AnubisObject;

public class ALobbyPrint extends AbstractConsolePrintFunction {
	public ALobbyPrint(AnubisObject owner, String name, boolean newline) {
		super(owner, name, newline);
	}
	
	@Override
	protected PrintWriter getPrintWriter(ScriptContext context) {
		return getStdout(context);
	}
}
