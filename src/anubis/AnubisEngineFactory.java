package anubis;

import java.util.Arrays;
import java.util.List;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

/**
 * @author SiroKuro
 */
public class AnubisEngineFactory implements ScriptEngineFactory {
	
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
		return Arrays.asList(new String[]{
			"anubis", "an"
		});
	}
	
	public Bindings getGlobalBindings() {
		// TODO Auto-generated method stub
		return null;
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
	public String getMethodCallSyntax(String arg0, String arg1, String... arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<String> getMimeTypes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<String> getNames() {
		return Arrays.asList("anubis", "Anubis", "AnubisLanguage", "AnubisScript");
	}
	
	@Override
	public String getOutputStatement(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object getParameter(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getProgram(String... arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ScriptEngine getScriptEngine() {
		return new AnubisEngine(this);
	}
	
}
