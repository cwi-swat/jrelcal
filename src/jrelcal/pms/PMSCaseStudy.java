package jrelcal.pms;


import jrelcal.Pair;
import jrelcal.sets.*;

public class PMSCaseStudy {
	
	Set<Subsystem> subsystems;
	Set<Interface> interfaces;
	Set<Body> bodies;
	Set<Configuration> configurations;
	Set<Build> builds;
	Relation<Build, Interface> boms;
	Relation<Build, Build> systemBoms;
	
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
		Set<Set<Build>> result = new Set<Set<Build>>();
		Set<Set<Build>> space = space(classification);
		for (Set<Build> sol: space) {
			result.add(prune(systemBoms, classification, sol));
		}
		return result;
	}
	
	void example() {
		Set<Subsystem> subsystems = new Set<Subsystem>();
		subsystems.add(new Subsystem("App"));
		subsystems.add(new Subsystem("X"));
		subsystems.add(new Subsystem("Y"));
		subsystems.add(new Subsystem("Z"));
		subsystems.add(new Subsystem("A"));
		subsystems.add(new Subsystem("B"));
		subsystems.add(new Subsystem("C"));
		subsystems.add(new Subsystem("M"));
		subsystems.add(new Subsystem("N"));
		
		
		/*
		
set[AnInterface] interfaces = {
	<"App", -1>,
	<"X", -1>,
	<"Y", -1>,
	<"Z", -1>,
	<"A", -1>,
	<"A", -2>,
	<"A", -3>,
	<"B", -1>,
	<"B", -2>,
	<"C", -1>,
	<"C", -2>,
	<"M", -1>,
	<"M", -2>,
	<"N", -1>
	}

set[ABody] bodies = {
	<"App", 1>,
	<"X", 1>,
	<"Y", 1>,
	<"Z", 1>,
	<"A", 1>,
	<"A", 2>,
	<"A", 3>,
	<"B", 1>,
	<"B", 2>,
	<"C", 1>,
	<"C", 2>,
	<"M", 1>,
	<"M", 2>,
	<"N", 1>
	}

ABody BApp = <"App", 1>
ABody BX1 = <"X", 1>
ABody BY1 = <"Y", 1>
ABody BZ1 = <"Z", 1>
ABody BA1 = <"A", 1>
ABody BA2 = <"A", 2>
ABody BA3 = <"A", 3>
ABody BB1 = <"B", 1>
ABody BB2 = <"B", 2>
ABody BC1 = <"C", 1>
ABody BC2 = <"C", 2>
ABody BM1 = <"M", 1>
ABody BM2 = <"M", 2>
ABody BN1 = <"N", 1>


AnInterface IApp = <"App", -1>
AnInterface IX1 = <"X", -1>
AnInterface IY1 = <"Y", -1>
AnInterface IZ1 = <"Z", -1>
AnInterface IA1 = <"A", -1>
AnInterface IA2 = <"A", -2>
AnInterface IA3 = <"A", -3>
AnInterface IB1 = <"B", -1>
AnInterface IB2 = <"B", -2>
AnInterface IC1 = <"C", -1>
AnInterface IC2 = <"C", -2>
AnInterface IM1 = <"M", -1>
AnInterface IM2 = <"M", -2>
AnInterface IN1 = <"N", -1>

AConfiguration CApp = <<"App", -1>, <"App", 1>>
AConfiguration CX1 = <<"X", -1>, <"X", 1>>
AConfiguration CY1 = <<"Y", -1>, <"Y", 1>>
AConfiguration CZ1 = <<"Z", -1>, <"Z", 1>>
AConfiguration CA1 = <<"A", -1>, <"A", 1>>
AConfiguration CA2 = <<"A", -2>, <"A", 2>>
AConfiguration CA3 = <<"A", -3>, <"A", 3>>
AConfiguration CB1 = <<"B", -1>, <"B", 1>>
AConfiguration CB2 = <<"B", -2>, <"B", 2>>
AConfiguration CC1 = <<"C", -1>, <"C", 1>>
AConfiguration CC2 = <<"C", -2>, <"C", 2>>
AConfiguration CM1 = <<"M", -1>, <"M", 1>>
AConfiguration CM2 = <<"M", -2>, <"M", 2>>
AConfiguration CN1 = <<"N", -1>, <"N", 1>>

ABuild App = <CApp, 0>
ABuild X1 = <CX1, 0>
ABuild Y1 = <CY1, 0>
ABuild Z1 = <CZ1, 0>
ABuild A1 = <CA1, 0>
ABuild A2 = <CA2, 0>
ABuild A3 = <CA3, 0>
ABuild B1 = <CB1, 0>
ABuild B2 = <CB2, 0>
ABuild C1 = <CC1, 0>
ABuild C2 = <CC2, 0>
ABuild M1 = <CM1, 0>
ABuild M2 = <CM2, 0>
ABuild N1 = <CN1, 0>

set[ABuild] builds =
	{App, X1, Y1, Z1, A1, A2, A3, B1, B2, C1, C2, M1, M2, N1}

ABOM boms = {
	<App, IX1>,
	<App, IY1>,
	<App, IZ1>,
	<X1, IA1>,
	<Y1, IA2>,
	<Z1, IA3>,
	<Z1, IC2>,
	<A1, IB1>,
	<A1, IC1>,
	<A2, IB2>,
	<A2, IC1>,
	<A3, IB2>,
	<A3, IC1>,
	<B1, IM1>,
	<B2, IM2>,
	<C2, IN1>
}

		 */
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	
	

}
