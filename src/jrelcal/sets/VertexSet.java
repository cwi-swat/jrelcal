package jrelcal.sets;

import java.util.TreeSet;

@SuppressWarnings("serial")
public class VertexSet extends TreeSet<Integer> {
    public VertexSet copy() {
    	VertexSet vertexSet = new VertexSet();
    	vertexSet.addAll(this);
    	return vertexSet;
    }
}
