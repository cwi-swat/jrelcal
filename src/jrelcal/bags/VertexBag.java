/*
 * Created on Jan 19, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jrelcal.bags;

/**
 * @author storm
 * @class jrelcal.VertexBag
 */
public class VertexBag extends Bag<Integer> {
	public VertexBag() {
		super();
	}
	
	public VertexBag copy() {
    	VertexBag vertexBag = new VertexBag();
    	vertexBag.addAll(this);
    	return vertexBag;
    }
}