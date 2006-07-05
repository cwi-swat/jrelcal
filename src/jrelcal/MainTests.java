package jrelcal;

import jrelcal.bags.MultiRelation;
import jrelcal.bags.Bag;
import jrelcal.sets.IndexedSet;

public class MainTests {
	public static final int BOUND = 10;
	
	public static void main(String arg[]) {
		Bag<Pair<Integer, Integer>> pairs = new Bag<Pair<Integer, Integer>>();
		for (int i = 0; i < 4; i++) {
			pairs.add(new Pair<Integer, Integer>(i, i + 1));
			pairs.add(new Pair<Integer, Integer>(i+1, i + 2));
		}
		
		IndexedSet<Integer> set = new IndexedSet<Integer>();
		for (int i = 0; i < BOUND; i++) {
			set.add(i);
		}

		Bag<Pair<Integer, Integer>> p1 = new Bag<Pair<Integer, Integer>>();
		Bag<Pair<Integer, Integer>> p2 = new Bag<Pair<Integer, Integer>>();
		
		for (int i = 0; i < BOUND - 1; i++) {
			p1.add(new Pair<Integer, Integer>(i, i + 1));
			p2.add(new Pair<Integer, Integer>(i + 1, i + 2));
		}		
		
		p1.add(new Pair<Integer,Integer>(0,1));
			
		System.out.println("Pairs 1: " + p1);
		System.out.println("Pairs 2: " + p2);

		MultiRelation<Integer,Integer> r1 = new MultiRelation<Integer,Integer>(p1);
		MultiRelation<Integer,Integer> r2 = new MultiRelation<Integer,Integer>(p2);
		
		System.out.println("Rel 1: " + r1);
		System.out.println("Rel 2: " + r2);

		
//		System.out.println("r1.compose(r2): " + r1.compose(r2));
//		System.out.println("r1.compose(r1): " + r1.compose(r1));
//		System.out.println("r2.compose(r1): " + r2.compose(r1));
		
		System.out.println("R1 intersection R2: " + r1.intersection(r2));
		System.out.println("Transitive closure of rel 1: " + MultiRelation.transitiveClosure(r1));

	}

}
