/*
 * Created on Jan 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jrelcal.sets;

import java.util.TreeSet;
import java.util.Iterator;

import jrelcal.Pair;

/**
 * @author storm
 * @class jrelcal.OrderedSet
 */
@SuppressWarnings("serial")
public class OrderedSet<T extends Comparable<T>> extends TreeSet<T>
    implements Comparable<OrderedSet<T>>, Iterable<T> {

    private OrderedSet<T> copy() {
        OrderedSet<T> set = new OrderedSet<T>();
        set.addAll(this);
        return set;
    }
        
    public OrderedSet() {
        super();
    }
    
    public OrderedSet(T element) {
        this();
        add(element);
    }

    public OrderedSet(T element1, T element2) {
        this();
        add(element1);
        add(element2);
    }

    public OrderedSet<T> union(OrderedSet<T> bag) {
        OrderedSet<T> newSet = copy();
        newSet.addAll(bag);
        return newSet;
    }

    public OrderedSet<T> difference(OrderedSet<T> bag) {
        OrderedSet<T> newSet = copy();
        newSet.removeAll(bag);
        return newSet;
    }

    public OrderedSet<T> intersection(OrderedSet<T> bag) {
        OrderedSet<T> newSet = copy();
        newSet.retainAll(bag);
        return newSet;
    }

    public VertexSet toVertexSet(IndexedSet<T> set) {
        VertexSet vertexSet = new VertexSet();
        for (T t: this)
            vertexSet.add(set.indexOf(t));
        return vertexSet;
    }

    
    public int compareTo(OrderedSet<T> set) {
        int thisSize = size();
        int thatSize = set.size();
        if (thisSize < thatSize) return -1;
        if (thisSize > thatSize) return +1;
        Iterator<T> thatIterator = set.iterator();
        for (Iterator<T> thisIterator = iterator(); thisIterator.hasNext();) {
            T thisElt = thisIterator.next();
            T thatElt = thatIterator.next();
            int cmp = thisElt.compareTo(thatElt);
            if (cmp != 0) return cmp;
        }
        return 0;
    }
    
    public boolean equals(OrderedSet<T> set) {
        return compareTo(set) == 0;
    }
    
    public IndexedSet<T> toIndexedSet() {
        IndexedSet<T> set = new IndexedSet<T>();
        for (T t: this)
            set.add(t);
        return set;
    }
    
    public String toString() {
        final String curly = "{";
        StringBuilder str = new StringBuilder(curly);
        for (T t: this) {
            str = str.append(t + ", ");
        }
        if (!str.toString().equals(curly))
            str = str.delete(str.length() - 2, str.length());
        str.append("}");
        return str.toString();
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> OrderedSet<OrderedSet<T>> biggerProduct(OrderedSet<OrderedSet<T>> space) {
        if (space.size() == 0) {
            return new OrderedSet<OrderedSet<T>>();
        }
        if (space.size() == 1) {
            return space;
        }
        if (space.size() == 2) {
            OrderedSet<T> two[] = new OrderedSet[2];
            int i = 0;
            for (OrderedSet<T> set: space) {
                two[i++] = set;
            }
            Relation<T, T> prod = AdjacencyTableRelation.cartesianProduct(two[0], two[1]);
            OrderedSet<OrderedSet<T>> result = new OrderedSet<OrderedSet<T>>();
            for (Pair<T,T> pair: prod) {
                result.add(new OrderedSet<T>(pair.getFirst(), pair.getSecond()));
            }
            return result;          
        }
        if (space.size() > 2) {
            OrderedSet<T> head = null;
            for (OrderedSet<T> set: space) {
                head = set;
                break;
            }
            OrderedSet<OrderedSet<T>> tail = space.difference(new OrderedSet<OrderedSet<T>>(head));
            OrderedSet<OrderedSet<T>> tailProduct = biggerProduct(tail);
            OrderedSet<OrderedSet<T>> result = new OrderedSet<OrderedSet<T>>();
            for (T t: head) {
                for (OrderedSet<T> set: tailProduct) {
                    result.add(set.union(new OrderedSet<T>(t)));
                }
            }
            return result;
        }
        return null;
    }
}
