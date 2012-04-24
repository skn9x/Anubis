package anubis.runtime;

import java.util.Arrays;
import anubis.AnubisObject;
import anubis.parser.ParserHelper;

/**
 * @author SiroKuro
 */
public abstract class ABuiltinFunction extends AFunction {
	protected final String name;
	
	protected ABuiltinFunction() {
		this.name = String.format("function(%s)", getClass().getSimpleName());
	}
	
	protected ABuiltinFunction(AnubisObject owner, String name) {
		assert owner != null;
		assert name != null;
		register(owner, name);
		this.name = String.format("function(%s/%s)", Operator.toString(owner), ParserHelper.quoteIdentifier(name));
	}
	
	public ABuiltinFunction register(AnubisObject owner, String name) {
		owner.setSlot(name, this, true);
		return this;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public abstract static class _0 extends ABuiltinFunction {
		public _0() {
			;
		}
		
		public _0(AnubisObject owner, String name) {
			super(owner, name);
		}
		
		@Override
		public AnubisObject call(AnubisObject this1, AnubisObject... args) {
			return exec(this1);
		}
		
		protected abstract AnubisObject exec(AnubisObject this1);
	}
	
	public abstract static class _1 extends ABuiltinFunction {
		public _1() {
			;
		}
		
		public _1(AnubisObject owner, String name) {
			super(owner, name);
		}
		
		@Override
		public AnubisObject call(AnubisObject this1, AnubisObject... args) {
			if (args == null) {
				return exec(this1, null);
			}
			switch (args.length) {
				case 0:
					return exec(this1, null);
				default:
					return exec(this1, args[0]);
					
			}
		}
		
		protected abstract AnubisObject exec(AnubisObject this1, AnubisObject x);
	}
	
	public abstract static class _2 extends ABuiltinFunction {
		public _2() {
			;
		}
		
		public _2(AnubisObject owner, String name) {
			super(owner, name);
		}
		
		@Override
		public AnubisObject call(AnubisObject this1, AnubisObject... args) {
			if (args == null) {
				return exec(this1, null, null);
			}
			switch (args.length) {
				case 0:
					return exec(this1, null, null);
				case 1:
					return exec(this1, args[0], null);
				default:
					return exec(this1, args[0], args[1]);
			}
		}
		
		protected abstract AnubisObject exec(AnubisObject this1, AnubisObject x, AnubisObject y);
	}
	
	public abstract static class _3 extends ABuiltinFunction {
		public _3() {
			;
		}
		
		public _3(AnubisObject owner, String name) {
			super(owner, name);
		}
		
		@Override
		public AnubisObject call(AnubisObject this1, AnubisObject... args) {
			if (args == null) {
				return exec(this1, null, null, null);
			}
			switch (args.length) {
				case 0:
					return exec(this1, null, null, null);
				case 1:
					return exec(this1, args[0], null, null);
				case 2:
					return exec(this1, args[0], args[1], null);
				default:
					return exec(this1, args[0], args[1], args[2]);
			}
		}
		
		protected abstract AnubisObject exec(AnubisObject this1, AnubisObject x, AnubisObject y, AnubisObject z);
	}
	
	public abstract static class _4 extends ABuiltinFunction {
		public _4() {
			;
		}
		
		public _4(AnubisObject owner, String name) {
			super(owner, name);
		}
		
		@Override
		public AnubisObject call(AnubisObject this1, AnubisObject... args) {
			if (args == null) {
				return exec(this1, null, null, null, null);
			}
			switch (args.length) {
				case 0:
					return exec(this1, null, null, null, null);
				case 1:
					return exec(this1, args[0], null, null, null);
				case 2:
					return exec(this1, args[0], args[1], null, null);
				case 3:
					return exec(this1, args[0], args[1], args[2], null);
				default:
					return exec(this1, args[0], args[1], args[2], args[3]);
			}
		}
		
		protected abstract AnubisObject exec(AnubisObject this1, AnubisObject x, AnubisObject y, AnubisObject z, AnubisObject w);
	}
	
	public abstract static class _n extends ABuiltinFunction {
		private final int argc;
		
		public _n(AnubisObject owner, String name, int argc) {
			super(owner, name);
			this.argc = argc;
		}
		
		public _n(int argc) {
			this.argc = argc;
		}
		
		@Override
		public AnubisObject call(AnubisObject this1, AnubisObject... args) {
			if (args == null)
				return exec(this1, new AnubisObject[0]);
			return exec(this1, Arrays.copyOf(args, argc));
		}
		
		protected abstract AnubisObject exec(AnubisObject this1, AnubisObject[] args);
	}
}
