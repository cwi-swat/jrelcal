package jrelcal.pms;

public class Build {
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
	
}
