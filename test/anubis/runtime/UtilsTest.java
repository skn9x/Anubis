package anubis.runtime;

import static org.junit.Assert.assertNull;
import java.math.BigInteger;
import org.junit.Test;

public class UtilsTest {
	@Test
	public void testCast() {
		ANumber number = new AInteger(BigInteger.ONE);
		assertNull(Utils.cast(number, AString.class));
	}
}
