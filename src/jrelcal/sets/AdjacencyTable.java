package jrelcal.sets;

import jrelcal.Pair;

public class AdjacencyTable {
	private VertexSet[] _table;

	public AdjacencyTable(int bound, Set<Pair<Integer,Integer>> edges) {
		this(bound);
		for (Pair<Integer, Integer> edge : edges) {
			_table[edge.getFirst()].add(edge.getSecond());
		}
	}

	public AdjacencyTable(int bound) {
		initializeTable(bound);
	}

	public AdjacencyTable union(AdjacencyTable table) {
		AdjacencyTable newTable = copy();
		for (int i = 0; i < newTable.bound(); i++) {
			newTable.getImage(i).addAll(table.getImage(i));
		}
		return newTable;
	}

	public AdjacencyTable intersection(AdjacencyTable table) {
		AdjacencyTable newTable = copy();
		for (int i = 0; i < newTable.bound(); i++) {
			newTable.getImage(i).retainAll(table.getImage(i));
		}
		return newTable;
	}

	public AdjacencyTable difference(AdjacencyTable table) {
		AdjacencyTable newTable = copy();
		for (int i = 0; i < newTable.bound(); i++) {
			newTable.getImage(i).removeAll(table.getImage(i));
		}
		return newTable;
	}

	public AdjacencyTable complement(int bound) {
		AdjacencyTable newTable = copy();
		for (int i = 0; i < newTable.bound(); i++) {
			VertexSet image = newTable.getImage(i);
			newTable.setImage(i, createIntSet(bound));
			for (Integer vertex : image) {
				newTable.getImage(i).remove(vertex);
			}
		}
		return newTable;
	}

	public AdjacencyTable compose(AdjacencyTable table) {
		AdjacencyTable newTable = copy();
		for (int i = 0; i < bound(); i++) {
			for (int j = 0; j < table.bound(); j++) {
				if (getImage(i).contains(j))
					newTable.remap(i, j, table.getImage(j));				
			}
		}
		return newTable;
	}

	public AdjacencyTable composeNTimes(int n) {
		assert(n > 0);
		if (n == 1)
			return this;
		return compose(composeNTimes(n - 1));
	}

	public AdjacencyTable reflexiveClosure(int bound) {
		return union(identityGraph(bound));
	}

	public AdjacencyTable symmetricClosure(int bound) {
		return union(inverse(bound));
	}

	public AdjacencyTable transitiveClosure() {
		return leastFixPointUnionCompose(this, this);
	}

	private static AdjacencyTable leastFixPointUnionCompose(AdjacencyTable table, AdjacencyTable fixed) {
		AdjacencyTable newTable = unionCompose(table, fixed);
		if (newTable.equals(table))
			return table;
		return leastFixPointUnionCompose(newTable, fixed);
	}
	
	private static AdjacencyTable unionCompose(AdjacencyTable table, AdjacencyTable fixed) {
		return table.union(table.compose(fixed));
	}

	public static AdjacencyTable emptyGraph(int bound) {
		return new AdjacencyTable(bound);
	}

	public static AdjacencyTable identityGraph(int bound) {
		AdjacencyTable table = new AdjacencyTable(bound);
		for (int i = 0; i < bound; i++) {
			table.getImage(i).add(i);
		}
		return table;
	}

	public static AdjacencyTable totalGraph(VertexSet from,  VertexSet to) {
		int bound = from.size();
		AdjacencyTable table = new AdjacencyTable(bound);
		for (int i = 0; i < bound; i++) {
			table.setImage(i, to.copy());
		}
		return table;
	}

	public AdjacencyTable reflexiveTransitiveClosure(int bound) {
		return transitiveClosure().union(identityGraph(bound));
	}

	public Set<Pair<Integer,Integer>> getEdges() {
		Set<Pair<Integer,Integer>> edges = new Set<Pair<Integer,Integer>>();
		for (int i = 0; i < _table.length; i++) {
			for (Integer vertex : _table[i]) {
				edges.add(new Pair<Integer, Integer>(i, vertex));
			}
		}
		return edges;
	}

	public Integer outDegree(int n) {
		return _table[n].size();
	}

	public Integer[] outDegree() {
		Integer table[] = new Integer[_table.length];
		for (int i = 0; i < table.length; i++) {
			table[i] = _table[i].size();
		}
		return table;
	}

	public Integer[] inDegree(int bound) {
		return inverse(bound).outDegree();
	}

	public AdjacencyTable inverse(int bound) {
		return new AdjacencyTable(bound, reverseEdges());
	}

	private Set<Pair<Integer,Integer>> reverseEdges() {
		Set<Pair<Integer,Integer>> edges = new Set<Pair<Integer,Integer>>();
		for (Pair<Integer, Integer> edge : getEdges()) {
			edges.add(new Pair<Integer, Integer>(edge.getSecond(), edge
					.getFirst()));
		}
		return edges;
	}

	/*
	 * Accessor functions
	 */

	public int bound() {
		return _table.length;
	}

	public VertexSet getImage(int n) {
		return _table[n];
	}

	public void setImage(int n, VertexSet image) {
		_table[n] = image;
	}

	public void addToImage(int n, Integer vertex) {
		_table[n].add(vertex);
	}
	
	/*
	 * Helper functions.
	 */

	private void remap(int i, int j, VertexSet set) {
		_table[i].remove(j);
		_table[i].addAll(set);
	}

	private static VertexSet createIntSet(int bound) {
		VertexSet set = new VertexSet();
		for (int i = 0; i < bound; i++) {
			set.add(bound);
		}
		return set;
	}

	public AdjacencyTable copy() {
		return new AdjacencyTable(_table.length, getEdges());
	}

	private void initializeTable(int bound) {
		_table = new VertexSet[bound];
		for (int i = 0; i < bound; i++) {
			_table[i] = new VertexSet();
		}
	}

	public boolean equals(AdjacencyTable table) {
		int bound = bound();

		if (bound != table.bound())
			return false;

		for (int i = 0; i < bound; i++) {
			if (!getImage(i).equals(table.getImage(i)))
				return false;
		}

		return true;
	}

	public String toString() {
		return getEdges().toString();
	}
	
}
