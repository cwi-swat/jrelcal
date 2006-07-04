/*
 * Created on Jan 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jrelcal.bags;

import jrelcal.Pair;
import jrelcal.sets.IndexedSet;

/**
 * @author storm
 * @class jrelcal.ImmutableMultiRelation
 */
public class MultiRelation<S extends Comparable<S>, T extends Comparable<T>> {
	private IndexedSet<S> _fromSet;

	private IndexedSet<T> _toSet;

	private MultiAdjacencyTable _table;

	public void initialize(OrderedBag<Pair<S, T>> pairs) {
		initialize(inferFromSet(pairs), inferToSet(pairs), pairs);
	}
	
	public void initialize(IndexedSet<S> domain, IndexedSet<T> range, OrderedBag<Pair<S, T>> pairs) {
		initialize(domain, range, new MultiAdjacencyTable(domain.size(), pairsToEdges(
				domain, range, pairs)));
	}
	
	protected void initialize(IndexedSet<S> domain, IndexedSet<T> range,
			MultiAdjacencyTable table) {
		_fromSet = domain;
		_toSet = range;
		_table = table;		
	}
	
	public void addAdditive(Pair<S,T> pair) {
		initialize((new OrderedBag<Pair<S,T>>(pair)).additiveUnion(asPairs()));
	}

	public void addMaximally(Pair<S,T> pair) {
		initialize((new OrderedBag<Pair<S,T>>(pair)).maximalUnion(asPairs()));
	}

	public MultiRelation() {
		initialize(new IndexedSet<S>(), new IndexedSet<T>(), new OrderedBag<Pair<S,T>>());
	}
	
	public MultiRelation(OrderedBag<Pair<S, T>> pairs) {
		initialize(inferFromSet(pairs), inferToSet(pairs), pairs);
	}

	public MultiRelation(IndexedSet<S> domain, IndexedSet<T> range,
			OrderedBag<Pair<S, T>> pairs) {
		initialize(domain, range, new MultiAdjacencyTable(domain.size(),
				pairsToEdges(domain, range, pairs)));
	}

	protected MultiRelation(IndexedSet<S> domain, IndexedSet<T> range,
			MultiAdjacencyTable table) {
		initialize(domain, range, table);
	}

	public OrderedBag<Pair<S, T>> asPairs() {
		return asPairs(getFromSet(), getToSet());
	}

	public OrderedBag<Pair<S, T>> asPairs(IndexedSet<S> fromSet,
			IndexedSet<T> toSet) {
		OrderedBag<Pair<S, T>> pairs = new OrderedBag<Pair<S, T>>();
		for (Pair<Integer, Integer> edge : getTable().getEdges()) {
			pairs.add(new Pair<S, T>(fromSet.elementAt(edge.getFirst()), toSet
					.elementAt(edge.getSecond())));
		}
		return pairs;
	}

	public MultiRelation<T, S> inverse() {
		return new MultiRelation<T, S>(getToSet(), getFromSet(),
				getTable().inverse(getToSet().size()));
	}

	public OrderedBag<S> domain() {
		OrderedBag<S> bag = new OrderedBag<S>();
		for (int i = 0; i < getTable().bound(); i++) {
			for (int j = 0; j < outDegree(i); j++)
				bag.add(getFromSet().elementAt(i));
		}
		return bag;
	}

	public OrderedBag<T> range() {
		OrderedBag<T> bag = new OrderedBag<T>();
		Integer inDegree[] = getTable().inDegree(getTable().bound());
		for (int i = 0; i < getTable().bound(); i++) {
			for (int j = 0; j < inDegree[i]; j++)
				bag.add(getToSet().elementAt(i));
		}
		return bag;
	}

	public MultiRelation<S, T> pruneWithDomainAndRange() {
		return new MultiRelation<S, T>(asPairs());
	}

	public MultiRelation<S, T> additiveUnion(
			MultiRelation<S, T> relation) {
		if (getFromSet().equals(relation.getFromSet())
				&& getToSet().equals(relation.getToSet()))
			return new MultiRelation<S, T>(getFromSet(), getToSet(),
					getTable().additiveUnion(relation.getTable()));
		return new MultiRelation<S, T>(getFromSet().union(
				relation.getFromSet()), getToSet().union(relation.getToSet()),
				asPairs().additiveUnion(relation.asPairs()));
	}

