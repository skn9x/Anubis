package anubis.runtime;

import anubis.AnubisObject;
import anubis.code.CodeBlock;

public class AUserFunction extends AFunction {
	private final CodeBlock body;
	private final AnubisObject outer;
	private final String[] args;
	
	public AUserFunction(CodeBlock body, AnubisObject outer, String... args) {
		assert body != null;
		assert outer != null;
		assert args != null;
		this.body = body;
		this.outer = outer;
		this.args = args;
	}
	
	@Override
	public AnubisObject call(AnubisObject _this, AnubisObject... param) {
		AnubisObject local = AObjects.newContext(_this, outer);
		for (int i = 0; i < args.length; i++) {
			if (i < param.length) {
				local.setSlot(args[i], param[i]);
			}
		}
		return body.exec(local);
	}
}
