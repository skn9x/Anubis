package anubis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.StringReader;
import org.junit.After;
import org.junit.Before;
import anubis.ast.CompilationUnit;
import anubis.code.CodeBlock;
import anubis.code.asm.AsmCodeBlockFactory;
import anubis.parser.Parser;
import anubis.runtime.AObject;
import anubis.runtime.AObjects;
import anubis.runtime.ObjectFactory;
import anubis.runtime.StandardObjectFactory;

public class AbstractTest {
	private ObjectFactory oldFactory;
	protected AnubisObject _this;
	protected AnubisObject outer;
	protected AnubisObject local;
	
	public AnubisObject exec(CompilationUnit node) throws Exception {
		String className = "sample";
		// ê∂ê¨
		AsmCodeBlockFactory factory = new AsmCodeBlockFactory(className, true);
		CodeBlock code = factory.newCodeBlock(node);
		assertNotNull(code);
		
		// exec åƒÇ—èoÇµ
		return code.exec(local);
	}
	
	public AnubisObject exec(String code) throws Exception {
		return exec(new Parser().parse(new StringReader(code)));
	}
	
	@Before
	public void setUp() throws Exception {
		this.oldFactory = AObjects.setCurrent(new StandardObjectFactory());
		this._this = AObjects.getTraitsFactory().attach(new AObject());
		this.outer = AObjects.getTraitsFactory().attach(new AObject());
		this.local = AObjects.newContext(_this, outer);
	}
	
	@After
	public void tearDown() throws Exception {
		AObjects.setCurrent(oldFactory);
	}

	public void assertAEquals(Object expected, Object obj) {
		assertEquals(AObjects.getObject(expected), obj);
	}
	
}
