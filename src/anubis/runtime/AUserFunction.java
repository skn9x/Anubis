package anubis.runtime;

import anubis.AnubisObject;
import anubis.SpecialSlot;
import anubis.code.CodeBlock;

public class AUserFunction extends AFunction {
	private final CodeBlock body;
	private final String[] args;
	
	public AUserFunction(CodeBlock body, String... args) {
		assert body != null;
		assert args != null;
		this.body = body;
		this.args = args;
	}
	
	@Override
	public AnubisObject call(AnubisObject _this, AnubisObject... param) {
		AnubisObject local = AObjects.newContext(_this, getSlot(SpecialSlot.OUTER));
		for (int i = 0; i < args.length; i++) {
			if (i < param.length) {
				local.setSlot(args[i], param[i]);
			}
		}
		// TODO param 自体のセット
		return body.exec(local);
	}
}
