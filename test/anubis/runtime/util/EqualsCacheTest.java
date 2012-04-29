package anubis.runtime.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import org.junit.Test;
import anubis.runtime.AString;
import anubis.runtime.ObjectFactory;
import anubis.runtime.StandardObjectFactory;

public class EqualsCacheTest {
	
	@Test
	public void test() {
		ObjectFactory factory = new StandardObjectFactory();
		Cache<String, AString> cache = new EqualsCache<String, AString>();
		
		String key = new String("hello world");
		cache.put(key, factory.getString(key));
		assertNotNull(cache.get(key));
		assertNotNull(cache.get(new String(key)));
		assertEquals(1, cache.size());
		
		Reference<String> ref = new WeakReference<String>(key);
		key = null;
		
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				new Object();
			}
			System.gc();
		}
		assertNull(ref.get());
		assertEquals(0, cache.size());
	}
	
}
