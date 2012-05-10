package anubis.runtime;

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
			assertAEquals(1, range.getItem(AObjects.getObject(0)));
			assertAEquals(1.5, range.getItem(AObjects.getObject(0.5)));
			assertAEquals(2, range.getItem(AObjects.getObject(1)));
			assertNull(range.getItem(AObjects.getObject(2)));
		}
		// 無限リスト
		{
			ARange range = new ARange(AObjects.getNumber(3), null, AObjects.getNumber(2));
			assertNull(range.getItem(AObjects.getObject(-1)));
			assertAEquals(3, range.getItem(AObjects.getObject(0)));
			assertAEquals(4, range.getItem(AObjects.getObject(0.5)));
			assertAEquals(5, range.getItem(AObjects.getObject(1)));
			assertAEquals(6, range.getItem(AObjects.getObject(1.5)));
			assertAEquals(7, range.getItem(AObjects.getObject(2)));
		}
		// 降順リスト
		{
			ARange range = new ARange(AObjects.getNumber(3), AObjects.getNumber(1), AObjects.getNumber(-1));
			assertNull(range.getItem(AObjects.getObject(-1)));
			assertAEquals(3, range.getItem(AObjects.getObject(0)));
			assertAEquals(2.5, range.getItem(AObjects.getObject(0.5)));
			assertAEquals(2, range.getItem(AObjects.getObject(1)));
			assertAEquals(1.5, range.getItem(AObjects.getObject(1.5)));
			assertAEquals(1, range.getItem(AObjects.getObject(2)));
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
			assertAEquals(1, iter.next());
			
			assertTrue(iter.hasNext());
			assertAEquals(3, iter.next());
			
			assertTrue(iter.hasNext());
			assertAEquals(5, iter.next());
			
			assertFalse(iter.hasNext());
		}
	}
	
	@Test
	public void testSlice() {
		// 1, 2, 3, 4, 5
		ARange range = new ARange(AObjects.getNumber(1), AObjects.getNumber(5), AObjects.getNumber(1));
		{
			// 2, 3, 4
			ARange subRange = (ARange) range.getItem(AObjects.getNumber(1), AObjects.getNumber(3));
			assertAEquals(2, subRange.getItem(AObjects.getNumber(0)));
			assertAEquals(3, subRange.getItem(AObjects.getNumber(1)));
			assertAEquals(4, subRange.getItem(AObjects.getNumber(2)));
			assertNull(subRange.getItem(AObjects.getNumber(3)));
		}
		{
			// 4, 3, 2
			ARange subRange = (ARange) range.getItem(AObjects.getNumber(3), AObjects.getNumber(1));
			assertAEquals(4, subRange.getItem(AObjects.getNumber(0)));
			assertAEquals(3, subRange.getItem(AObjects.getNumber(1)));
			assertAEquals(2, subRange.getItem(AObjects.getNumber(2)));
			assertNull(subRange.getItem(AObjects.getNumber(3)));
		}
	}
}
