package anubis.runtime.builtin;

import java.io.PrintWriter;
import java.io.Writer;
import java.text.MessageFormat;
import javax.script.ScriptContext;
import anubis.AnubisObject;
import anubis.EngineCore;
import anubis.runtime.ABuiltinFunction;
import anubis.runtime.AObjects;
import anubis.runtime.Operator;

public abstract class AbstractConsolePrintFunction extends ABuiltinFunction {
	private final boolean newline;
	
	public AbstractConsolePrintFunction(AnubisObject owner, String name, boolean newline) {
		super(owner, name);
		this.newline = newline;
	}
	
	@Override
	public AnubisObject call(AnubisObject _this, AnubisObject... args) {
		PrintWriter writer = getPrintWriter(EngineCore.getCurrentContext());
		String msg = message(args);
		writer.print(msg);
		if (isNewLine())
			writer.println();
		writer.flush();
		return AObjects.getString(msg);
	}
	
	protected abstract PrintWriter getPrintWriter(ScriptContext context);
	
	protected boolean isNewLine() {
		return newline;
	}
	
	protected String message(AnubisObject... args) {
		if (args == null || args.length == 0) {
			return "";
		}
		if (args.length == 1) {
			return Operator.toString(args[0]);
		}
		{
			Object[] objs = new Object[args.length - 1];
			for (int i = 0; i < objs.length; i++) {
				objs[i] = Operator.toString(args[i + 1]);
			}
			return MessageFormat.format(Operator.toString(args[0]), objs);
		}
	}
	
	protected static PrintWriter getStderr(ScriptContext context) {
		if (context != null) {
			Writer out = context.getErrorWriter();
			if (out != null) {
				return new PrintWriter(out);
			}
		}
		return new PrintWriter(System.err);
	}
	
	protected static PrintWriter getStdout(ScriptContext context) {
		if (context != null) {
			Writer out = context.getWriter();
			if (out != null) {
				return new PrintWriter(out);
			}
		}
		return new PrintWriter(System.out);
	}
}
