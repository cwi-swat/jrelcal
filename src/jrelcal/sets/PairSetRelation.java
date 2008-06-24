package jrelcal.sets;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.collections15.Predicate;

import jrelcal.Pair;

public class PairSetRelation<S extends Comparable<S>, T extends Comparable<T>>
    extends
        AbstractRelation<S, T> {
    private Set<Pair<S, T>> set = new HashSet<Pair<S, T>>();

    public PairSetRelation() {}

    public PairSetRelation(Relation<S,T> rel) {
        this.set = new HashSet<Pair<S, T>>(rel.asPairs());
    }
    
    public PairSetRelation(Set<Pair<S, T>> set) {
        this.set = new HashSet<Pair<S, T>>(set);
    }

    public void add(Pair<S, T> pair) {
        set.add(pair);
    }
    
    public void add(Set<Pair<S, T>> pairSet) {
        for (Pair<S,T> p: pairSet)
            this.add(p);        
    }

    public OrderedSet<Pair<S, T>> asPairs() {
        return new OrderedSet<Pair<S, T>>(set);
    }

    public int cardinality() {
        return set.size();
    }

    public <U extends Comparable<U>> Relation<S, U> compose(Relation<T, U> relation) {
        Relation<S, U> result = new PairSetRelation<S, U>();
        for (Pair<S, T> p : set) { 
            for (Pair<T, U> p2 : relation) {
                if (p.getSecond().equals(p2.getFirst()))
                    result.add(new Pair<S, U>(p.getFirst(), p2.getSecond()));
            }
        }
        return result;
    }

    public Relation<S, T> difference(Relation<S, T> relation) {
        Set<Pair<S, T>> resultSet = new HashSet<Pair<S, T>>(set);
        resultSet.removeAll(relation.asPairs());
        Relation<S, T> result = new PairSetRelation<S, T>(resultSet);
        return result;
    }

    public OrderedSet<S> domain() {
        OrderedSet<S> result = new OrderedSet<S>();
        for (Pair<S, T> p : set)
            result.add(p.getFirst());
        return result;
    }

    public Relation<S, T> domainExclusion(OrderedSet<S> set) {
        Relation<S, T> result = new PairSetRelation<S, T>();
        for (Pair<S, T> p : this)
            if (!set.contains(p.getFirst()))
                result.add(p);
        return result;
    }

    public Relation<S, T> domainRestriction(OrderedSet<S> set) {
        Relation<S, T> result = new PairSetRelation<S, T>();
        for (Pair<S, T> p : this)
            if (set.contains(p.getFirst()))
                result.add(p);
        return result;
    }
    
    public Relation<S, T> domainRestriction(Predicate<S> p) {
        Relation<S, T> result = new PairSetRelation<S, T>();
        for (Pair<S, T> pair : this) {
            if (p.evaluate(pair.getFirst()))
                result.add(pair);
        }
        return result;
    }

    public boolean equals(Relation<S, T> relation) {
        return asPairs().equals(relation.asPairs());
    }

    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        return equals((Relation<S, T>)o);
    }

    public Relation<S, T> intersection(Relation<S, T> relation) {
        Set<Pair<S, T>> intersection = new HashSet<Pair<S, T>>(set);
        intersection.retainAll(relation.asPairs());
        return new PairSetRelation<S, T>(intersection);
    }

    public Relation<T, S> inverse() {
        Relation<T, S> result = new PairSetRelation<T, S>();
        for (Pair<S, T> p : set)
            result.add(p.swap());
        return result;
    }

    public Iterator<Pair<S, T>> iterator() {
        return set.iterator();
    }

    public OrderedSet<T> range() {
        OrderedSet<T> resultSet = new OrderedSet<T>();
        for (Pair<S, T> p : this)
            resultSet.add(p.getSecond());

        return resultSet;
    }

    public Relation<S, T> rangeExclusion(OrderedSet<T> set) {
        Relation<S, T> result = new PairSetRelation<S, T>();
        for (Pair<S, T> p : this)
            if (!set.contains(p.getSecond()))
                result.add(p);
        return result;
    }

    public Relation<S, T> rangeRestriction(OrderedSet<T> set) {
        Relation<S, T> result = new PairSetRelation<S, T>();
        for (Pair<S, T> p : this)
            if (set.contains(p.getSecond()))
                result.add(p);
        return result;
    }

    public Relation<S, T> union(Relation<S, T> relation) {
        set.addAll(relation.asPairs());
        return this;
    }

    public OrderedSet<T> rightSection(S s) {
        OrderedSet<S> set = new OrderedSet<S>();
        set.add(s);
        return domainRestriction(set).range();
    }

    public OrderedSet<S> leftSection(T t) {
        OrderedSet<T> set = new OrderedSet<T>();
        set.add(t);
        return rangeRestriction(set).domain();
    }

    public static <T extends Comparable<T>> PairSetRelation<T, T> transitiveClosure(
        PairSetRelation<T, T> relation) {
        PairSetRelation<T, T> result = new PairSetRelation<T, T>(relation.asPairs());
        int prevResultSize = 0;    
        while (!(prevResultSize == result.cardinality())) {
            prevResultSize = result.cardinality();
            for (Pair<T,T> p : result.asPairs()) {                
                for (Pair<T, T> p2 : result.asPairs()) {
                    if (p.getSecond().equals(p2.getFirst())) {
                        result.add(new Pair<T, T>(p.getFirst(), p2.getSecond()));
                    }
                }
            }
        }
        return result;
    }
    
    public static <T extends Comparable<T>> PairSetRelation<T, T> reflexiveTransitiveClosure(
        PairSetRelation<T, T> rel) {
        PairSetRelation<T, T> result = new PairSetRelation<T, T>(rel);
        int prevResultSize = 0;    
        while (!(prevResultSize == result.cardinality())) {
            prevResultSize = result.cardinality();
            for (Pair<T,T> p : result.asPairs()) {
                result.add(new Pair<T,T>(p.getFirst(), p.getFirst()));
                result.add(new Pair<T,T>(p.getSecond(), p.getSecond()));
                for (Pair<T, T> p2 : result.asPairs()) {
                    if (p.getSecond().equals(p2.getFirst())) {
                        result.add(new Pair<T, T>(p.getFirst(), p2.getSecond()));
                    }
                }
            }
        }
        return result;
    }
}
