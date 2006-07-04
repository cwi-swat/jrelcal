/*
 * Created on Jan 19, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jrelcal.sets;

/**
 * @author storm
 * @class jrelcal.Indexable
 */
public interface Indexable<T> extends Iterable<T> {
	public int indexOf(T t);
	public T elementAt(int n);
	public VertexSet toVertexSet();
}
