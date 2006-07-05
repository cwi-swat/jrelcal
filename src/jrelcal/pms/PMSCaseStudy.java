package jrelcal.pms;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import jrelcal.Pair;
import jrelcal.sets.Relation;
import jrelcal.sets.Set;

public class PMSCaseStudy {
	Relation<Build, Build> allSystemBoms(Relation<Build,Interface> boms, Set<Build> builds) {
		Relation<Build,Build> result = new Relation<Build,Build>();
		for (Pair<Build,Interface> b1_i: boms) {
			Build b1 = b1_i.getFirst();
			Interface i = b1_i.getSecond();			
			for (Build b2: builds) {
				if (i.subsystem.equals(b2.configuration.iface.subsystem)) {
					result.add(new Pair<Build, Build>(b1, b2));
				}
			}
		}
		return result;
	}
	

	Relation<Build,Build> aSystemBom(Relation<Build,Build> systemBoms, Build b) {
		Relation<Build,Build> result = new Relation<Build,Build>();
		Relation<Build,Build> systemBomsClosure = Relation.reflexiveTransitiveClosure(systemBoms);
		Set<Build> reachable = systemBomsClosure.rightImage(b);
		for (Pair<Build,Build> b1_b2: systemBoms) {
			Build b1 = b1_b2.getFirst();
			Build b2 = b1_b2.getSecond();
			if (reachable.contains(b1) && reachable.contains(b2)) {
				result.add(new Pair<Build,Build>(b1, b2));
			}
		}
		return result;
	}
	
	
	Relation<Build,Subsystem> classify(Relation<Build,Build> systemBoms) {
		Relation<Build,Subsystem> result = new Relation<Build, Subsystem>();
		for (Build b: Relation.carrier(systemBoms)) {
			result.add(new Pair<Build,Subsystem>(b, b.configuration.iface.subsystem));
		}
		return result;
	}
	
	Set<Set<Build>> generalizedProduct(Set<Set<Build>> space) {
		Set<Set<Build>> result = new Set<Set<Build>>();
		Set<Set<Build>> previous = null;
		for (Set<Build> current: space) {
			if (previous == null) {
				previous = new Set<Set<Build>>(current);
				continue;
			}
			Relation<Set<Build>,Build> product = Relation.cartesianProduct(previous, current);
			previous = new Set<Set<Build>>();
			for (Pair<Set<Build>,Build> pair: product) {
				previous.add(pair.getFirst().union(new Set<Build>(pair.getSecond())));
			}			
		}
		result = previous;
		return result;
	}
	
	Set<Set<Build>> space(Relation<Build, Subsystem> classification) {
		Set<Set<Build>> result = new Set<Set<Build>>();
		for (Subsystem s: classification.range()) {
			Set<Build> bs = classification.inverse().image(s);
			result.add(bs);
		}
		return generalizedProduct(result);
	}
	
	
	
	
	Set<Build> prune(Relation<Build,Build> systemBoms, Relation<Build, Subsystem> classification, Set<Build> s) {
		Set<Build> result = new Set<Build>();
		for (Build b: s) {
			Set<Build> bset = new Set<Build>(b);
			Set<Build> reachableWithoutB = systemBoms.image(s.difference(bset)); 
			if (classification.image(reachableWithoutB).containsAll(classification.image(b))) {
				result.add(b);
			}
		}
		return result;
	}
	
	Set<Set<Build>> searchSpace(Relation<Build,Build> systemBoms, Relation<Build, Subsystem> classification, Set<Build> s) {
		// It seems that pruning isn't necessary?!!?!?
//		Set<Set<Build>> result = new Set<Set<Build>>();
//		Set<Set<Build>> space = space(classification);
//		for (Set<Build> sol: space) {
//			result.add(prune(systemBoms, classification, sol));
//		}
//		return result;
		return space(classification);
	}
	
