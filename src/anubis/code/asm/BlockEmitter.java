package anubis.code.asm;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class BlockEmitter {
	private final Deque<Block> blocks = new LinkedList<Block>();
	
	public void emitBreak(CodeBuilder builder, String name) throws IllegalGotoException {
		Block block = emitFinallyBlock(builder, name);
		builder.emitGoto(block.getBreak());
	}
	
	public void emitContinue(CodeBuilder builder, String name) throws IllegalGotoException {
		Block block = emitFinallyBlock(builder, name);
		builder.emitGoto(block.getContinue());
	}
	
	public void endBlock() {
		blocks.removeFirst();
	}
	
	public void startPreventBlock() {
		blocks.addFirst(null);
	}
	
	public void startStrongBlock(String name, Label _break, Label _continue) {
		pushBlock(name, true, _break, _continue, null);
	}
	
	public void startTryFinallyBlock(String name, Label _break, Label _continue, TryFinallyEmitter _finally) {
		pushBlock(name, blocks.size() == 0, _break, _continue, _finally); // 一番最初のブロックは必ず Strong にする
	}
	
	public void startWeakBlock(String name, Label _break, Label _continue) {
		pushBlock(name, blocks.size() == 0, _break, _continue, null); // 一番最初のブロックは必ず Strong にする
	}
	
	private Block emitFinallyBlock(CodeBuilder builder, String name) throws IllegalGotoException {
		for (Block f: new ArrayList<Block>(blocks)) { // finally を emit すると blocks が更新されてしまう。使いたいのは現時点での iterator
			if (f == null) {
				throw new IllegalGotoException("jumps cannot be in finally");
			}
			if (f.getFinally() != null) {
				f.getFinally().emitFinally(builder);
			}
			if (name == null ? f.isStrong() : name.equals(f.getName())) {
				return f;
			}
		}
		throw new IllegalGotoException("jump target is nothing");
	}
	
	private void pushBlock(Block block) {
		blocks.addFirst(block);
	}
	
	private void pushBlock(String name, boolean strong, Label _break, Label _continue, TryFinallyEmitter _finally) {
		pushBlock(new Block(name, strong, _break, _continue, _finally));
	}
	
	public static class IllegalGotoException extends Exception {
		private static final long serialVersionUID = 1L;
		
		public IllegalGotoException() {
			super();
		}
		
		public IllegalGotoException(String message) {
			super(message);
		}
		
		public IllegalGotoException(String message, Throwable cause) {
			super(message, cause);
		}
		
		public IllegalGotoException(Throwable cause) {
			super(cause);
		}
	}
	
	private static class Block {
		private final String name;
		private final boolean strong;
		private final Label _break, _continue;
		private final TryFinallyEmitter _finally;
		
		public Block(String name, boolean strong, Label _break, Label _continue, TryFinallyEmitter _finally) {
			this.name = name;
			this.strong = strong;
			this._break = _break;
			this._continue = _continue;
			this._finally = _finally;
		}
		
		public Label getBreak() {
			return _break;
		}
		
		public Label getContinue() {
			return _continue;
		}
		
		public TryFinallyEmitter getFinally() {
			return _finally;
		}
		
		public String getName() {
			return name;
		}
		
		public boolean isStrong() {
			return strong;
		}
	}
}
