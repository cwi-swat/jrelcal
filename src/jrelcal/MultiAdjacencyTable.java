/*
 * Created on Jan 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jrelcal;


/**
 * @author storm
 * @class jrelcal.MultiAdjacencyTable
 */
public class MultiAdjacencyTable {
	private VertexBag[] _table;

	public MultiAdjacencyTable(int bound, OrderedBag<Pair<Integer,Integer>> edges) {
		this(bound);
		for (Pair<Integer, Integer> edge : edges) {
			addToImage(edge.getFirst(), edge.getSecond());
		}
	}

	public int cardinality() {
		int card = 0;
		for (int i = 0; i < bound(); i++) 
			card += getImage(i).cardinality();
		return card;
	}
	
	public MultiAdjacencyTable(int bound) {
		initializeTable(bound);
	}

	public MultiAdjacencyTable union(MultiAdjacencyTable table) {
		return additiveUnion(table);
	}

	public MultiAdjacencyTable additiveUnion(MultiAdjacencyTable table) {
		MultiAdjacencyTable newTable = copy();
		for (int i = 0; i < newTable.bound(); i++) {
			newTable.getImage(i).addAllAdditive(table.getImage(i));
		}
		return newTable;
	}
	
	public MultiAdjacencyTable maximalUnion(MultiAdjacencyTable table) {
		MultiAdjacencyTable newTable = copy();
		for (int i = 0; i < newTable.bound(); i++) {
			newTable.getImage(i).addAllMaximally(table.getImage(i));
		}
		return newTable;
	}


	public MultiAdjacencyTable intersection(MultiAdjacencyTable table) {
		MultiAdjacencyTable newTable = copy();
		for (int i = 0; i < newTable.bound(); i++) {
			newTable.getImage(i).retainAll(table.getImage(i));
		}
		return newTable;
	}

	public MultiAdjacencyTable difference(MultiAdjacencyTable table) {
		MultiAdjacencyTable newTable = copy();
		for (int i = 0; i < newTable.bound(); i++) {
			newTable.getImage(i).removeAll(table.getImage(i));
		}
		return newTable;
	}

	public MultiAdjacencyTable complement(int bound) {
		MultiAdjacencyTable newTable = copy();
		for (int i = 0; i < newTable.bound(); i++) {
			VertexBag image = newTable.getImage(i);
			newTable.setImage(i, createIntBag(bound));
			for (Integer vertex : image) {
				newTable.getImage(i).remove(vertex);
			}
		}
		return newTable;
	}

	public MultiAdjacencyTable compose(MultiAdjacencyTable table) {
		//System.out.println("Composing " + this + "\nwith\n" + table);
		MultiAdjacencyTable newTable = copy();
		for (int i = 0; i < bound(); i++) {
			for (int j = 0; j < table.bound(); j++) {
				if (getImage(i).contains(j))
					newTable.remap(i, j, table.getImage(j));				
			}
		}
		//System.out.println("... is " + newTable);
		return newTable;
	}

	public MultiAdjacencyTable composeNTimes(int n) {
		assert(n > 0);
		if (n == 1)
			return this;
		return compose(composeNTimes(n - 1));
	}

	public MultiAdjacencyTable reflexiveClosure(int bound) {
		return additiveUnion(identityGraph(bound));
	}

	public MultiAdjacencyTable symmetricClosure(int bound) {
		return additiveUnion(inverse(bound));
	}

	public MultiAdjacencyTable transitiveClosure() {
		MultiAdjacencyTable newTable = this;
		MultiAdjacencyTable compositionTable = this;
		for (int i = 1; i < cardinality(); i++) {
			compositionTable = compositionTable.compose(this);
			newTable = newTable.additiveUnion(compositionTable);
		}
		return newTable;
		//return leastFixPointUnionCompose(this, this);
	}