	void example() {
		Set<Subsystem> subsystems;
		Set<Interface> interfaces;
		Set<Body> bodies;
		Set<Configuration> configurations;
		Set<Build> builds;
		Relation<Build, Interface> boms;
		Relation<Build, Build> systemBoms;

		
		Subsystem SApp = new Subsystem("App");
		Subsystem SX = new Subsystem("X");
		Subsystem SY = new Subsystem("Y");
		Subsystem SZ = new Subsystem("Z");
		Subsystem SA = new Subsystem("A");
		Subsystem SB = new Subsystem("B");
		Subsystem SC = new Subsystem("C");
		Subsystem SM = new Subsystem("M");
		Subsystem SN = new Subsystem("N");
		
		
		subsystems = new Set<Subsystem>();
		subsystems.add(SApp);
		subsystems.add(SX);
		subsystems.add(SY);
		subsystems.add(SZ);
		subsystems.add(SA);
		subsystems.add(SB);
		subsystems.add(SC);
		subsystems.add(SM);
		subsystems.add(SN);

		Interface IApp1 = new Interface(SApp, -1);
		Interface IX1 = new Interface(SX, -1);
		Interface IY1 = new Interface(SY, -1);
		Interface IZ1 = new Interface(SZ, -1);
		Interface IA1 = new Interface(SA, -1);
		Interface IA2 = new Interface(SA, -2);
		Interface IA3 = new Interface(SA, -3);
		Interface IB1 = new Interface(SB, -1);
		Interface IB2 = new Interface(SB, -2);
		Interface IC1 = new Interface(SC, -1);
		Interface IC2 = new Interface(SC, -2);
		Interface IM1 = new Interface(SM, -1);
		Interface IM2 = new Interface(SM, -2);
		Interface IN1 = new Interface(SN, -1);

		
		
		interfaces = new Set<Interface>();
		interfaces.add(IApp1);
		interfaces.add(IX1);
		interfaces.add(IY1);
		interfaces.add(IZ1);
		interfaces.add(IA1);
		interfaces.add(IA2);
		interfaces.add(IA3);
		interfaces.add(IB1);
		interfaces.add(IB2);
		interfaces.add(IC1);
		interfaces.add(IC2);
		interfaces.add(IM1);
		interfaces.add(IM2);
		interfaces.add(IN1);
		

		Body BApp1 = new Body(SApp, 1);
		Body BX1 = new Body(SX, 1);
		Body BY1 = new Body(SY, 1);
		Body BZ1 = new Body(SZ, 1);
		Body BA1 = new Body(SA, 1);
		Body BA2 = new Body(SA, 2);
		Body BA3 = new Body(SA, 3);
		Body BB1 = new Body(SB, 1);
		Body BB2 = new Body(SB, 2);
		Body BC1 = new Body(SC, 1);
		Body BC2 = new Body(SC, 2);
		Body BM1 = new Body(SM, 1);
		Body BM2 = new Body(SM, 2);
		Body BN1 = new Body(SN, 1);
		
		bodies = new Set<Body>();
		bodies.add(BApp1);
		bodies.add(BX1);
		bodies.add(BY1);
		bodies.add(BZ1);
		bodies.add(BA1);
		bodies.add(BA2);
		bodies.add(BA3);
		bodies.add(BB1);
		bodies.add(BB2);
		bodies.add(BC1);
		bodies.add(BC2);
		bodies.add(BM1);
		bodies.add(BM2);
		bodies.add(BN1);
		

		Configuration CApp1 = new Configuration(IApp1, BApp1);
		Configuration CX1 = new Configuration(IX1, BX1);
		Configuration CY1 = new Configuration(IY1, BY1);
		Configuration CZ1 = new Configuration(IZ1, BZ1);
		Configuration CA1 = new Configuration(IA1, BA1);
		Configuration CA2 = new Configuration(IA2, BA2);
		Configuration CA3 = new Configuration(IA3, BA3);
		Configuration CB1 = new Configuration(IB1, BB1);
		Configuration CB2 = new Configuration(IB2, BB2);
		Configuration CC1 = new Configuration(IC1, BC1);
		Configuration CC2 = new Configuration(IC2, BC2);
		Configuration CM1 = new Configuration(IM1, BM1);
		Configuration CM2 = new Configuration(IM2, BM2);
		Configuration CN1 = new Configuration(IN1, BN1);
		
		configurations = new Set<Configuration>();
		configurations.add(CApp1);
		configurations.add(CX1);
		configurations.add(CY1);
		configurations.add(CZ1);
		configurations.add(CA1);
		configurations.add(CA2);
		configurations.add(CA3);
		configurations.add(CB1);
		configurations.add(CB2);
		configurations.add(CC1);
		configurations.add(CC2);
		configurations.add(CM1);
		configurations.add(CM2);
		configurations.add(CN1);
		

		Build App1 = new Build(CApp1, 0);
		Build X1 = new Build(CX1, 0);
		Build Y1 = new Build(CY1, 0);
		Build Z1 = new Build(CZ1, 0);
		Build A1 = new Build(CA1, 0);
		Build A2 = new Build(CA2, 0);
		Build A3 = new Build(CA3, 0);
		Build B1 = new Build(CB1, 0);
		Build B2 = new Build(CB2, 0);
		Build C1 = new Build(CC1, 0);
		Build C2 = new Build(CC2, 0);
		Build M1 = new Build(CM1, 0);
		Build M2 = new Build(CM2, 0);
		Build N1 = new Build(CN1, 0);

		builds = new Set<Build>();
		builds.add(App1);
		builds.add(X1);
		builds.add(Y1);
		builds.add(Z1);
		builds.add(A1);
		builds.add(A2);
		builds.add(A3);
		builds.add(B1);
		builds.add(B2);
		builds.add(C1);
		builds.add(C2);
		builds.add(M1);
		builds.add(M2);
		builds.add(N1);
		
		boms = new Relation<Build, Interface>();
		boms.add(new Pair<Build,Interface>(App1, IX1));
		boms.add(new Pair<Build,Interface>(App1, IY1));
		boms.add(new Pair<Build,Interface>(App1, IZ1));
		boms.add(new Pair<Build,Interface>(X1, IA1));
		boms.add(new Pair<Build,Interface>(Y1, IA2));
		boms.add(new Pair<Build,Interface>(Z1, IA3));
		boms.add(new Pair<Build,Interface>(Z1, IC2));
		boms.add(new Pair<Build,Interface>(A1, IB1));
		boms.add(new Pair<Build,Interface>(A1, IC1));
		boms.add(new Pair<Build,Interface>(A2, IB2));
		boms.add(new Pair<Build,Interface>(A2, IC1));
		boms.add(new Pair<Build,Interface>(A3, IB2));
		boms.add(new Pair<Build,Interface>(A3, IC1));
		boms.add(new Pair<Build,Interface>(B1, IM1));
		boms.add(new Pair<Build,Interface>(B2, IM2));
		boms.add(new Pair<Build,Interface>(C2, IN1));
		
		systemBoms = allSystemBoms(boms, builds);
		Relation<Build,Subsystem> classification = classify(systemBoms);
		Set<Set<Build>> solutionSpace = searchSpace(systemBoms, classification, builds);
	
		p("/*");
		p(" * Boms: " + boms);
		p(" * SystemBoms: " + systemBoms);
		p(" * Classification: " + classification);
		p(" * Solution space: " + solutionSpace);
		p(" * Solution size: " + solutionSpace.size());
		p(" */\n\n");
		
		//Set<Relation<Build,Build>> derivedSystemBoms = new Set<Relation<Build,Build>>();
		// App1 is the root of all systemboms
		
		int i = 0;
		for(Set<Build> sol: solutionSpace) {
			p("/* Solution #" + ++i + "*/");
			Relation<Build,Build> temp = new Relation<Build, Build>();
			for (Pair<Build,Build> pair: Relation.totalGraph(sol)) {				
				Build b1 = pair.getFirst();
				Build b2 = pair.getSecond();
				for (Build b: systemBoms.image(b1)) {
					if (classification.image(b2).equals(classification.image(b))) {
						temp.add(pair);
					}
				}
			}
			dot2(temp);
		}
		
		try {
			output.close();
		}
		catch (IOException e) {
			
		}
		
		
	}
	
