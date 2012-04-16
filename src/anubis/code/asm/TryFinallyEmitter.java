package anubis.code.asm;

public abstract class TryFinallyEmitter {
	private final String label;
	private final BlockEmitter block;
	
	public TryFinallyEmitter(BlockEmitter block, String label) {
		assert block != null;
		this.block = block;
		this.label = label;
	}
	
	public void emit(CodeBuilder builder) {
		Label _TRY = builder.newLabel();
		Label _TRY_END = builder.newLabel();
		Label _CATCH = builder.newLabel();
		Label _END = builder.newLabel();
		
		// try
		block.startTryFinallyBlock(label, _END, _END, this);
		try {
			builder.emitLabel(_TRY);
			emitTry(builder);
			builder.emitLabel(_TRY_END);
		}
		finally {
			block.endBlock();
		}
		
		// finally
		block.startPreventBlock();
		try {
			emitFinally(builder);
		}
		finally {
			block.endBlock();
		}
		builder.emitGoto(_END);
		
		// catch
		builder.emitLabel(_CATCH);
		block.startPreventBlock();
		try {
			emitFinally(builder);
		}
		finally {
			block.endBlock();
		}
		builder.emitThrow();
		
		// end
		builder.emitLabel(_END);
		builder.emitTryBlock(_TRY, _TRY_END, _CATCH, null);
	}
	
	public abstract void emitFinally(CodeBuilder builder);
	
	public abstract void emitTry(CodeBuilder builder);
}
