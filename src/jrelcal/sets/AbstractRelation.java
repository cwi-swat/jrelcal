package jrelcal.sets;

import java.util.Iterator;

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
}