	public void dot(Relation<Build, Build> systemBom) {
		p("digraph bla {");
		for (Build b: Relation.carrier(systemBom)) {
			p(b.toIdentifier() + " [label=\"" + b.toLabel() + "\"]");
		}
		for (Pair<Build, Build> pair: systemBom) {
			Build b1 = pair.getFirst();
			Build b2 = pair.getSecond();
			p(b1.toIdentifier() + " -> " + b2.toIdentifier());
		}
		p("}");
		
	}

	public void dot2(Relation<Build, Build> systemBom) {
		p("strict digraph bla {");
		for (Build b: Relation.carrier(systemBom)) {
			Interface in = b.configuration.iface;
			Body bo = b.configuration.body;
			p(in.toIdentifier() + " [shape=ellipse,label=\"" + in.toLabel() + "\"]");
			p(bo.toIdentifier() + " [shape=box,label=\"" + bo.toLabel() + "\"]");
			p(in.toIdentifier() + " -> " + bo.toIdentifier() + " [style=dotted,arrowhead=none]");
		}
		for (Pair<Build, Build> pair: systemBom) {
			Build b1 = pair.getFirst();
			Build b2 = pair.getSecond();
			p(b1.configuration.body.toIdentifier() + " -> " + b2.configuration.iface.toIdentifier());
		}
		p("}");
		
	}

	
	private BufferedWriter output = null;
	
	public void p(String s) {
		try {
			if (output == null) {
				output = new BufferedWriter(new FileWriter("/export/scratch1/storm/bla.dot"));
			}
	        output.write(s + "\n");
	    } catch (IOException e) {
	    	System.err.println("Error!");
	    }
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PMSCaseStudy app = new PMSCaseStudy();
		app.example();
	}
	
	
	
	

}
