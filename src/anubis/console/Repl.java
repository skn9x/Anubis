package anubis.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import anubis.AnubisEngine;
import anubis.AnubisObject;
import anubis.except.AnubisExitError;
import anubis.runtime.Operator;
import anubis.runtime.TraitsFactory;
import anubis.runtime.builtin.ALobbyExit;

public class Repl implements ALobbyExit.Callback {
	private final ScriptEngineFactory factory;
	private boolean running = true;
	
	public Repl(ScriptEngineFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public void onExit(AnubisExitError exit) {
		System.out.println("bye!");
		running = false;
	}
	
	public void repl(boolean nologo, boolean noprompt) {
		AnubisEngine engine = (AnubisEngine) factory.getScriptEngine();
		engine.put(ScriptEngine.FILENAME, "stdin");
		if (!nologo) {
			System.out.printf("%s(%s) on java(%s), %s", factory.getEngineName(), factory.getEngineVersion(),
					System.getProperty("java.version", "unknown version"),
					System.getProperty("os.name", "[unknown os]"));
			System.out.println();
			System.out.println("if you want to exit, please type 'exit' or CTRL+Z.");
			System.out.println();
		}
		try {
			TraitsFactory tf = engine.getObjectFactory().getTraitsFactory();
			ALobbyExit exit = tf.attach(new ALobbyExit(this));
			engine.put("exit", engine.getObjectFactory().getTraitsFactory().attach(exit));
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			MAINLOOP: while (running) {
				StringBuilder code = new StringBuilder();
				if (!noprompt) {
					System.out.print("an> ");
				}
				while (running) {
					String line = reader.readLine();
					if (line == null)
						break MAINLOOP;
					if (line.length() == 0)
						break;
					code.append(line + "\n");
					if (!noprompt) {
						System.out.print("  > ");
					}
				}
				try {
					AnubisObject obj = engine.evalForRepl(code.toString());
					if (obj == exit) {
						System.out.println("bye!");
						running = false;
					}
					if (running) {
						if (obj != null) {
							System.out.println("--> " + Operator.toString(obj));
						}
						System.out.println();
					}
				}
				catch (ScriptException ex) {
					Throwable cause = ex.getCause();
					if (cause == null)
						cause = ex;
					cause.printStackTrace(System.out);
					System.out.println();
				}
				catch (Exception ex) {
					ex.printStackTrace(System.out);
					System.out.println();
				}
			}
		}
		catch (IOException ex) {
			;
		}
	}
}
