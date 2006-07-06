package jrelcal.ci;

import jrelcal.Pair;
import jrelcal.sets.*;

public class ContinuousIntegration {
	
	
	class Build implements Comparable<Build> {
		private String _name;
		private Integer _id;
		
		public Build(String name, int id) {
			_name = name;
			_id = id;
		}
		
		public String getName() {
			return _name;
		}
		
		public Integer getId() {
			return _id;
		}
		
		public int compareTo(Build b) {
			int n = getName().compareTo(b.getName());
			if (n != 0) {
				return n;
			}
			return getId().compareTo(b.getId());
		}
		
		public String toString() {
			return "<" + getName() + ", " + getId() + ">";
		}
		
	}
		
	public static void main(String args[]) {
		ContinuousIntegration ci = new ContinuousIntegration();
		ci.example();
	}
	
	public void example() {
		Set<Build> builds = new Set<Build>();
		builds.add(new Build("A", 1));
		builds.add(new Build("B", 1));
		builds.add(new Build("B", 2));
		builds.add(new Build("C", 1));
		builds.add(new Build("C", 2));
		builds.add(new Build("D", 1));
		
		Relation<String,Build> classification = new Relation<String,Build>();
		for (Build b: builds) {
			classification.add(new Pair<String,Build>(b.getName(), b));
		}
		System.out.println(classification);
		Set<String> D = classification.domain().difference(new Set<String>("A"));
		Set<Set<Build>> buildContexts = new Set<Set<Build>>();
		for (String d: D) {
			buildContexts.add(classification.image(d));
		}
		System.out.println(buildContexts);
		System.out.println(Set.biggerProduct(buildContexts));	
	}
	
	
	
}
