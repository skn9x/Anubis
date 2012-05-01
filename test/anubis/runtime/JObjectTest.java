package anubis.runtime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import anubis.AbstractTest;
import anubis.AnubisObject;
import anubis.SpecialSlot;
import anubis.runtime.java.JCaster;

/**
 * @author SiroKuro
 */
public class JObjectTest extends AbstractTest {
	@Test
	public void testNewInstance01() throws Exception {
		JObject obj = AObjects.newJObject("abc");
		assertNotNull(obj);
		assertEquals(JClass.class, obj.getSlot(SpecialSlot.SUPER).getClass());
	}
	
	@Test
	public void testNewInstance02() throws Exception {
		local.setSlot("cls", AObjects.getObject(String.class));
		AnubisObject result = exec("cls.new('abc')");
		
		assertNotNull(result);
		assertEquals(JObject.class, result.getClass());
		assertEquals(String.class, JCaster.cast(result).getClass());
	}
	
	@Test
	public void testNewInstance03() throws Exception {
		local.setSlot("cls", AObjects.getObject(Sample.class));
		
		exec("obj = cls.new(1, 2)");
		assertAEquals(1, exec("obj.x"));
		assertAEquals(2, exec("obj.y"));
		
		exec("obj.x = 3");
		exec("obj.y = 4");
		assertAEquals(3, exec("obj.getX()"));
		assertAEquals(4, exec("obj.getY()"));
	}
	
	private static class Sample {
		private int x;
		public int y;
		
		public Sample(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@SuppressWarnings("unused")
		public int getX() {
			return x;
		}
		
		@SuppressWarnings("unused")
		public int getY() {
			return y;
		}
		
		@SuppressWarnings("unused")
		public void setX(int x) {
			this.x = x;
		}
		
		@SuppressWarnings("unused")
		public void setY(int y) {
			this.y = y;
		}
	}
}
