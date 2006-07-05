package jrelcal.sets;

import java.util.TreeSet;

@SuppressWarnings("serial")
public class VertexSet extends TreeSet<Integer> {
    public VertexSet copy() {
    	VertexSet vertexSet = new VertexSet();
    	vertexSet.addAll(this);
    	return vertexSet;
    }
    
    
    public String toString() {
    	final String curly = "{";
    	StringBuilder str = new StringBuilder(curly);
    	for (Integer t: this) {
    		str = str.append(t + ", ");
    	}
    	if (!str.toString().equals(curly))
    		str = str.delete(str.length() - 2, str.length());
    	str.append("}");
    	return str.toString();
    }
    
}
