package jrelcal.sets;

import java.util.Iterator;

import jrelcal.Pair;

public interface Relation<S extends Comparable<S>, T extends Comparable<T>>
    extends
        Iterable<Pair<S, T>>,
        Comparable<Relation<S, T>> {

    public void initialize(Set<Pair<S, T>> pairs);

    public void initialize(IndexedSet<S> domain, IndexedSet<T> range, Set<Pair<S, T>> pairs);

    public void add(Pair<S, T> pair);

    public Set<Pair<S, T>> asPairs();

    public Relation<T, S> inverse();

    public Set<S> domain();

    public Set<T> range();

    public Relation<S, T> pruneWithDomainAndRange();

    public Relation<S, T> union(Relation<S, T> relation);

    public Relation<S, T> intersection(Relation<S, T> relation);

    public Relation<S, T> difference(Relation<S, T> relation);

    public Relation<S, T> complement();

    public <U extends Comparable<U>> Relation<S, U> compose(Relation<T, U> relation);

    public Relation<S, T> domainRestriction(Set<S> set);

    public Relation<S, T> domainExclusion(Set<T> set);

    public Relation<S, T> rangeRestriction(Set<T> set);

    public Relation<S, T> rangeExclusion(Set<T> set);

    public Set<T> rightImage(S s);

    public Set<S> leftImage(T t);

    public Set<T> image(S s);

    public Set<T> image(Set<S> set);

    public Set<T> rightImage(Set<S> set);

    public Set<S> leftImage(Set<T> set);

    public Set<T> rightSection(S s);

    public Set<S> leftSection(T t);

    public boolean equals(Relation<S, T> relation);

    @SuppressWarnings("unchecked")
    public boolean equals(Object o);

    public int cardinality();

    public boolean elementOf(Pair<S, T> element);

    public String toString();

    public Iterator<Pair<S, T>> iterator();

    public int compareTo(Relation<S, T> relation);

}