	public MultiRelation<S, T> maximalUnion(
			MultiRelation<S, T> relation) {
		if (getFromSet().equals(relation.getFromSet())
				&& getToSet().equals(relation.getToSet()))
			return new MultiRelation<S, T>(getFromSet(), getToSet(),
					getTable().maximalUnion(relation.getTable()));
		return new MultiRelation<S, T>(getFromSet().union(
				relation.getFromSet()), getToSet().union(relation.getToSet()),
				asPairs().maximalUnion(relation.asPairs()));
	}

	public MultiRelation<S, T> intersection(
			MultiRelation<S, T> relation) {
		if (getFromSet().equals(relation.getFromSet())
				&& getToSet().equals(relation.getToSet()))
			return new MultiRelation<S, T>(getFromSet(), getToSet(),
					getTable().intersection(relation.getTable()));
		IndexedSet<S> fromSet = getFromSet()
				.intersection(relation.getFromSet());
		IndexedSet<T> toSet = getToSet().intersection(relation.getToSet());
		return new MultiRelation<S, T>(fromSet, toSet, asPairs()
				.intersection(relation.asPairs()));
	}

	public MultiRelation<S, T> difference(
			MultiRelation<S, T> relation) {
		if (getFromSet().equals(relation.getFromSet())
				&& getToSet().equals(relation.getToSet()))
			return new MultiRelation<S, T>(getFromSet(), getToSet(),
					getTable().difference(relation.getTable()));
		return new MultiRelation<S, T>(getFromSet().difference(
				relation.getFromSet()), getToSet().difference(
				relation.getToSet()), asPairs().difference(relation.asPairs()));
	}

	public MultiRelation<S, T> complement() {
		return new MultiRelation<S, T>(getFromSet(), getToSet(),
				getTable().complement(getToSet().size()));
	}

	public <U extends Comparable<U>> MultiRelation<S, U> compose(
			MultiRelation<T, U> relation) {
		commensurateFromAndToSets(relation);
		return doCompose(relation);
	}

