package anubis.runtime;

/**
 * @author SiroKuro
 */
public class ARegex extends APrimitive {
	@Override
	public String getType() {
		return ObjectType.REGEX;
	}
}
