package jrelcal.sets;

import java.util.Iterator;

import jrelcal.Pair;

public abstract class AbstractRelation<S extends Comparable<S>, T extends Comparable<T>>
    implements
        Relation<S, T> {

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
}
