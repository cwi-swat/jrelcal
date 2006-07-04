package jrelcal.sets;

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("serial")
public class IndexedSet<T extends Comparable<T>> extends OrderedSet<T> 
	implements Indexable<T> {

	public VertexSet toVertexSet() {
		return super.toVertexSet(this);
	}
	
	private IndexedSet<T> copy() {
		IndexedSet<T> set = new IndexedSet<T>();
		set.addAll(this);
		return set;
	}
		
	public IndexedSet<T> union(IndexedSet<T> set) {
		IndexedSet<T> newSet = copy();
		newSet.addAll(set);
		return newSet;
	}

	public IndexedSet<T> difference(IndexedSet<T> set) {
		IndexedSet<T> newSet = copy();
		newSet.removeAll(set);
		return newSet;
	}

	public IndexedSet<T> intersection(IndexedSet<T> set) {
		IndexedSet<T> newSet = copy();
		newSet.retainAll(set);
		return newSet;
	}
	
	public T elementAt(int n) {
		int i = 0;
		for (T t : this) {
			if (n == i)
				return t;
			i++;
		}
		throw new NoSuchElementException();
	}

	public int indexOf(T element) {
		int i = 0;
		for (T t : this) {
			if (t.equals(element))
				return i;
			i++;
		}
		return -1;
	}

	public boolean equals(IndexedSet<T> set) {
		return compareTo(set) == 0;
	}
	
	
	public int compareTo(IndexedSet<T> set) {
		int thisSize = size();
		int thatSize = set.size();
		if (thisSize < thatSize) return -1;
		if (thisSize > thatSize) return +1;
		Iterator<T> thatIterator = set.iterator();
		for (Iterator<T> thisIterator = iterator(); thisIterator.hasNext();) {
			T thisElt = thisIterator.next();
			T thatElt = thatIterator.next();
			int cmp = thisElt.compareTo(thatElt);
			if (cmp != 0) return cmp;
		}
		return 0;
	}
	
}
