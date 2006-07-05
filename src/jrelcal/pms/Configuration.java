/**
 * 
 */
package jrelcal.pms;

class Configuration {
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
	
}