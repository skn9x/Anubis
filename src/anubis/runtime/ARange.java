package anubis.runtime;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.NoSuchElementException;
import anubis.AIterable;
import anubis.ASliceable;
import anubis.AnubisObject;
import anubis.TypeName;
import anubis.runtime.traits.func.NumberOperator;

@TypeName(ObjectType.LIST)
public class ARange extends AObject implements ASliceable, AIterable {
	private final AnubisObject start, end, step;
	private final boolean decent;
	
	public ARange(AnubisObject start, AnubisObject end, AnubisObject step) {
		assert start != null;
		this.start = start;
		this.end = end;
		this.step = step;
		this.decent = step != null && NumberOperator.minus(step) != null;
	}
	
	@Override
	public Iterator<AnubisObject> getAIterator() {
		return new Iterator<AnubisObject>() {
			private BigInteger index = BigInteger.ZERO;
			
			@Override
			public boolean hasNext() {
				AnubisObject next = getItem(AObjects.getNumber(index));
				if (next == null)
					return false;
				return true;
			}
			
			@Override
			public AnubisObject next() {
				AnubisObject next = getItem(AObjects.getNumber(index));
				if (next == null)
					throw new NoSuchElementException();
				index = index.add(BigInteger.ONE);
				return next;
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	@Override
	public AnubisObject getItem(AnubisObject index) {
		if (NumberOperator.minus(index) != null) {
			return null;
		}
		if (step != null) {
			index = NumberOperator.multiply(index, step);
		}
		AnubisObject value = NumberOperator.add(start, index);
		if (end != null) {
			if (decent ? NumberOperator.lessThan(value, end) != null : NumberOperator.lessThan(end, value) != null) {
				return null;
			}
		}
		return value;
	}
	
	@Override
	public AnubisObject getItem(AnubisObject start, AnubisObject end) {
		AnubisObject newStart = getItem(start);
		if (newStart == null) {
			newStart = start;
		}
		AnubisObject newEnd = getItem(end);
		if (newEnd == null) {
			newEnd = end;
		}
		AnubisObject newStep = step;
		if (NumberOperator.lessThan(end, start) != null) {
			newStep = NumberOperator.negative(step);
		}
		return AObjects.newRange(newStart, newEnd, newStep);
	}
	
	@Override
	public Iterator<AnubisObject> iterator() {
		return getAIterator();
	}
	
	@Override
	public AnubisObject setItem(AnubisObject index, AnubisObject value) {
		throw new UnsupportedOperationException();
	}
}
