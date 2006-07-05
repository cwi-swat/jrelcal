/**
 * 
 */
package jrelcal.pms;

class Subsystem implements Comparable<Subsystem> {
	protected String name;
	
	public Subsystem(String name) {
		this.name = name;
	}
	
	public boolean equals(Object o) {
		return name.equals(((Subsystem)o).name);
	}

	public int compareTo(Subsystem s) {
		return name.compareTo(s.name);
	}
	
	public String toString() {
		return name;
	}
	
	public String toIdentifier() {
		return toString();
	}
	
}