package anubis.runtime.java;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import anubis.AbstractTest;
import anubis.AnubisObject;
import anubis.runtime.AObjects;

public class ListAdapterTest extends AbstractTest {
	/**
	 * adapter へ add した要素を origin から確認
	 */
	@Test
	public void testAdd() {
		List<Integer> origin = new ArrayList<Integer>();
		List<AnubisObject> list = new ListAdapter<Integer>(origin, Integer.class);
		list.add(AObjects.getNumber(1));
		list.add(AObjects.getNumber(2));
		
		assertEquals(2, origin.size());
		assertEquals((Object) 1, origin.get(0));
		assertEquals((Object) 2, origin.get(1));
	}
	
	/**
	 * origin の要素を adapter から確認
	 */
	@Test
	public void testGet() {
		List<Integer> origin = new ArrayList<Integer>();
		List<AnubisObject> list = new ListAdapter<Integer>(origin, Integer.class);
		origin.add(1);
		origin.add(2);
		
		assertEquals(2, list.size());
		assertAEquals(1, list.get(0));
		assertAEquals(2, list.get(1));
	}
	
	@Test
	public void testInScript() throws Exception {
		List<Integer> origin = new ArrayList<Integer>();
		origin.add(1234);
		origin.add(5678);
		local.setSlot("obj", AObjects.getObject(origin));
		assertAEquals(1234, exec("obj[0]"));
		assertAEquals(5678, exec("obj[1]"));
	}
	
	/**
	 * adapter から remove した要素を origin から確認
	 */
	@Test
	public void testRemove() {
		List<Integer> origin = new ArrayList<Integer>();
		origin.add(1);
		origin.add(2);
		origin.add(3);
		List<AnubisObject> list = new ListAdapter<Integer>(origin, Integer.class);
		list.remove(1);
		
		assertEquals(2, origin.size());
		assertEquals((Object) 1, origin.get(0));
		assertEquals((Object) 3, origin.get(1));
	}
	
	/**
	 * adapter へ set した要素を origin から確認
	 */
	@Test
	public void testSet() {
		List<Integer> origin = new ArrayList<Integer>();
		origin.add(1);
		origin.add(2);
		origin.add(3);
		List<AnubisObject> list = new ListAdapter<Integer>(origin, Integer.class);
		list.set(1, AObjects.getNumber(4));
		
		assertEquals(3, origin.size());
		assertEquals((Object) 1, origin.get(0));
		assertEquals((Object) 4, origin.get(1));
		assertEquals((Object) 3, origin.get(2));
	}
	
	/**
	 * adapter と origin の size が一致することを確認
	 */
	@Test
	public void testSize() {
		List<Integer> origin = new ArrayList<Integer>();
		List<AnubisObject> list = new ListAdapter<Integer>(origin, Integer.class);
		assertEquals(0, origin.size());
		assertEquals(0, list.size());
		
		list.add(AObjects.getNumber(1));
		assertEquals(1, origin.size());
		assertEquals(1, list.size());
		
		origin.add(2);
		assertEquals(2, origin.size());
		assertEquals(2, list.size());
	}
}
