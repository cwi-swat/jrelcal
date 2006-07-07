package jrelcal.bom;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import jrelcal.Pair;
import jrelcal.sets.Relation;
import jrelcal.sets.Set;

public class BillsOfMaterials {
	private Relation<Build,Interface> _billsOfMaterials;
	private Set<Build> _builds;
	private Relation<Build,Build> _systemBoms;
	private Relation<Build,Subsystem> _classification;
	private Set<Set<Build>> _solutionSpace;
	private Set<Relation<Build,Build>> _allSystemBoms;
	
	public BillsOfMaterials(Set<Build> builds, Relation<Build,Interface> billsOfMaterials) {
		_billsOfMaterials = billsOfMaterials;
		_builds = builds; 
		_systemBoms = allSystemBoms(_billsOfMaterials, _builds);
		_classification = classify(_systemBoms);
		_solutionSpace = solutionSpace(_systemBoms, _classification);
		_allSystemBoms = separateSystemBoms(_solutionSpace, _systemBoms, _classification);
	}
	
	
	private Relation<Build, Build> allSystemBoms(Relation<Build,Interface> boms, Set<Build> builds) {
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
	
	@SuppressWarnings("unused")
	private Relation<Build,Build> aSystemBom(Relation<Build,Build> systemBoms, Build b) {
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
	
	private Relation<Build,Subsystem> classify(Relation<Build,Build> systemBoms) {
		Relation<Build,Subsystem> result = new Relation<Build, Subsystem>();
		for (Build b: Relation.carrier(systemBoms)) {
			result.add(new Pair<Build,Subsystem>(b, b.configuration.iface.subsystem));
		}
		return result;
	}
	
	private Set<Set<Build>> solutionSpace(Relation<Build,Build> systemBoms, Relation<Build,Subsystem> classification) {
		Set<Set<Build>> result = new Set<Set<Build>>();
		for (Subsystem s: classification.range()) {
			Set<Build> bs = classification.inverse().image(s);
			result.add(bs);
		}
		result = Set.biggerProduct(result);  
		return result;
	}
	
	private Set<Relation<Build,Build>> separateSystemBoms(Set<Set<Build>> solutionSpace, Relation<Build,Build> systemBoms, Relation<Build,Subsystem> classification) {
		Set<Relation<Build,Build>> result = new Set<Relation<Build,Build>>();
		for (Set<Build> solution: solutionSpace) {
			Relation<Build,Build> temp = new Relation<Build, Build>();
			for (Pair<Build,Build> pair: Relation.totalGraph(solution)) {
				Build b1 = pair.getFirst();
				Build b2 = pair.getSecond();
				for (Build b: systemBoms.image(b1)) {
					if (classification.image(b2).equals(classification.image(b))) {
						temp.add(pair);
					}
				}
			}
			result.add(temp);
		}
		return result;
	}
	
	public static String systemBomToDotString(Relation<Build, Build> systemBom) {
		StringBuilder str = new StringBuilder("digraph bla {\n");
		for (Build b: Relation.carrier(systemBom)) {
			str.append(b.toIdentifier() + " [label=\"" + b.toLabel() + "\"]\n");
		}
		for (Pair<Build, Build> pair: systemBom) {
			Build b1 = pair.getFirst();
			Build b2 = pair.getSecond();
			str.append(b1.toIdentifier() + " -> " + b2.toIdentifier() + "\n");
		}
		str.append("}\n");
		return str.toString();
	}
	
	public static String systemBomsToDotString(Set<Relation<Build,Build>> systemBoms) {
		StringBuilder str = new StringBuilder();
		for (Relation<Build,Build> systemBom: systemBoms) {
			str.append(systemBomToDotString(systemBom));
		}
		return str.toString();
	}
	
	public static String systemBomsToDotStringWithInterfaces(Set<Relation<Build,Build>> systemBoms) {
		StringBuilder str = new StringBuilder();
		for (Relation<Build,Build> systemBom: systemBoms) {
			str.append(systemBomToDotStringWithInterfaces(systemBom));
		}
		return str.toString();
	}

	public static String systemBomToDotStringWithInterfaces(Relation<Build, Build> systemBom) {
		StringBuilder str = new StringBuilder("strict digraph bla {\n");
		for (Build b: Relation.carrier(systemBom)) {
			Interface in = b.configuration.iface;
			Body bo = b.configuration.body;
			str.append(in.toIdentifier() + " [shape=ellipse,label=\"" + in.toLabel() + "\"]\n");
			str.append(bo.toIdentifier() + " [shape=box,label=\"" + bo.toLabel() + "\"]\n");
			str.append(in.toIdentifier() + " -> " + bo.toIdentifier() + " [style=dotted,arrowhead=none]\n");
		}
		for (Pair<Build, Build> pair: systemBom) {
			Build b1 = pair.getFirst();
			Build b2 = pair.getSecond();
			str.append(b1.configuration.body.toIdentifier() + " -> " + b2.configuration.iface.toIdentifier() + "\n");
		}
		str.append("}\n");
		return str.toString();
	}
	
	private static Set<Build> myBuilds() {
		Set<Subsystem> subsystems;
		Set<Interface> interfaces;
		Set<Body> bodies;
		Set<Configuration> configurations;
		Set<Build> builds;
		
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
		return builds;
	}
	
	private static Relation<Build,Interface> myBoms() {
		Set<Subsystem> subsystems;
		Set<Interface> interfaces;
		Set<Body> bodies;
		Set<Configuration> configurations;
		Set<Build> builds;
		Relation<Build, Interface> boms;
		
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
		
		return boms;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BillsOfMaterials app = new BillsOfMaterials(myBuilds(), myBoms());
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("/export/scratch1/storm/bla.dot"));
			for (Relation<Build,Build> systemBom: app.getAllSystemBoms()) {
				out.write(systemBomToDotString(systemBom));
			}
			out.close();
		} 
		catch (IOException e) {
			System.err.println("IO error :-(");
		}
	}


	public Set<Relation<Build, Build>> getAllSystemBoms() {
		return _allSystemBoms;
	}

	public Relation<Build, Interface> getBillsOfMaterials() {
		return _billsOfMaterials;
	}

	public Set<Build> getBuilds() {
		return _builds;
	}

	public Relation<Build, Subsystem> getClassification() {
		return _classification;
	}

	public Set<Set<Build>> getSolutionSpace() {
		return _solutionSpace;
	}

	public Relation<Build, Build> getSystemBoms() {
		return _systemBoms;
	}
	
	
	
	

}
