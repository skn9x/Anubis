package anubis.console;

import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import anubis.AnubisEngineFactory;

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
			else if (cmdopt.hasOption("l")) {
				String code = cmdopt.getOptionValue("l");
				Line main = new Line(factory, code == null ? "" : code);
				main.exec();
			}
			else {
				Repl main = new Repl(factory, cmdopt.hasOption("nologo"), cmdopt.hasOption("noprompt"));
				main.exec();
			}
		}
		catch (ParseException e) {
			System.out.println(e.toString());
			showHelp(options);
		}
	}
	
	private static Option newOption(String longName, String shortName, String args, ResourceBundle bundle) {
		if (longName != null) {
			OptionBuilder.withLongOpt(longName);
			OptionBuilder.withDescription(bundle.getString(longName));
		}
		if (args != null) {
			OptionBuilder.hasArg();
			OptionBuilder.withArgName(args);
		}
		if (shortName != null) {
			return OptionBuilder.create(shortName);
		}
		else {
			return OptionBuilder.create();
		}
	}
	
	private static Options newOptions() {
		String baseName = Main.class.getPackage().getName() + "." + "options";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, Locale.getDefault());
		
		Options result = new Options();
		result.addOption(newOption("help", "h", null, bundle));
		result.addOption(newOption("compile", "c", null, bundle));
		result.addOption(newOption("dir", "d", "directory", bundle));
		result.addOption(newOption("encode", "e", "charset", bundle));
		result.addOption(newOption("file", "f", "sourcefile", bundle));
		result.addOption(newOption("line", "l", "code", bundle));
		result.addOption(newOption("nologo", null, null, bundle));
		result.addOption(newOption("noprompt", null, null, bundle));
		result.addOption(newOption("noassert", "na", null, bundle));
		return result;
	}
	
	private static void showHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("anubis", options, true);
	}
}
