package anubis.runtime.java;

import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Test;
import anubis.AbstractTest;
import anubis.AnubisObject;
import anubis.runtime.AObjects;

public class MapAdapterTest extends AbstractTest {
	/**
	 * Entry.setValue の確認
	 */
	@Test
	public void testEntrySetValue() {
		Map<Integer, String> origin = new HashMap<Integer, String>();
		Map<AnubisObject, AnubisObject> map = new MapAdapter<Integer, String>(origin, Integer.class, String.class);
		origin.put(1, "a");
		origin.put(2, "b");
		
		for (Entry<AnubisObject, AnubisObject> ent: map.entrySet()) {
			ent.setValue(AObjects.getObject(ent.getValue() + "999"));
		}
		assertEquals(2, origin.size());
		assertEquals("a999", origin.get(1));
		assertEquals("b999", origin.get(2));
	}
	
	/**
	 * origin の要素を adapter から確認
	 */
	@Test
	public void testGet() {
		Map<Integer, String> origin = new HashMap<Integer, String>();
		Map<AnubisObject, AnubisObject> map = new MapAdapter<Integer, String>(origin, Integer.class, String.class);
		origin.put(1, "a");
		origin.put(2, "b");
		
		assertEquals(2, map.size());
		assertAEquals("a", map.get(AObjects.getObject(1)));
		assertAEquals("b", map.get(AObjects.getObject(2)));
	}
	
	/**
	 * adapter に put した要素を origin から確認
	 */
	@Test
	public void testPut() {
		Map<Integer, String> origin = new HashMap<Integer, String>();
		Map<AnubisObject, AnubisObject> map = new MapAdapter<Integer, String>(origin, Integer.class, String.class);
		map.put(AObjects.getObject(1), AObjects.getObject("a"));
		map.put(AObjects.getObject(2), AObjects.getObject("b"));
		
		assertEquals(2, origin.size());
		assertEquals("a", origin.get(1));
		assertEquals("b", origin.get(2));
	}
	
	/**
	 * adapter から remove した要素を origin から確認
	 */
	@Test
	public void testRemove() {
		Map<Integer, String> origin = new HashMap<Integer, String>();
		origin.put(1, "a");
		origin.put(2, "b");
		Map<AnubisObject, AnubisObject> map = new MapAdapter<Integer, String>(origin, Integer.class, String.class);
		map.remove(AObjects.getObject(1));
		
		assertEquals(1, origin.size());
		assertEquals("b", origin.get(2));
	}
	
	/**
	 * adapter と origin の size が一致することを確認
	 */
	@Test
	public void testSize() {
		Map<Integer, String> origin = new HashMap<Integer, String>();
		Map<AnubisObject, AnubisObject> map = new MapAdapter<Integer, String>(origin, Integer.class, String.class);
		assertEquals(0, origin.size());
		assertEquals(0, map.size());
		
		origin.put(1, "a");
		assertEquals(1, origin.size());
		assertEquals(1, map.size());
		
		map.put(AObjects.getObject(2), AObjects.getObject("b"));
		assertEquals(2, origin.size());
		assertEquals(2, map.size());
	}
}
