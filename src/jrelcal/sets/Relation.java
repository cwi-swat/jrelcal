package jrelcal.sets;

import java.util.Iterator;

import jrelcal.Pair;


public class Relation<S extends Comparable<S>, T extends Comparable<T>> implements Iterable<Pair<S,T>> {
	private IndexedSet<S> _fromSet;
	private IndexedSet<T> _toSet;
	private AdjacencyTable _table;

	public void initialize(Set<Pair<S, T>> pairs) {
		initialize(inferFromSet(pairs), inferToSet(pairs), pairs);
	}
	
	public void initialize(IndexedSet<S> domain, IndexedSet<T> range, Set<Pair<S, T>> pairs) {
		initialize(domain, range, new AdjacencyTable(domain.size(), pairsToEdges(
				domain, range, pairs)));
	}
	
	protected void initialize(IndexedSet<S> domain, IndexedSet<T> range,
			AdjacencyTable table) {
		_fromSet = domain;
		_toSet = range;
		_table = table;		
	}
	
	public void add(Pair<S,T> pair) {
		initialize((new Set<Pair<S,T>>(pair)).union(asPairs()));
	}
	
	public Relation() {
		initialize(new IndexedSet<S>(), new IndexedSet<T>(), new Set<Pair<S,T>>());
	}
	
	public Relation(Set<Pair<S, T>> pairs) {
		initialize(pairs);
	}

	public Relation(IndexedSet<S> domain, IndexedSet<T> range,
			Set<Pair<S, T>> pairs) {
		initialize(domain, range, pairs);
	}

	protected Relation(IndexedSet<S> domain, IndexedSet<T> range,
			AdjacencyTable table) {
		initialize(domain, range, table);
	}

	@SuppressWarnings("hiding")
	protected <S extends Comparable<S>, T extends Comparable<T>> Relation<S,T> newRelation() {
		return new Relation<S,T>();
	}
	
	@SuppressWarnings("hiding")
	protected <S extends Comparable<S>, T extends Comparable<T>> Relation<S,T> newRelation(Set<Pair<S, T>> pairs) {
		return new Relation<S,T>(pairs);
	}

	@SuppressWarnings("hiding")
	protected <S extends Comparable<S>, T extends Comparable<T>> Relation<S,T> newRelation(IndexedSet<S> domain, IndexedSet<T> range,
			Set<Pair<S, T>> pairs) {
		return new Relation<S,T>(domain, range, pairs);
	}

	@SuppressWarnings("hiding")
	protected <S extends Comparable<S>, T extends Comparable<T>> Relation<S,T> newRelation(IndexedSet<S> domain, IndexedSet<T> range,
			AdjacencyTable table) {
		return new Relation<S,T>(domain, range, table);
	}

	
	public Set<Pair<S, T>> asPairs() {
		Set<Pair<S, T>> pairs = new Set<Pair<S, T>>();
		for (Pair<Integer, Integer> edge : getTable().getEdges()) {
			pairs.add(new Pair<S, T>(getFromSet().elementAt(edge.getFirst()),
					getToSet().elementAt(edge.getSecond())));
		}
		return pairs;
	}

	public Relation<T, S> inverse() {
		return new Relation<T, S>(getToSet(), getFromSet(), getTable()
				.inverse(getToSet().size()));
	}

	public Set<S> domain() {
		Set<S> set = new Set<S>();
		for (int i = 0; i < getTable().bound(); i++) {
			if (outDegree(i) > 0)
				set.add(getFromSet().elementAt(i));
		}
		return set;
	}

	public Set<T> range() {
		Set<T> set = new Set<T>();
		int n = getToSet().size();
		Integer inDegree[] = getTable().inDegree(n);
		for (int i = 0; i < n; i++) {
			if (inDegree[i] > 0)
				set.add(getToSet().elementAt(i));
		}
		return set;
	}

	public Relation<S, T> pruneWithDomainAndRange() {
		return new Relation<S, T>(asPairs());
	}

	public Relation<S, T> union(Relation<S, T> relation) {
		if (getFromSet().equals(relation.getFromSet())
				&& getToSet().equals(relation.getToSet()))
			return new Relation<S, T>(getFromSet(), getToSet(), getTable()
					.union(relation.getTable()));
		return new Relation<S, T>(getFromSet()
				.union(relation.getFromSet()), getToSet()
				.union(relation.getToSet()), asPairs()
				.union(relation.asPairs()));
	}

	public Relation<S, T> intersection(Relation<S, T> relation) {
		if (getFromSet().equals(relation.getFromSet())
				&& getToSet().equals(relation.getToSet()))
			return new Relation<S, T>(getFromSet(), getToSet(), getTable()
					.intersection(relation.getTable()));
		return new Relation<S, T>(getFromSet().intersection(relation
				.getFromSet()), getToSet().intersection(relation.getToSet()),
				asPairs().intersection(relation.asPairs()));
	}

