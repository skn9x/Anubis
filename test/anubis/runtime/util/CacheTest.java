package anubis.runtime.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import org.junit.Ignore;
import org.junit.Test;
import anubis.runtime.AString;
import anubis.runtime.ObjectFactory;
import anubis.runtime.StandardObjectFactory;

public class CacheTest {
	
	@Test
	@Ignore
	public void test() {
		ObjectFactory factory = new StandardObjectFactory();
		Cache<String, AString> cache = new Cache<String, AString>();
		
		String key = new String("hello world");
		cache.put(key, factory.getString(key));
		assertEquals(1, cache.size());
		assertNotNull(cache.get(key));
		
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
