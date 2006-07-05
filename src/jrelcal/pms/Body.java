/**
 * 
 */
package jrelcal.pms;

class Body extends VersionedObject implements Comparable<Body> {
	public Body(Subsystem subsystem, Integer version) {
		super(subsystem, version);
	}
	
	public int compareTo(Body b) {
		return super.compareTo(b);
	}
	
}