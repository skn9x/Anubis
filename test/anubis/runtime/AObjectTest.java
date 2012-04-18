package anubis.runtime;

import static anubis.SpecialSlot.OUTER;
import static anubis.SpecialSlot.SUPER;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import anubis.AnubisObject;
import anubis.except.SlotReadonlyException;

/**
 * @author SiroKuro
 */
public class AObjectTest {
	private AObject obj = new AObject();
	private AnubisObject _super = new AObject();
	private AnubisObject _outer = new AObject();
	private AnubisObject obj2 = new AObject();
	private AnubisObject obj3 = new AObject();
	private AnubisObject obj4 = new AObject();
	private AnubisObject obj5 = new AObject();
	private AnubisObject obj6 = new AObject();
	private AnubisObject obj7 = new AObject();
	
	/**
	 * {@link anubis.runtime.AObject#findSlot(java.lang.String)} のためのテスト・メソッド。
	 */
	@Test
	public void testFindSlot() {
		// obj.super = _super;
		// obj.outer = _outer;
		// obj.a = _super;
		// _super.b = obj2;
		// _outer.b = obj3;
		// _outer.c = obj3;
		obj.setSlot(SUPER, _super);
		obj.setSlot(OUTER, _outer);
		obj.setSlot("a", _super);
		_super.setSlot("b", obj2);
		_outer.setSlot("b", obj3);
		_outer.setSlot("c", obj3);
		
		assertSame(_super, obj.findSlot("a"));
		assertSame(obj2, obj.findSlot("b"));
		assertSame(obj3, obj.findSlot("c"));
		assertNull(obj.findSlot("d"));
	}
	
	/**
	 * {@link anubis.runtime.AObject#findSlot(java.lang.String)} のためのテスト・メソッド。
	 */
	@Test
	public void testFindSlot02() {
		// obj.super = _super;
		// obj.outer = _outer;
		// _super.super = obj2;
		// _super.outer = obj3;
		// _outer.super = obj4;
		// _outer.outer = obj5;
		obj.setSlot(SUPER, _super);
		obj.setSlot(OUTER, _outer);
		_super.setSlot(SUPER, obj2);
		_super.setSlot(OUTER, obj3);
		_outer.setSlot(SUPER, obj4);
		_outer.setSlot(OUTER, obj5);
		
		obj2.setSlot("a", obj6);
		obj3.setSlot("a", obj7); // super.outer は絶対に取得されない
		obj4.setSlot("a", obj7);
		obj5.setSlot("a", obj7);
		obj2.setSlot("b", null);
		obj3.setSlot("b", obj7); // super.outer は絶対に取得されない
		obj4.setSlot("b", obj6);
		obj5.setSlot("b", obj7);
		obj2.setSlot("c", null);
		obj3.setSlot("c", obj7); // super.outer は絶対に取得されない
		obj4.setSlot("c", null);
		obj5.setSlot("c", obj6);
		obj2.setSlot("d", null);
		obj3.setSlot("d", obj7); // super.outer は絶対に取得されない
		obj4.setSlot("d", null);
		obj5.setSlot("d", null);
		
		assertSame(obj6, obj.findSlot("a"));
		assertSame(obj6, obj.findSlot("b"));
		assertSame(obj6, obj.findSlot("c"));
		assertNull(obj.findSlot("d"));
		assertNull(obj.findSlot("e"));
	}
	
	/**
	 * {@link anubis.runtime.AObject#findSlot(java.lang.String)} のためのテスト・メソッド。
	 */
	@Test
	public void testFindSlot03() {
		// obj.super = _super;
		// _super.super = obj;
		// _super.a = obj2;
		obj.setSlot(SUPER, _super);
		_super.setSlot(SUPER, obj);
		_super.setSlot("a", obj2);
		
		// obj.outer = _outer;
		// _outer.outer = obj;
		// _outer.b = obj3;
		obj.setSlot(OUTER, _outer);
		_outer.setSlot(OUTER, obj);
		_outer.setSlot("b", obj3);
		
		assertSame(obj2, obj.findSlot("a"));
		assertSame(obj3, obj.findSlot("b"));
		assertNull(obj.findSlot("c"));
	}
	
