package anubis.code.asm;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import anubis.code.CodeBlock;

public class CodeBuilderTest {
	@Test
	public void testFinallize() throws Exception {
		String className = "abc";
		
		// �R���p�C��
		byte[] result = new CodeBuilder(className).finallize();
		assertNotNull(result);
		
		// �N���X���[�h
		CustomCodeClassLoader loader = new CustomCodeClassLoader();
		loader.putClassData(className, result);
		Class<?> clazz = loader.loadClass(className);
		assertNotNull(clazz);
		
		// �I�u�W�F�N�g����
		CodeBlock code = (CodeBlock) clazz.getField("INSTANCE").get(null);
		assertNotNull(code);
		
		// exec �Ăяo��
		assertNull(code.exec(null));
	}
}
