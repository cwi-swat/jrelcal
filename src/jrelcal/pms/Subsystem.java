/**
 * 
 */
package jrelcal.pms;

class Subsystem {
	protected String name;
	public Subsystem(String name) {
		this.name = name;
	}
	
	public boolean equals(Object o) {
		return name.equals(((Subsystem)o).name);
	}
	
}