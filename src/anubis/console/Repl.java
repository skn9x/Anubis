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
	private final boolean nologo, noprompt;
	private final ALobbyExit exit = new ALobbyExit(this);
	private boolean running = true;
	
	public Repl(ScriptEngineFactory factory, boolean nologo, boolean noprompt) {
		this.factory = factory;
		this.nologo = nologo;
		this.noprompt = noprompt;
	}
	
	@Override
	public void onExit(AnubisExitError exit) {
		System.out.println("bye!");
		running = false;
	}
	
	public void repl() {
		AnubisEngine engine = initEngine();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			printLogo();
			while (running) {
				try {
					String code = readLine(reader);
					if (code == null) {
						break;
					}
					AnubisObject obj = engine.evalForRepl(code);
					if (obj == exit) {
						System.out.println("bye!");
						break;
					}
					printResult(obj);
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
		finally {
			running = false;
		}
	}
	
	private AnubisEngine initEngine() {
		AnubisEngine engine = (AnubisEngine) factory.getScriptEngine();
		TraitsFactory tf = engine.getObjectFactory().getTraitsFactory();
		engine.put("exit", tf.attach(exit));
		engine.put(ScriptEngine.FILENAME, "stdin");
		return engine;
	}
	
	private void printLogo() {
		if (running && !nologo) {
			System.out.printf("%s(%s) on java(%s), %s", factory.getEngineName(), factory.getEngineVersion(),
					System.getProperty("java.version", "unknown version"),
					System.getProperty("os.name", "[unknown os]"));
			System.out.println();
			System.out.println("if you want to exit, please type 'exit' or CTRL+Z.");
			System.out.println();
		}
	}
	
	private void printPrompt(boolean cont) {
		if (running && !noprompt) {
			if (!cont)
				System.out.print("an> ");
			else
				System.out.print("  > ");
		}
	}
	
	private void printResult(AnubisObject obj) {
		if (running) {
			if (obj != null) {
				System.out.println("--> " + Operator.toString(obj));
			}
			System.out.println();
		}
	}
	
	private String readLine(BufferedReader reader) throws IOException {
		StringBuilder code = new StringBuilder();
		printPrompt(false);
		while (running) {
			String line = reader.readLine();
			if (line == null) // EOF
				return null;
			if (line.length() == 0)
				break;
			code.append(line + "\n");
			printPrompt(true);
		}
		return code.toString();
	}
}
