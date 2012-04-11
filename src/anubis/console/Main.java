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
		result.addOption(OptionBuilder.withLongOpt("help").withDescription("�R�}���h���C���I�v�V������\������").create("h"));
		result.addOption(OptionBuilder.withLongOpt("compile").withDescription("�R���p�C������").create("c"));
		result.addOption(OptionBuilder.withLongOpt("directory").hasArg().withArgName("directory").withDescription(
				"�������ꂽ�N���X�t�@�C���̏o�͐���w�肷��").create("d"));
		result.addOption(OptionBuilder.withLongOpt("file").hasArg().withArgName("sourcefile").withDescription(
				"�\�[�X�t�@�C�����w�肷��").create("f"));
		result.addOption(OptionBuilder.withLongOpt("line").hasArg().withArgName("code").withDescription(
				"�����Ɏw��̃R�[�h�����s����").create("l"));
		result.addOption(OptionBuilder.withLongOpt("nologo").withDescription("�Θb���[�h���Ƀ��S��\�����Ȃ�").create());
		result.addOption(OptionBuilder.withLongOpt("noprompt").withDescription("�Θb���[�h���Ƀv�����v�g��\�����Ȃ�").create());
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
					AnubisObject obj = engine.evalForRepl(code.toString());
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
