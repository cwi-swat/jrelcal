package jrelcal.sets;

import java.util.Iterator;

import jrelcal.Pair;

public interface Relation<S extends Comparable<S>, T extends Comparable<T>>
    extends
        Iterable<Pair<S, T>>,
        Comparable<Relation<S, T>> {

    public void initialize(OrderedSet<Pair<S, T>> pairs);

    public void initialize(IndexedSet<S> domain, IndexedSet<T> range, OrderedSet<Pair<S, T>> pairs);

    public void add(Pair<S, T> pair);

    public OrderedSet<Pair<S, T>> asPairs();

    public Relation<T, S> inverse();

    public OrderedSet<S> domain();

    public OrderedSet<T> range();

    public Relation<S, T> pruneWithDomainAndRange();

    public Relation<S, T> union(Relation<S, T> relation);

    public Relation<S, T> intersection(Relation<S, T> relation);

    public Relation<S, T> difference(Relation<S, T> relation);

    public Relation<S, T> complement();

    public <U extends Comparable<U>> Relation<S, U> compose(Relation<T, U> relation);

    public Relation<S, T> domainRestriction(OrderedSet<S> set);

    public Relation<S, T> domainExclusion(OrderedSet<T> set);

    public Relation<S, T> rangeRestriction(OrderedSet<T> set);

    public Relation<S, T> rangeExclusion(OrderedSet<T> set);

    public OrderedSet<T> rightImage(S s);

    public OrderedSet<S> leftImage(T t);

    public OrderedSet<T> image(S s);

    public OrderedSet<T> image(OrderedSet<S> set);

    public OrderedSet<T> rightImage(OrderedSet<S> set);

    public OrderedSet<S> leftImage(OrderedSet<T> set);

    public OrderedSet<T> rightSection(S s);

    public OrderedSet<S> leftSection(T t);

    public boolean equals(Relation<S, T> relation);

    @SuppressWarnings("unchecked")
    public boolean equals(Object o);

    public int cardinality();

    public boolean elementOf(Pair<S, T> element);

    public String toString();

    public Iterator<Pair<S, T>> iterator();

    public int compareTo(Relation<S, T> relation);

}