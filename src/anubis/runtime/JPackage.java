package anubis.runtime;

import anubis.AnubisObject;

/**
 * @author SiroKuro
 */
public class JPackage extends AObject {
	private final Package pack;
	
	public JPackage() {
		this.pack = null;
	}
	
	public JPackage(Package pack) {
		this.pack = pack;
	}
	
	@Override
	public AnubisObject getSlot(String name) {
		synchronized (this) {
			AnubisObject obj = super.getSlot(name);
			if (obj == null && !name.contains(".")) {
				String fullName = pack == null ? name : pack.getName() + "." + name;
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
			return AObjects.getJClass(Class.forName(name));
		}
		catch (ClassNotFoundException ignore) {
			return null;
		}
	}
	
	private static JPackage newPackage(String name) {
		Package pack = Package.getPackage(name);
		if (pack != null) {
			return new JPackage(pack);
		}
		return null;
	}
}
