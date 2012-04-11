package anubis.runtime;

import anubis.ACastable;

/**
 * @author SiroKuro
 */
public class AString extends APrimitive implements ACastable {
	private final String value;
	
	public AString(String value) {
		if (value == null)
			value = "";
		this.value = value;
	}
	
	@Override
	public Object asJava() {
		return value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AString other = (AString) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		}
		else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	@Override
	public String getType() {
		return ObjectType.STRING;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return getValue();
	}
	
	/**
	 * @param name
	 * @return
	 */
	public static AString valueOf(String value) {
		return new AString(value);
	}
	
}
