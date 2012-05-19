package anubis.runtime;

import java.util.regex.Pattern;
import anubis.TypeName;

/**
 * @author SiroKuro
 */
@TypeName(ObjectType.REGEX)
public class ARegex extends APrimitive {
	private final Pattern pattern;
	
	protected ARegex(Pattern pattern) {
		this.pattern = pattern;
	}
	
	@Override
	public Object asJava() {
		return pattern;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ARegex other = (ARegex) obj;
		if (pattern == null) {
			if (other.pattern != null)
				return false;
		}
		else if (!pattern.pattern().equals(other.pattern.pattern()))
			return false;
		else if (pattern.flags() != other.pattern.flags())
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
		return result;
	}
	
	public static ARegex valueOf(Pattern pattern) {
		return new ARegex(pattern);
	}
}
