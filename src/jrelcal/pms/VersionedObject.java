/**
 * 
 */
package jrelcal.pms;

class VersionedObject {
	protected Subsystem subsystem;
	protected Integer version;
	public VersionedObject(Subsystem subsystem, Integer version) {
		this.subsystem = subsystem;
		this.version = version;
	}
	
	public boolean equals(Object o) {
		VersionedObject vo = (VersionedObject)o;
		return subsystem.equals(vo.subsystem) &&
			version.equals(vo.version);
	}
	
	public int compareTo(VersionedObject o) {
		int n = subsystem.compareTo(o.subsystem);
		if (n != 0) {
			return n;
		}
		return version.compareTo(o.version);
	}
	
}