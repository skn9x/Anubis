package anubis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.StringReader;
import org.junit.After;
import org.junit.Before;
import anubis.ast.CompilationUnit;
import anubis.code.CodeBlock;
import anubis.code.Option;
import anubis.code.asm.AsmCodeBlockFactory;
import anubis.parser.Parser;
import anubis.runtime.AObjects;
import anubis.runtime.ObjectFactory;
import anubis.runtime.StandardObjectFactory;

public class AbstractTest {
	private ObjectFactory factory;
	private ObjectFactory oldFactory;
	protected AnubisObject _this;
	protected AnubisObject outer;
	protected AnubisObject local;
	
	public void assertAEquals(Object expected, Object obj) {
		ObjectFactory oldfactory = AObjects.setCurrent(factory);
		try {
			assertEquals(AObjects.getObject(expected), obj);
		}
		finally {
			AObjects.setCurrent(oldfactory);
		}
	}
	
	public AnubisObject exec(CompilationUnit node) throws Exception {
		ObjectFactory oldfactory = AObjects.setCurrent(factory);
		try {
			// 生成
			Option option = new Option();
			option.setProgramName("sample");
			CodeBlock code = new AsmCodeBlockFactory().newCodeBlock(node, option);
			assertNotNull(code);
			
			// exec 呼び出し
			return code.exec(local);
		}
		finally {
			AObjects.setCurrent(oldfactory);
		}
	}
	
	public AnubisObject exec(String code) throws Exception {
		ObjectFactory oldfactory = AObjects.setCurrent(factory);
		try {
			return exec(new Parser().parse(new StringReader(code)));
		}
		finally {
			AObjects.setCurrent(oldfactory);
		}
	}
	
	@Before
	public void setUp() throws Exception {
		this.factory = new StandardObjectFactory();
		this.oldFactory = AObjects.setCurrent(factory);
		this._this = AObjects.newObject(null);
		this.outer = AObjects.newObject(null);
		this.local = AObjects.newContext(_this, outer);
	}
	
	@After
	public void tearDown() throws Exception {
		AObjects.setCurrent(oldFactory);
	}
	
}
