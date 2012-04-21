package anubis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	private static final Pattern patCn = Pattern.compile("^[A-Za-z_$][A-Za-z0-9_$]*");
	
	private Utils() {
		;
	}
	
	public static String asString(Object obj) {
		if (obj == null)
			return null;
		return obj.toString();
	}
	
	public static String getCnFromSn(String srcfilename) {
		if (srcfilename != null) {
			Matcher mm = Utils.patCn.matcher(srcfilename);
			if (mm.find()) {
				return mm.group();
			}
		}
		return null;
	}
}
