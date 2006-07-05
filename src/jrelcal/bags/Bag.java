/*
 * Created on Jan 19, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jrelcal.bags;


import java.util.Collection;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import jrelcal.sets.IndexedSet;

/**
 * @author storm
 * @class jrelcal.TreeBag
 */
public class Bag<T extends Comparable<T>>  
	implements Comparable<Bag<T>>, Collection<T> {
	
	protected TreeMap<T,Integer> _map;

	protected TreeMap<T, Integer> getMap() {
		return _map;
	}
	
	public IndexedSet<T> toIndexedSet() {
		IndexedSet<T> set = new IndexedSet<T>();
		for (T t: this)
			set.add(t);
		return set;
	}
	
	
	
	public Bag() {
		_map = new TreeMap<T,Integer>();
	}

	public Bag(T t) {
		_map = new TreeMap<T,Integer>();
		add(t);
	}

	
	private Bag<T> copy() {
		Bag<T> newBag = new Bag<T>();
		newBag.addAll(this);
		return newBag;
	}
	
	public VertexBag toVertexBag(IndexedSet<T> set) {
		VertexBag bag = new VertexBag();
		for (T t: this)
			bag.add(set.indexOf(t));
		return bag;
	}
	
	public VertexBag toVertexBag() {
		return toVertexBag(toIndexedSet());
	}
	
	public boolean add(T element) {
		return addAdditive(element);
	}
	
	public boolean addMaximally(T element) {
		if (contains(element))
			return false;
		else {
			putElement(element, 1);
		}
		return true;
	}
	
	public boolean addAdditive(T element) {
		//System.out.println("Adding " + element);
		//System.out.println("-- Before: " + this);
		if (contains(element)) {
			Integer mult = multiplicity(element);
			mult++;
			putElement(element, mult);
		}
		else {
			putElement(element, 1);
		}
		//System.out.println("-- After: " + this);
		return true;
	}
	
	protected void putElement(T element, Integer mult) {
		getMap().put(element, mult);
	}
	
	public Bag<T> additiveUnion(Bag<T> bag) {
		Bag<T> newBag = copy();
		for (T key : bag.uniqueElements()) {
			Integer thatMult = bag.multiplicity(key);
			if (contains(key)) {
				Integer thisMult = multiplicity(key);
				newBag.putElement(key, thisMult + thatMult);
			} 
			else
				newBag.putElement(key, bag.multiplicity(key));
		}
		return newBag;
	}

	public Bag<T> difference(Bag<T> bag) {
		Bag<T> newBag = copy();
		for (T key : bag.uniqueElements()) {
			if (contains(key)) {
				Integer thisMult = multiplicity(key);
				Integer thatMult = bag.multiplicity(key);
				Integer newMult = thisMult - thatMult;
				if (newMult <= 0) {
					newBag.removeElement(key);
				} 
				else 
					newBag.putElement(key, newMult);
			}
		}
		return newBag;
	}

	public Bag<T> maximalUnion(Bag<T> bag) {
		Bag<T> newBag = copy();
		for (T key : bag.uniqueElements()) {
			Integer thatMult = bag.multiplicity(key);
			if (contains(key)) {
				Integer thisMult = multiplicity(key);
				newBag.putElement(key, thatMult > thisMult ? thatMult : thisMult);
			} 
			else 
				newBag.putElement(key, thatMult);
		}
		return newBag;
	}

	public Bag<T> intersection(Bag<T> bag) {
		Bag<T> newBag = new Bag<T>();
		for (T key : bag.uniqueElements()) {
			if (contains(key)) {
				Integer thisMult = multiplicity(key);
				Integer thatMult = bag.multiplicity(key);
				newBag.getMap().put(key, thatMult < thisMult ? thatMult : thisMult);
			} 
		}
		return newBag;
	}

	public Bag<T> unique() {
		Bag<T> newBag = new Bag<T>();
		for (T key : uniqueElements())
			newBag.add(key);
		return newBag;
	}

	public boolean equals(Object o) {
		return equals((Bag<T>)o);
	}
	
	public boolean equals(Bag<T> bag) {
		for (T t: uniqueElements()) {
			if (multiplicity(t) != bag.multiplicity(t)) {
				return false;
			}
		}
		for (T t: bag.uniqueElements()) {
			if (multiplicity(t) != bag.multiplicity(t)) {
				return false;
			}
		}
		return true;
	}
	
	
	public boolean contains(T key) {
		return getMap().containsKey(key) && getMap().get(key) > 0;
	}

	public boolean contains(Object o) {
		return contains((T)o);
	}

	public boolean containsAll(Bag<T> bag) {
		for (T element: bag) 
			if (!containsEntries(element, bag.multiplicity(element)))
				return false;
		return true;
	}

	private boolean containsEntries(T key, Integer mult) {
		return getMap().containsKey(key) && multiplicity(key) >= mult;
	}

	public int cardinality() {
		int i = 0;
		for (T key: uniqueElements())
			i += multiplicity(key);
		return i;	
	}

	protected Set<T> uniqueElements() {
		return getMap().keySet();
	}
	
	
	public Iterator<T> iterator() {
		return new TreeBagIterator<T>(this);
	}

	
	public int compareTo(Bag<T> bag) {
		int thisSize = cardinality();
		int thatSize = bag.cardinality();
		if (thisSize < thatSize) return -1;
		if (thisSize > thatSize) return +1;
		
		Iterator<T> thatIterator = bag.uniqueElements().iterator();
		for (Iterator<T> thisIterator = uniqueElements().iterator(); thisIterator.hasNext();) {
			T thisKey = thisIterator.next();
			T thatKey = thatIterator.next();
			int cmp = thisKey.compareTo(thatKey);
			if (cmp != 0) return cmp;
			cmp = getMap().get(thisKey).compareTo(bag.getMap().get(thatKey));
			if (cmp != 0) return cmp;
		}
		return 0;
	}

	public boolean addAll(Collection<? extends T> c) {
		return addAllAdditive(c);
	}
	
	
	public boolean addAllMaximally(Collection< ? extends T> c) {
		boolean b = false;
		Bag<T> bag = new Bag<T>();
		for (T t: c) 
			bag.addAdditive(t);
		for (T t: bag.uniqueElements()) {
			int mult = bag.multiplicity(t);
			if (mult > multiplicity(t))
				putElement(t, mult);
		}
		return b;
	}
	
	public boolean addAllAdditive(Collection< ? extends T> c) {
		boolean b = false;
		for (T t: c) {
			b |= addAdditive(t);
		}
		return b;
	}
	
	
	public boolean containsAll(Collection< ? > c) {
		for (Object o: c) {
			if (!contains(o))
				return false;
		}
		return true;
	}
	
	public int multiplicity(T element) {
		if (contains(element)) 
			return getMap().get(element);
		return 0;
	}
	

	private void removeElement(T element) {
		getMap().remove(element);
	}
	
	public boolean remove(T element) {
		int mult = multiplicity(element);
		if (mult > 1) {
			putElement(element, mult - 1);
			return true;
		}
		if (mult == 1) {
			removeElement(element);
			return true;
		}
		return false;
	}
	
	public boolean remove(Object o) {
		return this.remove((T)o);
	}

	
	public boolean removeAll(Collection< ? > c) {
		boolean b = false;
		for (Object o: c) {
			b |= remove(o);
		}
		return b;
	}

	public void clear() {
		getMap().clear();
	}
	
	public boolean isEmpty() {
		return getMap().isEmpty();
	}
	
	public int size() {
		return cardinality();
	}
	
	public boolean retainAll(Collection< ? > c) {
		boolean b = false;
		for (Object o: this) {
			if (!c.contains(o))
				b |= remove(o);
		}
		return b;
	}
	
	public Object[] toArray() {
		int size = size();
		Object[] a = new Object[size];
		int i = 0;
		for (Object o: this) {
			a[i++] = o;
		}
		return a;
	}
	
	public <X> X[] toArray(X[] a) {
		int size = size();
		if (size <= a.length) {
			int i = 0;
			for (T o: this) 
				a[i++] = (X)o;
			if (size < a.length)
				a[i] = null;
			return a;
		}
		a = (X[])new Object[size];
		int i = 0;
		for (T o: this) {
			a[i++] = (X)o;
		}
		return a;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder("{|");
		for (T t: this) {
			str = str.append(t + ", ");
		}
		if (!str.toString().equals("{|"))
			str = str.delete(str.length() - 2, str.length());
		str.append("|}");
		return str.toString();
	}
	
}

class TreeBagIterator<T extends Comparable<T>> implements Iterator<T> {
	private int _mult = 0;
	private T _key = null;
	private Iterator<T> _keyIterator;
	private Bag<T> _bag;
	
	public TreeBagIterator(Bag<T> bag) {
		_bag = bag;
		_keyIterator = bag.uniqueElements().iterator();
	}
	
	public boolean hasNext() {
		if (_keyIterator.hasNext() && _mult == 0)
			return true;
		if (!_keyIterator.hasNext() && _mult > 0)
			return true;
		if (_keyIterator.hasNext() && _mult > 0)
			return true;
		return false;
	}
	
	public T next() {
		if (_keyIterator.hasNext() && _mult == 0) {
			_key = _keyIterator.next();
			_mult = _bag.multiplicity(_key) - 1;
			return _key;
		}
		if (!_keyIterator.hasNext() && _mult > 0) {
			_mult--;
			return _key;
		}
		if (_keyIterator.hasNext() && _mult > 0) {
			_mult--;
			return _key;
		}
		throw new NoSuchElementException();
	}
	
	public void remove() {
		if (_mult == 0)
			_bag.getMap().remove(_key);
		else
			_bag.getMap().put(_key, _mult);
	}
	
}