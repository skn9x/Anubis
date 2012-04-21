package anubis;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.SimpleBindings;
import anubis.parser.ParserHelper;

/**
 * Anubis の ScriptEngineFactory です。
 * @author SiroKuro
 */
public class AnubisEngineFactory implements ScriptEngineFactory {
	private Bindings global = new SimpleBindings();
	
	public AnubisEngineFactory() {
		global.put(ScriptEngine.ENGINE, getEngineName());
		global.put(ScriptEngine.ENGINE_VERSION, getEngineVersion());
		global.put(ScriptEngine.NAME, getEngineName());
		global.put(ScriptEngine.LANGUAGE, getLanguageName());
		global.put(ScriptEngine.LANGUAGE_VERSION, getLanguageVersion());
		global.put("THREADING", "MULTITHREADED");
	}
	
	@Override
	public String getEngineName() {
		return "anubis";
	}
	
	@Override
	public String getEngineVersion() {
		String version = getClass().getPackage().getImplementationVersion();
		return version != null ? version : "0.0";
	}
	
	@Override
	public List<String> getExtensions() {
		return constList("anubis", "an");
	}
	
	public Bindings getGlobalBindings() {
		return global;
	}
	
	@Override
	public String getLanguageName() {
		return "anubis";
	}
	
	@Override
	public String getLanguageVersion() {
		return getEngineVersion();
	}
	
	@Override
	public String getMethodCallSyntax(String obj, String name, String... args) {
		StringBuilder result = new StringBuilder();
		result.append(ParserHelper.quoteIdentifier(obj));
		result.append(".");
		result.append(ParserHelper.quoteIdentifier(name));
		result.append("(");
		for (int i = 0; i < args.length; i++) {
			if (i != 0)
				result.append(", ");
			result.append(ParserHelper.quoteIdentifier(args[i]));
		}
		result.append(")");
		return result.toString();
	}
	
	@Override
	public List<String> getMimeTypes() {
		return constList("application/x-anubis");
	}
	
	@Override
	public List<String> getNames() {
		return constList("anubis", "Anubis", "AnubisLanguage", "AnubisScript");
	}
	
	@Override
	public String getOutputStatement(String text) {
		return "console.puts(" + ParserHelper.quoteString(text) + ")";
	}
	
	@Override
	public Object getParameter(String key) {
		return global.get(key);
	}
	
	@Override
	public String getProgram(String... stats) {
		StringBuilder result = new StringBuilder();
		if (stats != null) {
			for (String stat: stats) {
				result.append(stat);
				result.append("; ");
			}
		}
		return result.toString();
	}
	
	@Override
	public ScriptEngine getScriptEngine() {
		return new AnubisEngine(this);
	}
	
	private static <T> List<T> constList(T... args) {
		return Collections.unmodifiableList(Arrays.asList(args));
	}
	
}
