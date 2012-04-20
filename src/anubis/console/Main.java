package anubis.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.script.ScriptException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import anubis.AnubisEngine;
import anubis.AnubisEngineFactory;
import anubis.AnubisObject;
import anubis.runtime.Operator;

public class Main {
	private static final AnubisEngineFactory factory = new AnubisEngineFactory();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CommandLineParser parser = new PosixParser();
		Options options = newOptions();
		try {
			CommandLine cmdopt = parser.parse(options, args);
			if (cmdopt.hasOption("h")) {
				showHelp(options);
			}
			else if (cmdopt.hasOption("c")) {
				// TODO compile
			}
			else if (cmdopt.hasOption("f")) {
				// TODO interpreter
			}
			else {
				repl(cmdopt.hasOption("nologo"), cmdopt.hasOption("noprompt"));
			}
		}
		catch (ParseException e) {
			System.out.println(e.toString());
			showHelp(options);
		}
	}
	
	@SuppressWarnings("static-access")
	private static Options newOptions() { // TODO i18n
		Options result = new Options();
		result.addOption(OptionBuilder.withLongOpt("help").withDescription("コマンドラインオプションを表示する").create("h"));
		result.addOption(OptionBuilder.withLongOpt("compile").withDescription("コンパイルする").create("c"));
		result.addOption(OptionBuilder.withLongOpt("directory").hasArg().withArgName("directory").withDescription(
				"生成されたクラスファイルの出力先を指定する").create("d"));
		result.addOption(OptionBuilder.withLongOpt("file").hasArg().withArgName("sourcefile").withDescription(
				"ソースファイルを指定する").create("f"));
		result.addOption(OptionBuilder.withLongOpt("line").hasArg().withArgName("code").withDescription(
				"引数に指定のコードを実行する").create("l"));
		result.addOption(OptionBuilder.withDescription("対話モード時にロゴを表示しない").create("nologo"));
		result.addOption(OptionBuilder.withDescription("対話モード時にプロンプトを表示しない").create("noprompt"));
		result.addOption(OptionBuilder.withDescription("assert を有効にする").create("ea"));
		return result;
	}
	
	private static void repl(boolean nologo, boolean noprompt) {
		AnubisEngine engine = (AnubisEngine) factory.getScriptEngine();
		if (!nologo) {
			System.out.printf("%s(%s) on java(%s), %s", factory.getEngineName(), factory.getEngineVersion(),
					System.getProperty("java.version", "unknown version"),
					System.getProperty("os.name", "[unknown os]"));
			System.out.println();
			System.out.println("if you want to exit, please type 'exit()' or CTRL+Z.");
			System.out.println();
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			final boolean[] onexit = new boolean[1];
			MAINLOOP: while (!onexit[0]) {
				StringBuilder code = new StringBuilder();
				if (!noprompt) {
					System.out.print("an> ");
				}
				while (!onexit[0]) {
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
					AnubisObject obj = engine.evalForRepl(code.toString(), "stdin");
					if (obj != null) {
						System.out.println("--> " + Operator.toString(obj));
					}
					System.out.println();
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
	
	private static void showHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("anubis", options, true);
	}
}
