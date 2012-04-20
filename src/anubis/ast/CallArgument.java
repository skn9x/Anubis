package anubis.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author SiroKuro
 */
public class CallArgument extends Node {
	private final Expression _this;
	private final List<Expression> params;
	
	public CallArgument(Expression _this, Expression... params) {
		this._this = _this;
		this.params = Collections.unmodifiableList(Arrays.asList(params.clone()));
	}
	
	public CallArgument(Expression _this, List<Expression> params) {
		this._this = _this;
		this.params = Collections.unmodifiableList(new ArrayList<Expression>(params));
	}
	
	/**
	 * @return the args
	 */
	public List<Expression> getParams() {
		return params;
	}
	
	/**
	 * @return the _this
	 */
	public Expression getThis() {
		return _this;
	}
	
}
