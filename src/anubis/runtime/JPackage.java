package anubis.runtime;

import anubis.ACastable;
import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
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
					JClass c = newClass(fullName);
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
	
	@Override
	public String getType() {
		return ObjectType.JPACKAGE;
	}
	
	private static JClass newClass(String name) {
		try {
			return (JClass) AObjects.getObject(Class.forName(name)); // TODO キャストを整理
		}
		catch (ClassNotFoundException ignore) {
			return null;
		}
	}
	
	private static JPackage newPackage(String name) {
		return new JPackage(name);
	}
}
