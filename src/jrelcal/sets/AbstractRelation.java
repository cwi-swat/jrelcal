package jrelcal.sets;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jrelcal.Pair;

public abstract class AbstractRelation<S extends Comparable<S>, T extends Comparable<T>>
    implements
        Relation<S, T> {

    // TODO Peter: This compareTo assumes ordered/sorted sets...
    public int compareTo(Relation<S, T> relation) {
        int thisSize = cardinality();
        int thatSize = relation.cardinality();
        if (thisSize < thatSize)
            return -1;
        if (thisSize > thatSize)
            return +1;
        Iterator<Pair<S, T>> thatIterator = relation.iterator();
        for (Iterator<Pair<S, T>> thisIterator = iterator(); thisIterator.hasNext();) {
            Pair<S, T> thisElt = thisIterator.next();
            Pair<S, T> thatElt = thatIterator.next();
            int cmp = thisElt.compareTo(thatElt);
            if (cmp != 0)
                return cmp;
        }
        return 0;
    }

    public static <T extends Comparable<T>> Relation<T, T> transitiveClosure(Relation<T, T> rel) {
        if (rel instanceof AdjacencyTableRelation)
            return AdjacencyTableRelation.transitiveClosure((AdjacencyTableRelation<T, T>)rel);
        else if (rel instanceof PairSetRelation)
            return PairSetRelation.transitiveClosure((PairSetRelation<T, T>)rel);
        else {
            //TODO default implementation using asPairs();
            return null;
        }
    }

    public static <T extends Comparable<T>> Relation<T, T> slice(OrderedSet<T> seeds,
        Relation<T, T> rel, OrderedSet<T> stop) {
        Relation<T, T> result = new PairSetRelation<T, T>();
        OrderedSet<T> liveSeeds = new OrderedSet<T>();
        while (seeds.difference(stop).size() != 0) {
            liveSeeds = seeds.difference(stop);
            result = rel.domainRestriction(liveSeeds).union(result);
            seeds = result.range();
            stop = stop.union(liveSeeds);
        }
        return result;
    }

    private static <T extends Comparable<T>> boolean everySetEmpty(
        Map<T, OrderedSet<T>> seedStopList) {
        boolean result = true;
        for (OrderedSet<T> valueSet : seedStopList.values())
            result = result && valueSet.size() == 0;
        return result;
    }

    private static <T extends Comparable<T>> boolean someSetNotEqualPrevious(
        Map<T, OrderedSet<T>> done, Map<T, OrderedSet<T>> previous) {
        boolean result = false;
        for (T seed : done.keySet()) {
            if (!(done.get(seed).equals(previous.get(seed))))
                return true;
        }
        return result;
    }

    /*
     * Return pair (X,Y) with node X from seeds and node Y from stop if there is a path from X to Y.
     * (Instead of returning the complete subgraph)
     */
    public static <T extends Comparable<T>> Relation<T, T> lazySlice(OrderedSet<T> seeds,
        Relation<T, T> rel, OrderedSet<T> stops) {
        // For every seed store its outgoing neighbours
        Map<T, OrderedSet<T>> seedWorkList = new HashMap<T, OrderedSet<T>>();
        Map<T, OrderedSet<T>> seedStopList = new HashMap<T, OrderedSet<T>>();
        Map<T, OrderedSet<T>> seedDoneList = new HashMap<T, OrderedSet<T>>();
        Map<T, OrderedSet<T>> seedPrevDoneList = new HashMap<T, OrderedSet<T>>();
        for (T seed : seeds) {
            seedWorkList.put(seed, rel.rightSection(seed));

            seedStopList.put(seed, stops);

            seedDoneList.put(seed, new OrderedSet<T>(seed));

            seedPrevDoneList.put(seed, new OrderedSet<T>());
        }

        Relation<T, T> result = new PairSetRelation<T, T>();
        OrderedSet<T> doneSeeds = new OrderedSet<T>();

        // Or doneSeeds verandert niet meer, of voor elke seed zijn de stop list leeg.
        // of geen van de doneSeeds lists verandert meer
        while (!everySetEmpty(seedStopList)
            && someSetNotEqualPrevious(seedDoneList, seedPrevDoneList)) {
            for (T s : seedWorkList.keySet()) {
                seedPrevDoneList.put(s, seedDoneList.get(s));
                OrderedSet<T> neighbours = seedWorkList.get(s);
                doneSeeds = doneSeeds.union(seedWorkList.get(s));
                OrderedSet<T> temp = seedDoneList.get(s);
                OrderedSet<T> unionNew = temp.union(seedWorkList.get(s));
                seedDoneList.put(s, unionNew);
                OrderedSet<T> stopsForSeed = seedStopList.get(s);
                OrderedSet<T> common = stopsForSeed.intersection(neighbours);
                seedStopList.put(s, stopsForSeed.difference(common));
                for (T t : common)
                    result.add(new Pair<T, T>(s, t));
                seedWorkList.put(s, rel.domainRestriction(neighbours).range());
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return this.asPairs().toString();
    }
}
