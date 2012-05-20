package anubis.console;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import anubis.AnubisEngine;

public class Line {
	private final ScriptEngineFactory factory;
	private final String code;
	
	public Line(ScriptEngineFactory factory, String code) {
		this.factory = factory;
		this.code = code;
	}
	
	public void exec() {
		AnubisEngine engine = initEngine();
		try {
			engine.exec(code);
		}
		catch (ScriptException ex) {
			Throwable cause = ex.getCause();
			if (cause == null)
				cause = ex;
			cause.printStackTrace(System.err);
		}
	}
	
	private AnubisEngine initEngine() {
		AnubisEngine engine = (AnubisEngine) factory.getScriptEngine();
		engine.put(ScriptEngine.FILENAME, "cmdline");
		return engine;
	}
}
