package anubis.runtime;

import anubis.AnubisObject;

public class ARecord extends AObject {
	
	public class ConstField extends Field {
		private final APrimitive value;
		
		public ConstField(APrimitive value) {
			this.value = value;
		}
		
		@Override
		public AnubisObject get() {
			return value;
		}
		
		@Override
		public void set(AnubisObject value) {
			; // nop
		}
		
		@Override
		public void set(AnubisObject value, boolean readonly) {
			; // nop
		}
	}
	
	public abstract class Field {
		public abstract AnubisObject get();
		
		public abstract void set(AnubisObject value);
		
		public abstract void set(AnubisObject value, boolean readonly);
	}
	
	public class NestedField extends Field {
		
		@Override
		public AnubisObject get() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void set(AnubisObject value) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void set(AnubisObject value, boolean readonly) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class VariableField extends Field {
		private final String name;
		
		public VariableField(String name) {
			assert name != null;
			this.name = name;
		}
		
		@Override
		public AnubisObject get() {
			return ARecord.this.getSlot(name);
		}
		
		@Override
		public void set(AnubisObject value) {
			ARecord.this.setSlot(name, value);
		}
		
		@Override
		public void set(AnubisObject value, boolean readonly) {
			ARecord.this.setSlot(name, value, readonly);
		}
	}
}