	/**
	 * {@link anubis.runtime.AObject#getSlot(anubis.SpecialSlot)} のためのテスト・メソッド。
	 */
	@Test
	public void testGetProto() {
		assertNull(obj.getSlot(SUPER));
		assertNull(obj.getSlot(OUTER));
		
		// obj.super = void;
		// obj.outer = void;
		obj.setSlot(SUPER, null);
		obj.setSlot(OUTER, null);
		assertNull(obj.getSlot(SUPER));
		assertNull(obj.getSlot(OUTER));
		
		// obj.super = _super;
		obj.setSlot(SUPER, _super);
		assertSame(_super, obj.getSlot(SUPER));
		assertNull(obj.getSlot(OUTER));
		
		// obj.outer = _outer;
		obj.setSlot(OUTER, _outer);
		assertSame(_super, obj.getSlot(SUPER));
		assertSame(_outer, obj.getSlot(OUTER));
		
		// obj.super = _outer;
		obj.setSlot(SUPER, _outer);
		assertSame(_outer, obj.getSlot(SUPER));
		assertSame(_outer, obj.getSlot(OUTER));
		
		// obj.outer = _super;
		obj.setSlot(OUTER, _super);
		assertSame(_outer, obj.getSlot(SUPER));
		assertSame(_super, obj.getSlot(OUTER));
		
		// obj.super = void;
		obj.setSlot(SUPER, null);
		assertNull(obj.getSlot(SUPER));
		assertSame(_super, obj.getSlot(OUTER));
		
		// obj.outer = void;
		obj.setSlot(OUTER, null);
		assertNull(obj.getSlot(SUPER));
		assertNull(obj.getSlot(OUTER));
	}
	
	/**
	 * {@link anubis.runtime.AObject#getSlot(java.lang.String)} のためのテスト・メソッド。
	 */
	@Test
	public void testGetSlot() {
		assertNull(obj.getSlot("a"));
		assertNull(obj.getSlot("b"));
		
		// obj.a = void;
		// obj.b = void;
		obj.setSlot("a", null);
		obj.setSlot("b", null);
		assertNull(obj.getSlot("a"));
		assertNull(obj.getSlot("b"));
		
		// obj.a = _super;
		obj.setSlot("a", _super);
		assertSame(_super, obj.getSlot("a"));
		assertNull(obj.getSlot("b"));
		
		// obj.b = _outer;
		obj.setSlot("b", _outer);
		assertSame(_super, obj.getSlot("a"));
		assertSame(_outer, obj.getSlot("b"));
		
		// obj.a = _outer;
		obj.setSlot("a", _outer);
		assertSame(_outer, obj.getSlot("a"));
		assertSame(_outer, obj.getSlot("b"));
		
		// obj.b = _super;
		obj.setSlot("b", _super);
		assertSame(_outer, obj.getSlot("a"));
		assertSame(_super, obj.getSlot("b"));
		
		// obj.a = void;
		obj.setSlot("a", null);
		assertNull(obj.getSlot("a"));
		assertSame(_super, obj.getSlot("b"));
		
		// obj.b = void;
		obj.setSlot("b", null);
		assertNull(obj.getSlot("a"));
		assertNull(obj.getSlot("b"));
	}
	
	@Test
	public void testSetSlotReadonly() {
		obj.setSlot("a", obj2, true);
		assertSame(obj2, obj.getSlot("a"));
	}
	
	@Test(expected = SlotReadonlyException.class)
	public void testSetSlotReadonly02() {
		obj.setSlot("a", obj2, true);
		assertSame(obj2, obj.getSlot("a"));
		obj.setSlot("a", obj3);
	}
}
