/**
 * 
 */
package jrelcal.pms;

class Interface extends VersionedObject implements Comparable<Interface> {
	public Interface(Subsystem subsystem, Integer version) {
		super(subsystem, version);
	}
	
	public int compareTo(Interface i) {
		return super.compareTo(i);
	}
	
}