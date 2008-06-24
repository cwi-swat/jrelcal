package jrelcal.sets;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jrelcal.Pair;

public abstract class AbstractRelation<S extends Comparable<S>, T extends Comparable<T>>
    implements
        Relation<S, T> {

    // This compareTo assumes ordered/sorted sets...
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

    /*
     * (STATIC) GRAPH OPERATIONS
     * 
     * Operations on graph require relations to be of type <T,T>. This can be enforced
     * using generics, but this requires static definition of this operations.
     * 
     * An alternative would be to check the domain and range for type equality at runtime.
     * 
     */
            
    public static <T extends Comparable<T>> Relation<T, T> reflexiveTransitiveClosure(
        Relation<T, T> rel) {
        if (rel instanceof AdjacencyTableRelation)
            return AdjacencyTableRelation
                .reflexiveTransitiveClosure((AdjacencyTableRelation<T, T>)rel);
        else if (rel instanceof PairSetRelation)
            return PairSetRelation.reflexiveTransitiveClosure((PairSetRelation<T, T>)rel);
        else {
            //TODO default implementation using asPairs();
            return null;
        }
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
        Relation<T, T> rel, OrderedSet<T> stops) {
        Relation<T, T> result = new PairSetRelation<T, T>();
        OrderedSet<T> liveSeeds = new OrderedSet<T>();
        while (seeds.difference(stops).size() != 0) {
            liveSeeds = seeds.difference(stops);
            result = rel.domainRestriction(liveSeeds).union(result);
            seeds = result.range();
            stops = stops.union(liveSeeds);
        }
        return result;
    }

    public static <T extends Comparable<T>> Relation<T, T> reach(OrderedSet<T> seeds,
        Relation<T, T> rel, OrderedSet<T> stops) {
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
        while (!everySetEmpty(seedStopList) && !seedDoneList.equals(seedPrevDoneList)) {
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

    public static <T extends Comparable<T>> OrderedSet<T> carrier(Relation<T, T> rel) {
        return rel.domain().union(rel.range());
    }
    
    /*
     * Naive implementations of slice and reach using Transitive Closure (TC). These
     * are added for performance comparisons and consistency tests.
     */    
    public static <T extends Comparable<T>> Relation<T, T> sliceTC(OrderedSet<T> seeds,
        Relation<T, T> rel, OrderedSet<T> stops) {
        if (stops.size() > 0)
            return rel.domainRestriction(carrier(transitiveClosure(rel).domainRestriction(
                seeds).rangeRestriction(stops)));
        else
            return rel.domainRestriction(carrier(transitiveClosure(rel).domainRestriction(
                seeds)));
    }

    public static <T extends Comparable<T>> Relation<T, T> reachTC(OrderedSet<T> seeds,
        Relation<T, T> rel, OrderedSet<T> stops) {
        Relation<T, T> result = transitiveClosure(rel).domainRestriction(seeds);
        result = result.rangeRestriction(stops);
        return result;
    }
    
    private static <T extends Comparable<T>> boolean everySetEmpty(
        Map<T, OrderedSet<T>> seedStopList) {
        boolean result = true;
        for (OrderedSet<T> valueSet : seedStopList.values())
            result = result && valueSet.size() == 0;
        return result;
    }

    public static <R extends Comparable<R>, Q extends Comparable<Q>, S extends Comparable<S>> Relation<Q, S> reachCompose(
        Relation<Q, R> a, Relation<R, R> r, Relation<R, S> b) {
        return a.compose(AbstractRelation.reach(a.range(), r, b.domain())).compose(b);
    }

    @Override
    public String toString() {
        return this.asPairs().toString();
    }
}