	private static MultiAdjacencyTable leastFixPointUnionCompose(MultiAdjacencyTable table, MultiAdjacencyTable fixed) {
		MultiAdjacencyTable newTable = unionCompose(table, fixed);
		if (newTable.equals(table))
			return table;
		return leastFixPointUnionCompose(newTable, fixed);
	}
	
	private static MultiAdjacencyTable unionCompose(MultiAdjacencyTable table, MultiAdjacencyTable fixed) {
		return table.additiveUnion(table.compose(fixed));
	}

	public static MultiAdjacencyTable emptyGraph(int bound) {
		return new MultiAdjacencyTable(bound);
	}

	public static MultiAdjacencyTable identityGraph(int bound) {
		MultiAdjacencyTable table = new MultiAdjacencyTable(bound);
		for (int i = 0; i < bound; i++) {
			table.getImage(i).add(i);
		}
		return table;
	}

	public static MultiAdjacencyTable totalGraph(int bound, VertexBag from, VertexBag to) {
		MultiAdjacencyTable table = new MultiAdjacencyTable(bound);
		for (int i = 0; i < bound; i++) {
			for (int j = 0; j < from.multiplicity(i); j++)
				for (Integer k: to)
					table.addToImage(i, k);
		}
		return table;
	}

	public MultiAdjacencyTable reflexiveTransitiveClosure(int bound) {
		return transitiveClosure().additiveUnion(identityGraph(bound));
	}

	public OrderedBag<Pair<Integer,Integer>> getEdges() {
		OrderedBag<Pair<Integer,Integer>> edges = new OrderedBag<Pair<Integer,Integer>>();
		for (int i = 0; i < _table.length; i++) {
			for (Integer vertex : _table[i]) {
				edges.add(new Pair<Integer, Integer>(i, vertex));
			}
		}
		return edges;
	}

	
	public Integer outDegree(int n) {
		return getImage(n).cardinality();
	}

	public Integer[] outDegree() {
		int bound = bound();
		Integer table[] = new Integer[bound];
		for (int i = 0; i < bound; i++) {
			table[i] = getImageCardinality(i);
		}
		return table;
	}

	public Integer[] inDegree(int bound) {
		return inverse(bound).outDegree();
	}

	public MultiAdjacencyTable inverse(int bound) {
		return new MultiAdjacencyTable(bound, reverseEdges());
	}

	private OrderedBag<Pair<Integer,Integer>> reverseEdges() {
		OrderedBag<Pair<Integer,Integer>> edges = new OrderedBag<Pair<Integer,Integer>>();
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

	public VertexBag getImage(int n) {
		return _table[n];
	}

	public void setImage(int n, VertexBag image) {
		_table[n] = image;
	}
	
	public void addToImage(int n, Integer vertex) {
		_table[n].add(vertex);
	}
	
	public int getImageCardinality(int n) {
		return getImage(n).cardinality();
	}

	public int getImageMultiplicity(int i, Integer n) {
		return getImage(i).multiplicity(n);
	}

	/*
	 * Helper functions.
	 */

	private void remap(int i, int j, VertexBag bag) {
		int mult = getImageMultiplicity(i, j);
		for (int k = 0; k < mult; k++) {
			_table[i].remove(j);
			_table[i].addAllAdditive(bag);
		}
	}

	private static VertexBag createIntBag(int bound) {
		VertexBag bag = new VertexBag();
		for (int i = 0; i < bound; i++) {
			bag.add(bound);
		}
		return bag;
	}

	public MultiAdjacencyTable copy() {
		return new MultiAdjacencyTable(_table.length, getEdges());
	}

	private void initializeTable(int bound) {
		_table = new VertexBag[bound];
		for (int i = 0; i < bound; i++) {
			_table[i] = new VertexBag();
		}
	}

	public boolean equals(MultiAdjacencyTable table) {
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
		StringBuilder str = new StringBuilder("{\n");
		for (int i = 0; i < bound(); i++) {
			str = str.append("\t" + i + ": " + _table[i] + "\n");
		}
		str = str.append("}");
		return str.toString();
	}
	
}
