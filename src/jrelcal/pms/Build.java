
package jrelcal.pms;

public class Build implements Comparable<Build> {
	protected Integer id;
	protected Configuration configuration;
	
	public Build(Configuration configuration, Integer id) {
		this.id = id;
		this.configuration = configuration;
	}
	
	public boolean equals(Object o) {
		Build b = (Build)o;
		return id.equals(b.id) &&
			configuration.equals(b.configuration);
	}
	
	public int compareTo(Build b) {
		int n = configuration.compareTo(b.configuration);
		if (n != 0) {
			return n;
		}
		return id.compareTo(b.id);
	}

	public String toString() {
		return "<" + configuration.toString() + "," + id.toString() + ">";
	}	

	public String toIdentifier() {
		return "Build_" + configuration.toIdentifier() + "_" + id.toString();
	}
	
	public String toLabel() {
		return configuration.iface.subsystem + "^" + 
			Math.abs(configuration.iface.version) + "_" + 
			configuration.body.version;
	}
	
}
