package anubis.example;

import org.junit.runner.RunWith;
import anubis.test.AnubisTestRunner;
import anubis.test.SuiteCodes;

@RunWith(AnubisTestRunner.class)
@SuiteCodes({
	"tarai.an", "fib.an"
})
public class SampleTest {
}