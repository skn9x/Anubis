package anubis.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import anubis.AnubisEngine;
import anubis.AnubisEngineFactory;
import anubis.except.AnubisRuntimeException;
import anubis.except.AssertionException;

public class AnubisTestRunner extends Runner {
	private final Class<?> cls;
	private final Map<Description, String> filenames;
	private final Description desc;
	
	public AnubisTestRunner(Class<?> cls) {
		this.cls = cls;
		this.desc = Description.createSuiteDescription(cls.getName());
		{
			filenames = new HashMap<Description, String>();
			SuiteCodes code = cls.getAnnotation(SuiteCodes.class);
			if (code != null) {
				for (String filename: code.value()) {
					Description d = Description.createTestDescription(cls, filename);
					desc.addChild(d);
					filenames.put(d, filename);
				}
			}
		}
	}
	
	@Override
	public Description getDescription() {
		return desc;
	}
	
	@Override
	public void run(RunNotifier notifier) {
		for (Description d: desc.getChildren()) {
			notifier.fireTestStarted(d);
			try {
				run(filenames.get(d));
				notifier.fireTestFinished(d);
			}
			catch (AssertionError e) {
				notifier.fireTestFailure(new Failure(d, e));
			}
			catch (ScriptException e) {
				Throwable th = e.getCause();
				if (th instanceof AssertionException)
					notifier.fireTestFailure(new Failure(d, new AssertionError(th)));
				else if (th instanceof AnubisRuntimeException || th instanceof AssertionError)
					notifier.fireTestFailure(new Failure(d, th));
				else
					notifier.fireTestFailure(new Failure(d, e));
			}
			catch (Exception e) {
				notifier.fireTestFailure(new Failure(d, e));
			}
		}
	}
	
	private void run(Reader reader, String filename) throws ScriptException {
		AnubisEngineFactory factory = new AnubisEngineFactory();
		AnubisEngine engine = (AnubisEngine) factory.getScriptEngine();
		engine.put(ScriptEngine.FILENAME, filename);
		engine.eval(reader);
	}
	
	private void run(String filename) throws ScriptException, IOException {
		if (filename == null) {
			throw new FileNotFoundException();
		}
		InputStream in = cls.getResourceAsStream(filename);
		if (in == null) {
			throw new FileNotFoundException(filename);
		}
		Reader reader = new BufferedReader(new InputStreamReader(in));
		try {
			run(reader, filename);
		}
		finally {
			reader.close();
		}
	}
}
