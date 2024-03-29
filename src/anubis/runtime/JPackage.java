package anubis.runtime;

import anubis.ACastable;
import anubis.AnubisObject;
import anubis.TypeName;

/**
 * @author SiroKuro
 */
@TypeName(ObjectType.JPACKAGE)
public class JPackage extends AObject implements ACastable {
	private final String packName;
	private final Package pack;
	
	public JPackage() {
		this.packName = null;
		this.pack = null;
	}
	
	public JPackage(Package pack) {
		this.packName = pack.getName();
		this.pack = pack;
	}
	
	public JPackage(String packName) {
		this.packName = packName;
		this.pack = Package.getPackage(packName);
	}
	
	@Override
	public Object asJava() {
		if (pack != null)
			return pack;
		else
			return this;
	}
	
	@Override
	public String debugString() {
		return super.debugString() + "(" + packName + ")";
	}
	
	@Override
	public AnubisObject getSlot(String name) {
		synchronized (this) {
			AnubisObject obj = super.getSlot(name);
			if (obj == null && !name.contains(".")) {
				String fullName = packName == null ? name : packName + "." + name;
				// クラス作成
				{
					AnubisObject c = newClass(fullName);
					if (c != null) {
						setSlot(name, c);
						return c;
					}
				}
				// TODO インタフェイス作成
				{
					// パッケージ作成
					JPackage p = newPackage(fullName);
					if (p != null) {
						setSlot(name, p);
						return p;
					}
				}
			}
			return obj;
		}
	}
	
	private static AnubisObject newClass(String name) {
		try {
			return AObjects.getObject(Class.forName(name));
		}
		catch (ClassNotFoundException ignore) {
			return null;
		}
	}
	
	private static JPackage newPackage(String name) {
		return new JPackage(name);
	}
}