	private <U extends Comparable<U>> void commensurateFromAndToSets(
			MultiRelation<T, U> relation) {
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

	private <U extends Comparable<U>> MultiRelation<S, U> doCompose(
			MultiRelation<T, U> relation) {
		return new MultiRelation<S, U>(getFromSet(), relation
				.getToSet(), getTable().compose(relation.getTable()));
	}

	protected void updateToSet(IndexedSet<T> newToSet) {
		MultiAdjacencyTable newTable = new MultiAdjacencyTable(getFromSet()
				.size());
		MultiAdjacencyTable oldTable = getTable();
		IndexedSet<T> oldToSet = getToSet();
		for (int i = 0; i < bound(); i++) {
			for (Integer j : oldTable.getImage(i)) {
				newTable.addToImage(i, newToSet.indexOf(oldToSet.elementAt(j)));
			}
		}
		setToSet(newToSet);
		setTable(newTable);
	}

	protected void updateFromSet(IndexedSet<S> newFromSet) {
		MultiAdjacencyTable newTable = new MultiAdjacencyTable(newFromSet
				.size());
		MultiAdjacencyTable oldTable = getTable();
		IndexedSet<S> oldFromSet = getFromSet();
		for (int i = 0; i < bound(); i++) {
			newTable.setImage(newFromSet.indexOf(oldFromSet.elementAt(i)),
					oldTable.getImage(i));
		}
		setFromSet(newFromSet);
		setTable(newTable);
	}

	protected int bound() {
		return getTable().bound();
	}

	public MultiRelation<S, T> domainRestriction(OrderedBag<S> bag) {
		MultiAdjacencyTable table = getTable().copy();
		int bound = table.bound();
		VertexBag vertexBag = bag.toVertexBag(getFromSet());
		for (int i = 0; i < bound; i++) {
			if (!vertexBag.contains(i))
				table.setImage(i, new VertexBag());
		}
		return new MultiRelation<S, T>(getFromSet(), getToSet(), table);
	}

	public MultiRelation<S, T> domainExclusion(OrderedBag<S> bag) {
		MultiAdjacencyTable table = getTable().copy();
		int bound = table.bound();
		VertexBag vertexBag = bag.toVertexBag(getFromSet());
		for (int i = 0; i < bound; i++) {
			if (vertexBag.contains(i))
				table.setImage(i, new VertexBag());
		}
		return new MultiRelation<S, T>(getFromSet(), getToSet(), table);
	}

	public MultiRelation<S, T> rangeRestriction(OrderedBag<T> bag) {
		MultiAdjacencyTable table = getTable().copy();
		int bound = table.bound();
		VertexBag vertexBag = bag.toVertexBag(getToSet());
		for (int i = 0; i < bound; i++)
			table.getImage(i).retainAll(vertexBag);
		return new MultiRelation<S, T>(getFromSet(), getToSet(), table);
	}

	public MultiRelation<S, T> rangeExclusion(OrderedBag<T> bag) {
		MultiAdjacencyTable table = getTable().copy();
		int bound = table.bound();
		VertexBag vertexBag = bag.toVertexBag(getToSet());
		for (int i = 0; i < bound; i++)
			table.getImage(i).removeAll(vertexBag);
		return new MultiRelation<S, T>(getFromSet(), getToSet(), table);
	}

	
	public OrderedBag<T> rightSection(S s) {
		OrderedBag<S> bag = new OrderedBag<S>();
		bag.add(s);
		return domainRestriction(bag).range();
	}

	public OrderedBag<S> leftSection(T t) {
		OrderedBag<T> bag = new OrderedBag<T>();
		bag.add(t);
		return rangeRestriction(bag).domain();
	}
	
	public boolean equals(MultiRelation<S,T> relation) {
		// TODO: Implement this using its own compareTo
		return asPairs().equals(relation.asPairs());
	}

	public boolean equals(Object o) {
		return equals((MultiRelation<S,T>)o);
	}

	/*
	 * Static methods to mimic factory behaviour.
	 */

	public static <U extends Comparable<U>, V extends Comparable<V>> MultiRelation<U, V> emptyRelation(
			OrderedBag<U> from, OrderedBag<V> to) {
		return new MultiRelation<U, V>(from.toIndexedSet(), to.toIndexedSet(),
				MultiAdjacencyTable.emptyGraph(from.size()));
	}

	public static <U extends Comparable<U>, V extends Comparable<V>> MultiRelation<U, V> cartesianProduct(
			OrderedBag<U> from, OrderedBag<V> to) {
		IndexedSet<U> fromSet = from.toIndexedSet();
		IndexedSet<V> toSet = to.toIndexedSet();
		VertexBag fromBag = from.toVertexBag(fromSet);
		VertexBag toBag = to.toVertexBag(toSet);
		return new MultiRelation<U, V>(fromSet, toSet,
				MultiAdjacencyTable.totalGraph(fromSet.size(), fromBag, toBag));
	}

	/*
	 * Protected setters and getters.
	 */

	protected IndexedSet<S> getFromSet() {
		return _fromSet;
	}

	protected void setFromSet(IndexedSet<S> set) {
		_fromSet = set;
	}

	protected IndexedSet<T> getToSet() {
		return _toSet;
	}

	protected void setToSet(IndexedSet<T> set) {
		_toSet = set;
	}

	protected MultiAdjacencyTable getTable() {
		return _table;
	}

	protected void setTable(MultiAdjacencyTable table) {
		_table = table;
	}

	/*
	 * Private helper routines.
	 */

	protected static <S extends Comparable<S>, T extends Comparable<T>> IndexedSet<S> inferFromSet(
			OrderedBag<Pair<S, T>> pairs) {
		IndexedSet<S> fromSet = new IndexedSet<S>();
		for (Pair<S, T> pair : pairs) {
			fromSet.add(pair.getFirst());
		}
		return fromSet;
	}

	protected static <S extends Comparable<S>, T extends Comparable<T>> IndexedSet<T> inferToSet(
			OrderedBag<Pair<S, T>> pairs) {
		IndexedSet<T> toSet = new IndexedSet<T>();
		for (Pair<S, T> pair : pairs) {
			toSet.add(pair.getSecond());
		}
		return toSet;
	}

	private static <S extends Comparable<S>, T extends Comparable<T>> OrderedBag<Pair<Integer, Integer>> pairsToEdges(
			IndexedSet<S> fromSet, IndexedSet<T> toSet,
			OrderedBag<Pair<S, T>> pairs) {
		OrderedBag<Pair<Integer, Integer>> edges = new OrderedBag<Pair<Integer, Integer>>();
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
	 * Interface implementation of ImmutableMultiRelation
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

	
	///////////////////////////////////////////////////////////////////////
	
	
	public static <T extends Comparable<T>> OrderedBag<T> carrier(MultiRelation<T,T> relation) {
		return relation.domain().maximalUnion(relation.range());
	}
	
	public static <T extends Comparable<T>> MultiRelation<T,T> pruneWithCarrier(MultiRelation<T,T> relation) {
		return new MultiRelation<T,T>(relation.asPairs());
	}
		
	public static <T extends Comparable<T>> MultiRelation<T,T> reflexiveClosure(MultiRelation<T,T> relation) {
		return new MultiRelation<T,T>(relation.getFromSet(), relation.getToSet(),
				relation.getTable().additiveUnion(MultiAdjacencyTable.identityGraph(relation.getFromSet().size())));
	}
	
	public static <T extends Comparable<T>> MultiRelation<T,T> symmetricClosure(MultiRelation<T,T> relation) {
		return new MultiRelation<T,T>(relation.getFromSet(), relation.getToSet(),
				relation.getTable().additiveUnion(relation.getTable().inverse(relation.getFromSet().size())));
	}
	
	public static <T extends Comparable<T>> MultiRelation<T,T> transitiveClosure(MultiRelation<T,T> relation) {
		return new MultiRelation<T,T>(relation.getFromSet(), relation.getToSet(),
				relation.getTable().transitiveClosure());
	}
	
	public static <T extends Comparable<T>> MultiRelation<T,T> reflexiveTransitiveClosure(MultiRelation<T,T> relation) {
		IndexedSet<T> set = relation.getFromSet();
		return new MultiRelation<T,T>(set, relation.getToSet(), relation.getTable().reflexiveTransitiveClosure(set.size()));
	}
	
	public static <T extends Comparable<T>> MultiRelation<T,T> carrierRestriction(MultiRelation<T,T> relation, OrderedBag<T> bag) {
		IndexedSet<T> fromSet = relation.getFromSet();
		MultiAdjacencyTable table = relation.getTable().copy();
		int bound = table.bound();
		VertexBag vertexBag= bag.toVertexBag(fromSet);
		for (int i = 0; i < bound; i++) {
			if (!bag.contains(fromSet.elementAt(i)))
				table.setImage(i, new VertexBag());
			else 
				table.getImage(i).retainAll(vertexBag);
		}
		return new MultiRelation<T,T>(fromSet, relation.getToSet(), table);
	}
	
	public static <T extends Comparable<T>> OrderedBag<T> sources(MultiRelation<T,T> relation) {
		IndexedSet<T> fromSet = relation.getFromSet();
		int n = fromSet.size();
		MultiAdjacencyTable table = relation.getTable();
		Integer inDegree[] = table.inDegree(n);
		Integer outDegree[] = table.outDegree();
		OrderedBag<T> bag = new OrderedBag<T>();
		int i = 0;
		for (T t: fromSet) {
			if (inDegree[i] == 0)
				for (int j = 0; j < outDegree[i]; j++)
					bag.add(t);
			i++;
		}
		return bag;
	}

	public static <T extends Comparable<T>> OrderedBag<T> sinks(MultiRelation<T,T> relation) {
		IndexedSet<T> fromSet = relation.getFromSet();
		int n = fromSet.size();
		MultiAdjacencyTable table = relation.getTable();
		Integer inDegree[] = table.inDegree(n);
		Integer outDegree[] = table.outDegree();
		OrderedBag<T> bag = new OrderedBag<T>();
		int i = 0;
		for (T t: fromSet) {
			if (outDegree[i] == 0)
				for (int j = 0; j < inDegree[i]; j++)
					bag.add(t);
			i++;
		}
		return bag;
	}

	
	public static <T extends Comparable<T>> MultiRelation<T,T> identityGraph(OrderedBag<T> bag) {
		IndexedSet<T> set = bag.toIndexedSet();
		return new MultiRelation<T,T>(set, set, MultiAdjacencyTable.identityGraph(set.size()));
	}
	
	public static <T extends Comparable<T>> MultiRelation<T,T> totalGraph(OrderedBag<T> bag) {
		IndexedSet<T> set = bag.toIndexedSet();
		VertexBag vertexBag = bag.toVertexBag();
		return new MultiRelation<T,T>(set, set, 
				MultiAdjacencyTable.totalGraph(set.size(), vertexBag, vertexBag));
	}
	

	
	
}
