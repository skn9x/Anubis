package anubis.runtime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Iterator;
import org.junit.Test;
import anubis.AbstractTest;
import anubis.AnubisObject;

public class ARangeTest extends AbstractTest {
	
	@Test
	public void testGetItem() {
		// 昇順リスト
		{
			ARange range = new ARange(AObjects.getNumber(1), AObjects.getNumber(2), null);
			assertNull(range.getItem(AObjects.getObject(-1)));
			assertEquals(AObjects.getNumber(1), range.getItem(AObjects.getObject(0)));
			assertEquals(AObjects.getNumber(1.5), range.getItem(AObjects.getObject(0.5)));
			assertEquals(AObjects.getNumber(2), range.getItem(AObjects.getObject(1)));
			assertNull(range.getItem(AObjects.getObject(2)));
		}
		// 無限リスト
		{
			ARange range = new ARange(AObjects.getNumber(3), null, AObjects.getNumber(2));
			assertNull(range.getItem(AObjects.getObject(-1)));
			assertEquals(AObjects.getNumber(3), range.getItem(AObjects.getObject(0)));
			assertEquals(AObjects.getNumber(4), range.getItem(AObjects.getObject(0.5)));
			assertEquals(AObjects.getNumber(5), range.getItem(AObjects.getObject(1)));
			assertEquals(AObjects.getNumber(6), range.getItem(AObjects.getObject(1.5)));
			assertEquals(AObjects.getNumber(7), range.getItem(AObjects.getObject(2)));
		}
		// 降順リスト
		{
			ARange range = new ARange(AObjects.getNumber(3), AObjects.getNumber(1), AObjects.getNumber(-1));
			assertNull(range.getItem(AObjects.getObject(-1)));
			assertEquals(AObjects.getNumber(3), range.getItem(AObjects.getObject(0)));
			assertEquals(AObjects.getNumber(2.5), range.getItem(AObjects.getObject(0.5)));
			assertEquals(AObjects.getNumber(2), range.getItem(AObjects.getObject(1)));
			assertEquals(AObjects.getNumber(1.5), range.getItem(AObjects.getObject(1.5)));
			assertEquals(AObjects.getNumber(1), range.getItem(AObjects.getObject(2)));
			assertNull(range.getItem(AObjects.getObject(2.5)));
		}
	}
	
	@Test
	public void testIterator() {
		// 空Range
		{
			ARange range = new ARange(AObjects.getNumber(1), AObjects.getNumber(0), null);
			Iterator<AnubisObject> iter = range.iterator();
			
			assertFalse(iter.hasNext());
		}
		// 空じゃないRange
		{
			ARange range = new ARange(AObjects.getNumber(1), AObjects.getNumber(5), AObjects.getNumber(2));
			Iterator<AnubisObject> iter = range.iterator();
			
			assertTrue(iter.hasNext());
			assertEquals(AObjects.getNumber(1), iter.next());
			
			assertTrue(iter.hasNext());
			assertEquals(AObjects.getNumber(3), iter.next());
			
			assertTrue(iter.hasNext());
			assertEquals(AObjects.getNumber(5), iter.next());
			
			assertFalse(iter.hasNext());
		}
	}
}
