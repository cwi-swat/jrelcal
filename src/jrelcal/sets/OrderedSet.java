/*
 * Created on Jan 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jrelcal.sets;

import java.util.TreeSet;
import java.util.Iterator;

/**
 * @author storm
 * @class jrelcal.OrderedSet
 */
@SuppressWarnings("serial")
public class OrderedSet<T extends Comparable<T>> extends TreeSet<T>
	implements Comparable<OrderedSet<T>> {

	private OrderedSet<T> copy() {
		OrderedSet<T> set = new OrderedSet<T>();
		set.addAll(this);
		return set;
	}
		
	public OrderedSet() {
		super();
	}
	
	public OrderedSet(T element) {
		this();
		add(element);
	}
	
	public OrderedSet<T> union(OrderedSet<T> bag) {
		OrderedSet<T> newSet = copy();
		newSet.addAll(bag);
		return newSet;
	}

	public OrderedSet<T> difference(OrderedSet<T> bag) {
		OrderedSet<T> newSet = copy();
		newSet.removeAll(bag);
		return newSet;
	}

	public OrderedSet<T> intersection(OrderedSet<T> bag) {
		OrderedSet<T> newSet = copy();
		newSet.retainAll(bag);
		return newSet;
	}

	public VertexSet toVertexSet(IndexedSet<T> set) {
		VertexSet vertexSet = new VertexSet();
		for (T t: this)
			vertexSet.add(set.indexOf(t));
		return vertexSet;
	}

	
	public int compareTo(OrderedSet<T> set) {
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
	
	public boolean equals(OrderedSet<T> set) {
		return compareTo(set) == 0;
	}
	
	public IndexedSet<T> toIndexedSet() {
		IndexedSet<T> set = new IndexedSet<T>();
		for (T t: this)
			set.add(t);
		return set;
	}
	
}
