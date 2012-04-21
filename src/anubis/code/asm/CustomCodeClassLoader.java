package anubis.code.asm;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomCodeClassLoader extends ClassLoader {
	private final Map<String, ClassData> clss = new HashMap<String, ClassData>();
	private final String programName;
	private final ClassLoader parent;
	private int count = 0;
	
	public CustomCodeClassLoader(String programName) {
		this.programName = programName;
		this.parent = this.getClass().getClassLoader();
	}
	
	public Collection<ClassData> getClassData() {
		return clss.values();
	}
	
	public synchronized String newClassName() {
		if (count++ == 0) {
			return programName;
		}
		else {
			return programName + "$" + count;
		}
	}
	
	public void putClassData(String name, byte[] data) {
		if (clss.containsKey(name))
			throw new IllegalStateException("duplicated classname: " + name);
		clss.put(name, new ClassData(name, data));
	}
	
	public void saveClassFiles(File dir) throws IOException {
		for (ClassData data: getClassData()) {
			String filepath = data.name + ".class";
			if (dir != null) {
				filepath = dir.getAbsolutePath() + File.pathSeparator + filepath;
			}
			DataOutputStream out = null;
			try {
				out = new DataOutputStream(new FileOutputStream(filepath));
				out.write(data.data);
			}
			finally {
				if (out != null)
					out.close();
			}
		}
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		CustomCodeClassLoader.ClassData cd = clss.get(name);
		if (cd != null) {
			synchronized (this) {
				if (cd.cls == null) {
					cd.cls = defineClass(name, cd.data, 0, cd.data.length);
				}
			}
			return cd.cls;
		}
		return parent.loadClass(name);
	}
	
	private static class ClassData {
		public final String name;
		public final byte[] data;
		public Class<?> cls;
		
		public ClassData(String name, byte[] data) {
			this.name = name;
			this.data = data;
		}
	}
}
