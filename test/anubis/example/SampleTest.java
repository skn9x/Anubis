package anubis.example;

import org.junit.runner.RunWith;
import anubis.test.AnubisTestRunner;
import anubis.test.SuiteCodes;

@RunWith(AnubisTestRunner.class)
@SuiteCodes({
	"tarai.an", "fib.an", "stringbuilder.an", "fizzbuzz.an"
})
public class SampleTest {
}