	public Relation<S, T> difference(Relation<S, T> relation) {
		if (getFromSet().equals(relation.getFromSet())
				&& getToSet().equals(relation.getToSet()))
			return new Relation<S, T>(getFromSet(), getToSet(), getTable()
					.difference(relation.getTable()));
		return new Relation<S, T>(getFromSet().difference(relation
				.getFromSet()), getToSet().difference(relation.getToSet()),
				asPairs().difference(relation.asPairs()));
	}

	public Relation<S, T> complement() {
		return new Relation<S, T>(getFromSet(), getToSet(), getTable()
				.complement(getToSet().size()));
	}

	public <U extends Comparable<U>> Relation<S, U> compose(
			Relation<T, U> relation) {
		commensurateFromAndToSets(relation);
		return doCompose(relation);
	}
	
	protected <U extends Comparable<U>> void commensurateFromAndToSets(
			Relation<T, U> relation) {
		IndexedSet<T> toSet = getToSet();
		IndexedSet<T> fromSet = relation.getFromSet();
		if (!toSet.equals(fromSet)) {
			// These updates can be destructive
			// since they do not change the relations.
			boolean fromSetIsSubsetOfToSet = toSet.containsAll(fromSet);
			boolean toSetIsSubsetOfFromSet = fromSet.containsAll(toSet);
			if (fromSetIsSubsetOfToSet)
				relation.updateFromSet(toSet);
			if (toSetIsSubsetOfFromSet)
				updateToSet(fromSet);
			if (!fromSetIsSubsetOfToSet && !toSetIsSubsetOfFromSet) {
				IndexedSet<T> set = fromSet.union(toSet);
				relation.updateFromSet(set);
				updateToSet(set);
			}
		}
	}
	
	protected <U extends Comparable<U>> Relation<S, U> doCompose(
			Relation<T, U> relation) {
		return new Relation<S, U>(getFromSet(), relation.getToSet(),
				getTable().compose(relation.getTable()));		
	}
	
	protected void updateToSet(IndexedSet<T> newToSet) {
		AdjacencyTable newTable = new AdjacencyTable(getFromSet().size());
		AdjacencyTable oldTable = getTable(); 
		IndexedSet<T> oldToSet = getToSet();
		for (int i = 0; i < bound(); i++) {
			for (Integer j: oldTable.getImage(i)) {
				newTable.addToImage(i, newToSet.indexOf(oldToSet.elementAt(j)));
			}
		}
		setToSet(newToSet);
		setTable(newTable);
	}
	
	protected void updateFromSet(IndexedSet<S> newFromSet) {
		AdjacencyTable newTable = new AdjacencyTable(newFromSet.size());
		AdjacencyTable oldTable = getTable(); 
		IndexedSet<S> oldFromSet = getFromSet();
		for (int i = 0; i < bound(); i++) {
			newTable.setImage(newFromSet.indexOf(oldFromSet.elementAt(i)), oldTable.getImage(i));
		}
		setFromSet(newFromSet);
		setTable(newTable);
	}
	
	public Relation<S,T> domainRestriction(Set<S> set) {
		AdjacencyTable table = getTable().copy();
		int bound = table.bound();
		for (int i = 0; i < bound; i++) {
			if (!set.contains(getFromSet().elementAt(i)))
				table.setImage(i, new VertexSet());
		}
		return newRelation(getFromSet(), getToSet(), table);
	}

	public Relation<S,T> domainExclusion(Set<T> set) {
		AdjacencyTable table = getTable().copy();
		int bound = table.bound();
		for (int i = 0; i < bound; i++) {
			if (set.contains(getFromSet().elementAt(i)))
				table.setImage(i, new VertexSet());
		}
		return newRelation(getFromSet(), getToSet(), table);
	}

	public Relation<S,T> rangeRestriction(Set<T> set) {
		AdjacencyTable table = getTable().copy();
		int bound = table.bound();
		for (int i = 0; i < bound; i++)
			for (Integer image: table.getImage(i))
				if (!set.contains(getToSet().elementAt(image)))
					table.getImage(i).remove(image);			
		return newRelation(getFromSet(), getToSet(), table);
	}
	
	public Relation<S,T> rangeExclusion(Set<T> set) {
		AdjacencyTable table = getTable().copy();
		int bound = table.bound();
		for (int i = 0; i < bound; i++)
			for (Integer image: table.getImage(i))
				if (set.contains(getToSet().elementAt(image)))
					table.getImage(i).remove(image);			
		return newRelation(getFromSet(), getToSet(), table);
	}

