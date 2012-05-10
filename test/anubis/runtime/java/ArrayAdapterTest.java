package anubis.runtime.java;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;
import anubis.AbstractTest;
import anubis.AnubisObject;

public class ArrayAdapterTest extends AbstractTest {
	
	@Test
	public void testGetInt() {
		int[] origin = {
			1, 2, 3
		};
		List<AnubisObject> list = new ArrayAdapter(origin);
		
		assertEquals(3, list.size());
		assertAEquals(1, list.get(0));
		assertAEquals(2, list.get(1));
		assertAEquals(3, list.get(2));
	}
	
	@Test
	public void testSize() {
		assertEquals(3, new ArrayAdapter(new int[]{
			1, 2, 3,
		}).size());
		assertEquals(0, new ArrayAdapter(new int[]{ }).size());
	}
	
}
