package jrelcal.sets;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import jrelcal.Pair;

public class PairSetRelation<S extends Comparable<S>, T extends Comparable<T>> 
    extends AbstractRelation<S, T> 
{
    Set<Pair<S, T>> set = new HashSet<Pair<S,T>>();

    public void add(Pair<S, T> pair) {
        // TODO Auto-generated method stub
        
    }

    public OrderedSet<Pair<S, T>> asPairs() {
        // TODO Auto-generated method stub
        return null;
    }

    public int cardinality() {
        // TODO Auto-generated method stub
        return 0;
    }

    public Relation<S, T> complement() {
        // TODO Auto-generated method stub
        return null;
    }

    public <U extends Comparable<U>> Relation<S, U> compose(Relation<T, U> relation) {
        // TODO Auto-generated method stub
        return null;
    }

    public Relation<S, T> difference(Relation<S, T> relation) {
        // TODO Auto-generated method stub
        return null;
    }

    public OrderedSet<S> domain() {
        // TODO Auto-generated method stub
        return null;
    }

    public Relation<S, T> domainExclusion(OrderedSet<T> set) {
        // TODO Auto-generated method stub
        return null;
    }

    public Relation<S, T> domainRestriction(OrderedSet<S> set) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean elementOf(Pair<S, T> element) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean equals(Relation<S, T> relation) {
        // TODO Auto-generated method stub
        return false;
    }

    public OrderedSet<T> image(S s) {
        // TODO Auto-generated method stub
        return null;
    }

    public OrderedSet<T> image(OrderedSet<S> set) {
        // TODO Auto-generated method stub
        return null;
    }

    public void initialize(OrderedSet<Pair<S, T>> pairs) {
        // TODO Auto-generated method stub
        
    }

    public void initialize(IndexedSet<S> domain, IndexedSet<T> range, OrderedSet<Pair<S, T>> pairs) {
        // TODO Auto-generated method stub
        
    }

    public Relation<S, T> intersection(Relation<S, T> relation) {
        // TODO Auto-generated method stub
        return null;
    }

    public Relation<T, S> inverse() {
        // TODO Auto-generated method stub
        return null;
    }

    public Iterator<Pair<S, T>> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    public OrderedSet<S> leftImage(T t) {
        // TODO Auto-generated method stub
        return null;
    }

    public OrderedSet<S> leftImage(OrderedSet<T> set) {
        // TODO Auto-generated method stub
        return null;
    }

    public OrderedSet<S> leftSection(T t) {
        // TODO Auto-generated method stub
        return null;
    }

    public Relation<S, T> pruneWithDomainAndRange() {
        // TODO Auto-generated method stub
        return null;
    }

    public OrderedSet<T> range() {
        // TODO Auto-generated method stub
        return null;
    }

    public Relation<S, T> rangeExclusion(OrderedSet<T> set) {
        // TODO Auto-generated method stub
        return null;
    }

    public Relation<S, T> rangeRestriction(OrderedSet<T> set) {
        // TODO Auto-generated method stub
        return null;
    }

    public OrderedSet<T> rightImage(S s) {
        // TODO Auto-generated method stub
        return null;
    }

    public OrderedSet<T> rightImage(OrderedSet<S> set) {
        // TODO Auto-generated method stub
        return null;
    }

    public OrderedSet<T> rightSection(S s) {
        // TODO Auto-generated method stub
        return null;
    }

    public Relation<S, T> union(Relation<S, T> relation) {
        // TODO Auto-generated method stub
        return null;
    }
    
    
   
}