	public Set<T> rightImage(S s) {
		return rightSection(s);
	}

	public Set<S> leftImage(T t) {
		return leftSection(t);
	}

	public Set<T> image(S s) {
		return rightImage(s);
	}

	public Set<T> image(Set<S> set) {
		return rightImage(set);
	}

	public Set<T> rightImage(Set<S> set) {
		Set<T> result = new Set<T>();
		for (S s: set) {
			result = rightImage(s).union(result);
		}
		return result;
	}
	
	public Set<S> leftImage(Set<T> set) {
		Set<S> result = new Set<S>();
		for (T t: set) {
			result = leftImage(t).union(result);
		}
		return result;
	}
	
	public Set<T> rightSection(S s) {
		Set<S> set = new Set<S>();
		set.add(s);
		return domainRestriction(set).range();
	}
	
	public Set<S> leftSection(T t) {
		Set<T> set = new Set<T>();
		set.add(t);
		return rangeRestriction(set).domain();
	}

	public boolean equals(Relation<S,T> relation) {
		// TODO: Implement this using its own compareTo
		return asPairs().equals(relation.asPairs());
	}

	public boolean equals(Object o) {
		return equals((Relation<S,T>)o);
	}
	
	/*
	 * Static methods to mimic factory behaviour.
	 */

	public static <U extends Comparable<U>, V extends Comparable<V>> Relation<U, V> emptyRelation(
			Set<U> fromSet, Set<V> toSet) {
		return new Relation<U, V>(fromSet.toIndexedSet(), toSet.toIndexedSet(), AdjacencyTable
				.emptyGraph(fromSet.size()));
	}

	public static <U extends Comparable<U>, V extends Comparable<V>> Relation<U, V> cartesianProduct(
			Set<U> from, Set<V> to) {
		IndexedSet<U> fromSet = from.toIndexedSet();
		IndexedSet<V> toSet = to.toIndexedSet();
		VertexSet fromVertexSet = fromSet.toVertexSet();
		VertexSet toVertexSet = toSet.toVertexSet();		
		return new Relation<U, V>(fromSet, toSet, AdjacencyTable
				.totalGraph(fromVertexSet, toVertexSet));
	}

	/*
	 * Protected setters and getters.
	 */

	protected int bound() {
		return getTable().bound();
	}
	
	protected IndexedSet<S> getFromSet() {
		return _fromSet;
	}

	protected IndexedSet<T> getToSet() {
		return _toSet;
	}

	protected AdjacencyTable getTable() {
		return _table;
	}

	
	protected void setFromSet(IndexedSet<S> set) {
		_fromSet = set;
	}
	
	protected void setToSet(IndexedSet<T> set) {
		_toSet = set;
	}

	
	protected void setTable(AdjacencyTable table) {
		_table = table;
	}

	
	/*
	 * Private helper routines.
	 */

	protected static <S extends Comparable<S>, T extends Comparable<T>> IndexedSet<S> inferFromSet(
			Set<Pair<S, T>> pairs) {
		IndexedSet<S> fromSet = new IndexedSet<S>();
		for (Pair<S, T> pair : pairs) {
			fromSet.add(pair.getFirst());
		}
		return fromSet;
	}

	protected static <S extends Comparable<S>, T extends Comparable<T>> IndexedSet<T> inferToSet(
			Set<Pair<S, T>> pairs) {
		IndexedSet<T> toSet = new IndexedSet<T>();
		for (Pair<S, T> pair : pairs) {
			toSet.add(pair.getSecond());
		}
		return toSet;
	}

	private static <S extends Comparable<S>, T extends Comparable<T>> Set<Pair<Integer,Integer>> pairsToEdges(
			IndexedSet<S> fromSet, IndexedSet<T> toSet,
			Set<Pair<S, T>> pairs) {
		Set<Pair<Integer,Integer>> edges = new Set<Pair<Integer,Integer>>();
		for (Pair<S, T> pair : pairs) {
			edges.add(new Pair<Integer, Integer>(fromSet.indexOf(pair
					.getFirst()), toSet.indexOf(pair.getSecond())));
		}
		return edges;
	}

	private int outDegree(int n) {
		return getTable().outDegree(n);
	}

	
	
	/*
	 * Interface implementation of ImmutableRelation
	 */

	public int cardinality() {
		int card = 0;
		for (int i = 0; i < getTable().bound(); i++) {
			card += getTable().getImage(i).size();
		}
		return card;
	}

	public boolean elementOf(Pair<S, T> element) {
		return false;
	}

	public String toString() {
		return asPairs().toString();
	}

