/**
 * 
 */
package jrelcal.bom;

class Interface extends VersionedObject implements Comparable<Interface> {
	public Interface(Subsystem subsystem, Integer version) {
		super(subsystem, version);
	}
	
	public int compareTo(Interface i) {
		return super.compareTo(i);
	}
	
	public String toString() {
		return "<I" + subsystem.toString() + "," + version.toString() + ">";
	}
	
	public String toIdentifier() {
		return "I_" + super.toIdentifier();
	}
	
	public String toLabel() {
		return subsystem.toString() + "^" + Math.abs(version);
	}
	
	
}