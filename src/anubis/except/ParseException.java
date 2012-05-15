package anubis.except;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import anubis.parser.ParseError;

/**
 * @author SiroKuro
 */
public class ParseException extends AnubisRuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6476766469011922021L;
	
	private List<ParseError> errors = Collections.emptyList();
	
	/**
	 * 
	 */
	protected ParseException() {
	}
	
	/**
	 * @param message
	 */
	protected ParseException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	protected ParseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param cause
	 */
	protected ParseException(Throwable cause) {
		super(cause);
	}
	
	public void setErrors(List<ParseError> errors) {
		this.errors = Collections.unmodifiableList(new ArrayList<ParseError>(errors));
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder(super.toString());
		for (ParseError err: errors) {
			result.append(System.getProperty("line.separator", "\r\n"));
			result.append(err);
		}
		return result.toString();
	}
}
