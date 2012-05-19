package anubis.runtime;

import java.util.Arrays;
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
		AnubisObject local = newContext(_this);
		for (int i = 0; i < args.length && i < param.length; i++) {
			local.setSlot(args[i], param[i]);
		}
		local.setSlot("args", AObjects.newList(Arrays.asList(param)));
		local.setSlot("callee", this);
		return body.exec(local);
	}
	
	private AnubisObject newContext(AnubisObject _this) {
		return AObjects.newContext(_this, getSlot(SpecialSlot.OUTER));
	}
}
