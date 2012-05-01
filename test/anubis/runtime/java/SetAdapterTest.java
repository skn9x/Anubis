package anubis.runtime.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import anubis.AbstractTest;
import anubis.AnubisObject;
import anubis.runtime.AObjects;

public class SetAdapterTest extends AbstractTest {
	/**
	 * adapter へ add した要素を origin から確認
	 */
	@Test
	public void testAdd() {
		Set<Integer> origin = new HashSet<Integer>();
		Set<AnubisObject> set = new SetAdapter<Integer>(origin, Integer.class);
		set.add(AObjects.getNumber(1));
		set.add(AObjects.getNumber(2));
		
		assertEquals(2, origin.size());
		assertTrue(origin.contains(1));
		assertTrue(origin.contains(2));
		assertFalse(origin.contains(3));
	}
	
	/**
	 * origin の要素を adapter から確認
	 */
	@Test
	public void testGet() {
		Set<Integer> origin = new HashSet<Integer>();
		Set<AnubisObject> set = new SetAdapter<Integer>(origin, Integer.class);
		origin.add(1);
		origin.add(2);
		
		assertEquals(2, set.size());
		assertTrue(set.contains(AObjects.getNumber(1)));
		assertTrue(set.contains(AObjects.getNumber(2)));
		assertFalse(set.contains(AObjects.getNumber(3)));
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
		List<AnubisObject> set = new ListAdapter<Integer>(origin, Integer.class);
		set.remove(AObjects.getNumber(2));
		
		assertEquals(2, set.size());
		assertTrue(set.contains(AObjects.getNumber(1)));
		assertFalse(set.contains(AObjects.getNumber(2)));
		assertTrue(set.contains(AObjects.getNumber(3)));
	}
	
	/**
	 * adapter と origin の size が一致することを確認
	 */
	@Test
	public void testSize() {
		Set<Integer> origin = new HashSet<Integer>();
		Set<AnubisObject> set = new SetAdapter<Integer>(origin, Integer.class);
		assertEquals(0, origin.size());
		assertEquals(0, set.size());
		
		set.add(AObjects.getNumber(1));
		assertEquals(1, origin.size());
		assertEquals(1, set.size());
		
		origin.add(2);
		assertEquals(2, origin.size());
		assertEquals(2, set.size());
	}
}
