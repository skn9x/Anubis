package anubis.code.asm;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import anubis.code.CodeBlock;

public class CodeBuilderTest {
	@Test
	public void testFinallize() throws Exception {
		String className = "abc";
		
		// コンパイル
		byte[] result = new CodeBuilder(className).finallize();
		assertNotNull(result);
		
		// クラスロード
		CustomCodeClassLoader loader = new CustomCodeClassLoader();
		loader.putClassData(className, result);
		Class<?> clazz = loader.loadClass(className);
		assertNotNull(clazz);
		
		// オブジェクト生成
		CodeBlock code = (CodeBlock) clazz.getField("INSTANCE").get(null);
		assertNotNull(code);
		
		// exec 呼び出し
		assertNull(code.exec(null));
	}
}
