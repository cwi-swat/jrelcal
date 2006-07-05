/**
 * 
 */
package jrelcal.pms;

class Configuration implements Comparable<Configuration> {
	protected Interface iface;
	protected Body body;
	public Configuration(Interface iface, Body body) {
		this.iface = iface;
		this.body = body;
	}
	
	public boolean equals(Object o) {
		Configuration c = (Configuration)o;
		return iface.equals(c.iface) &&
			body.equals(c.body);
	}
	
	public int compareTo(Configuration c) {
		int n = iface.compareTo(c.iface);
		if (n != 0) {
			return n;
		}
		return body.compareTo(c.body);
	}
	
}