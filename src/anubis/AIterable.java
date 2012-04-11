package anubis;

import java.util.Iterator;

public interface AIterable extends AnubisObject, Iterable<AnubisObject> {
	public Iterator<AnubisObject> getAIterator();
}
