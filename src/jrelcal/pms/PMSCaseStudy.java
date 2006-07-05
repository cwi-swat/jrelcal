package jrelcal.pms;

import jrelcal.Pair;
import jrelcal.sets.*;

public class PMSCaseStudy {
	
	OrderedSet<String> subsystems;
	Relation<String, Integer> interfaces;
	Relation<String, Integer> bodies;
	Relation<Pair<String,Integer>,Pair<String,Integer>> configurations;
	Relation<Pair<String,Integer>,Integer> builds;
	Relation<Pair<Pair<String,Integer>,Integer>,Pair<String,Integer>> boms;
	Relation<Pair<Pair<String,Integer>,Integer>,Pair<Pair<String,Integer>,Integer>> systemBoms;
	
//	Relation<Pair<Pair<String,Integer>,Integer>,Pair<Pair<String,Integer>,Integer>> allSystemBoms() {
//		Pair<Pair<String,Integer>,Integer> b1;
//		Pair<Pair<String,Integer>,Integer> b2;
//		Pair<String,Integer> i;
//		
//	}
//	
	
//	rel[ABuild, ABuild] sysboms = 
//		{ <b1, b2> |
//		<ABuild b1, AnInterface i>: boms,
//		ABuild b2 : builds,
//		i.subsystem == b2.configuration.interface.subsystem
//	}
//	
	/*
	 * type ASubsystem = str
type AVersion = int
type ABuildID = int
type ASymbol = str

type AnItem = <ASubsystem subsystem, AVersion version>
type AnInterface = AnItem
type ABody = AnItem
type AConfiguration = <AnInterface interface, ABody body>
type ABuild = <AConfiguration configuration, ABuildID buildId>
type ABOM = rel[ABuild, AnInterface]
type ASystemBOM = rel[ABuild, ABuild]


set[ASubsystem] subsystems = {"App", "X", "Y", "Z", "A", "B", "C", "M", "M"} 


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


rel[ABuild, ABuild] sysboms = 
	{ <b1, b2> |
		<ABuild b1, AnInterface i>: boms,
		ABuild b2 : builds,
		i.subsystem == b2.configuration.interface.subsystem
	}
		

ASystemBOM sysbom(ABuild b) =
	{ <b1, b2> | <ABuild b1, ABuild, b2> : sysboms, {b1, b2} <= sysboms*[b] }

)
comment

ASystemBOM example = sysboms %%sysbom(App)

rel[ABuild, ASubsystem] class = 
	{ <b, b.configuration.interface.subsystem> |
		ABuild b : carrier(example) }

set[set[ABuild]] space = gprod( { bs | ASubsystem s: range(class), set[ABuild] bs <- inv(class)[s] } )

set[ABuild] prune(set[ABuild] s) = { b | ABuild b: s,
		class[b] <= class[example[ (s \ {b} ) ] ] }

set[set[ABuild]] pspace = { s | set[ABuild] sol: space, set[ABuild] s <- prune(sol) }

	 */
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	
	

}
