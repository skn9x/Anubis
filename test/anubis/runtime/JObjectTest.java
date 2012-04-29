package anubis.runtime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import anubis.AnubisObject;
import anubis.SpecialSlot;

/**
 * @author SiroKuro
 */
public class JObjectTest {
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		AObjects.setCurrent(new StandardObjectFactory());
	}
	
	@After
	public void tearDown() throws Exception {
		AObjects.setCurrent(null);
	}
	
	@Test
	public void testNewInstance01() throws Exception {
		JObject obj = AObjects.newJObject("abc");
		assertNotNull(obj);
		assertEquals(JClass.class, obj.getSlot(SpecialSlot.SUPER).getClass());
	}
	
	@Test
	public void testNewInstance02() throws Exception {
		JClass jcls = (JClass) AObjects.getObject(String.class);
		JFunction func = (JFunction) jcls.findSlot("new");
		AnubisObject obj = func.call(jcls, new AString("abc"));
		assertNotNull(obj);
		assertEquals(JClass.class, obj.getSlot(SpecialSlot.SUPER).getClass());
	}
}