	/////////////////////////////////////////////////////////
	
	
	public static <T extends Comparable<T>> Set<T> carrier(Relation<T,T> relation) {
		return relation.domain().union(relation.range());
	}
	
	public static <T extends Comparable<T>> Relation<T,T> pruneWithCarrier(Relation<T,T> relation) {
		return new Relation<T,T>(relation.asPairs());
	}
	
	/*public ImmutableGraph<T> composeNTimes(int n) {
		assert(n > 0);
		if (n == 1)
			return this;
		return (ImmutableGraph<T>)compose(composeNTimes(n-1));
	}*/
	
	public static <T extends Comparable<T>> Relation<T,T> reflexiveClosure(Relation<T,T> relation) {
		return new Relation<T,T>(relation.getFromSet(), relation.getToSet(), 
				relation.getTable().union(AdjacencyTable.identityGraph(relation.getFromSet().size())));
	}
	
	public static <T extends Comparable<T>> Relation<T,T> symmetricClosure(Relation<T,T> relation) {
		return new Relation<T,T>(relation.getFromSet(), relation.getToSet(), 
				relation.getTable().union(relation.getTable().inverse(relation.getFromSet().size())));
	}
	
	public static <T extends Comparable<T>> Relation<T,T> transitiveClosure(Relation<T,T> relation) {
		return new Relation<T,T>(relation.getFromSet(), relation.getToSet(),
				relation.getTable().transitiveClosure());
	}
	
	public static <T extends Comparable<T>> Relation<T, T> reflexiveTransitiveClosure(Relation<T,T> relation) {
		IndexedSet<T> set = relation.getFromSet();
		return new Relation<T,T>(set, relation.getToSet(), 
				relation.getTable().reflexiveTransitiveClosure(set.size()));
	}
	
	public static <T extends Comparable<T>> Relation<T, T> carrierRestriction(Relation<T,T> relation, Set<T> set) {
		AdjacencyTable table = relation.getTable().copy();
		int bound = table.bound();
		VertexSet vertexSet = set.toVertexSet(relation.getFromSet());
		for (int i = 0; i < bound; i++) {
			if (!vertexSet.contains(i))
				table.setImage(i, new VertexSet());
			else 
				table.getImage(i).retainAll(vertexSet);
		}
		return new Relation<T, T>(relation.getFromSet(), relation.getToSet(), table);
	}
	
	public static <T extends Comparable<T>> Set<T> sources(Relation<T,T> relation) {
		int n = relation.getFromSet().size();
		AdjacencyTable table = relation.getTable();
		Integer inDegree[] = table.inDegree(n);
		Integer outDegree[] = table.outDegree();
		IndexedSet<T> set = new IndexedSet<T>();
		int i = 0;
		for (T t: relation.getFromSet()) {
			if (inDegree[i] == 0 && outDegree[i] > 0)
				set.add(t);
			i++;
		}
		return set;
	}

	public static <T extends Comparable<T>> Set<T> sinks(Relation<T,T> relation) {
		int n = relation.getFromSet().size();
		AdjacencyTable table = relation.getTable();
		Integer inDegree[] = table.inDegree(n);
		Integer outDegree[] = table.outDegree();
		IndexedSet<T> set = new IndexedSet<T>();
		int i = 0;
		for (T t: relation.getFromSet()) {
			if (inDegree[i] > 0 && outDegree[i] == 0)
				set.add(t);
			i++;
		}
		return set;
	}

	public static <T extends Comparable<T>> Set<T> commonDescendants(Relation<T,T> relation, Set<T> set) {
		if (set.isEmpty())
			return carrier(relation);
		T last = set.last(); 
		Set<T> temp = transitiveClosure(relation).rightSection(last);
		return temp.intersection(commonDescendants(relation, (Set<T>)set.headSet(last)));
	}
	
	public static <T extends Comparable<T>> Set<T> commonAncestors(Relation<T,T> relation, Set<T> set) {
		if (set.isEmpty())
			return carrier(relation);
		T last = set.last(); 
		Set<T> temp = transitiveClosure(relation).leftSection(last);
		return temp.intersection(commonAncestors(relation, (Set<T>)set.headSet(last)));
	}
	
	public static <T extends Comparable<T>> Relation<T,T> identityGraph(Set<T> set) {
		IndexedSet<T> fromAndTo = set.toIndexedSet();
		return new Relation<T,T>(fromAndTo, fromAndTo, AdjacencyTable.identityGraph(set.size()));
	}
	
	public static <T extends Comparable<T>> Relation<T,T> totalGraph(Set<T> set) {
		return cartesianProduct(set, set);
	}

	public Iterator<Pair<S, T>> iterator() {
		return asPairs().iterator();
	}

	
	
	
}
