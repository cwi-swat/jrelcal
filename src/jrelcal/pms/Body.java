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
	
	public String toString() {
		return "<B" + subsystem.toString() + "," + version.toString() + ">";
	}
	
	public String toLabel() {
		return subsystem.toString() + "_" + version;
	}
	
	public String toIdentifier() {
		return "B_" + super.toIdentifier();
	}
	
	
}