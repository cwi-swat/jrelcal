package jrelcal.sets;

import java.util.Iterator;
import java.util.Set;

import jrelcal.Pair;

public interface Relation<S extends Comparable<S>, T extends Comparable<T>>
    extends
        Iterable<Pair<S, T>>,
        Comparable<Relation<S, T>> {

    public void add(Pair<S, T> pair);
    
    public void add(Set<Pair<S,T>> pairs);
   
    public OrderedSet<Pair<S, T>> asPairs();

    public Relation<T, S> inverse();

    public OrderedSet<S> domain();

    public OrderedSet<T> range();

    public Relation<S, T> union(Relation<S, T> relation);

    public Relation<S, T> intersection(Relation<S, T> relation);

    public Relation<S, T> difference(Relation<S, T> relation);

    public <U extends Comparable<U>> Relation<S, U> compose(Relation<T, U> relation);

    public Relation<S, T> domainRestriction(OrderedSet<S> set);

    public Relation<S, T> domainExclusion(OrderedSet<S> set);

    public Relation<S, T> rangeRestriction(OrderedSet<T> set);

    public Relation<S, T> rangeExclusion(OrderedSet<T> set);
        
    public OrderedSet<T> rightSection(S s); 
    
    public OrderedSet<S> leftSection(T t);
        
    public boolean equals(Relation<S, T> relation);
    
    @SuppressWarnings("unchecked")
    public boolean equals(Object o);

    public int cardinality();

    public String toString();

    public Iterator<Pair<S, T>> iterator();

    public int compareTo(Relation<S, T> relation);

}