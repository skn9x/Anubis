package anubis.runtime.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import anubis.AbstractTest;
import anubis.AnubisObject;

public class IteratorAdapterTest extends AbstractTest {
	
	@Test
	public void test() {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
		Iterator<AnubisObject> iter = new IteratorAdapter(list.iterator());
		
		assertTrue(iter.hasNext());
		assertAEquals(1, iter.next());
		
		assertTrue(iter.hasNext());
		assertAEquals(2, iter.next());
		iter.remove();
		
		assertTrue(iter.hasNext());
		assertAEquals(3, iter.next());
		
		assertFalse(iter.hasNext());
		
		assertEquals(2, list.size());
		assertEquals((Integer) 1, list.get(0));
		assertEquals((Integer) 3, list.get(1));
	}
	
}
