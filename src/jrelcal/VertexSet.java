package jrelcal;

import java.util.TreeSet;

public class VertexSet extends TreeSet<Integer> {
    public VertexSet copy() {
    	VertexSet vertexSet = new VertexSet();
    	vertexSet.addAll(this);
    	return vertexSet;
    }
}
