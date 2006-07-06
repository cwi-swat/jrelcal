package jrelcal.bom;

import jrelcal.Pair;
import jrelcal.sets.Relation;
import jrelcal.sets.Set;
import junit.framework.TestCase;

public class BillsOfMaterialsTest extends TestCase {
	Subsystem SApp = new Subsystem("App");
	Subsystem SX = new Subsystem("X");
	Subsystem SY = new Subsystem("Y");
	Subsystem SZ = new Subsystem("Z");
	Subsystem SA = new Subsystem("A");
	Subsystem SB = new Subsystem("B");
	Subsystem SC = new Subsystem("C");
	Subsystem SM = new Subsystem("M");
	Subsystem SN = new Subsystem("N");

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

	
	Set<Subsystem> subsystems;
	Set<Interface> interfaces;
	Set<Body> bodies;
	Set<Configuration> configurations;
	Set<Build> builds;
	Relation<Build, Interface> boms;
	Relation<Build, Build> systemBoms;


	public BillsOfMaterialsTest(String string) {
		super(string);
	}
	
	public void testSingletonBuild() {
		BillsOfMaterials bom = new BillsOfMaterials(new Set<Build>(N1), new Relation<Build,Interface>());
		assertEquals(bom.getAllSystemBoms(), new Set<Relation<Build,Build>>());
	}
	

	public void testOneDependencyBuild() {
		Set<Build> builds = new Set<Build>();
		builds.add(C2);
		builds.add(N1);
		Relation<Build,Interface> boms = new Relation<Build, Interface>();
		boms.add(new Pair<Build,Interface>(C2, IN1));
		BillsOfMaterials bom = new BillsOfMaterials(builds, boms);
		Relation<Build,Build> sysBom = new Relation<Build, Build>();
		sysBom.add(new Pair<Build,Build>(C2, N1));
		assertEquals(bom.getAllSystemBoms(), new Set<Relation<Build,Build>>(sysBom));
	}

	public void testOneDependencyTwoPossibilities() {
		Set<Build> builds = new Set<Build>();
		builds.add(B1);
		builds.add(M1);
		builds.add(M2);
		Relation<Build,Interface> boms = new Relation<Build, Interface>();
		boms.add(new Pair<Build,Interface>(B1, IM1));
		BillsOfMaterials bom = new BillsOfMaterials(builds, boms);
		Set<Relation<Build,Build>> expected = new Set<Relation<Build,Build>>();
		Relation<Build,Build> sysBom = new Relation<Build, Build>();
		sysBom.add(new Pair<Build,Build>(B1, M1));
		expected.add(sysBom);
		
		sysBom = new Relation<Build, Build>();
		sysBom.add(new Pair<Build,Build>(B1, M2));
		expected.add(sysBom);
		assertEquals(bom.getAllSystemBoms(), expected);
	}
	
	public void testTwoDependenciesOnePossibility() {
		Set<Build> builds = new Set<Build>();
		builds.add(App1);
		builds.add(X1);
		builds.add(Y1);
		Relation<Build,Interface> boms = new Relation<Build, Interface>();
		boms.add(new Pair<Build,Interface>(App1, IX1));
		boms.add(new Pair<Build,Interface>(App1, IY1));
		BillsOfMaterials bom = new BillsOfMaterials(builds, boms);
		Set<Relation<Build,Build>> expected = new Set<Relation<Build,Build>>();
		Relation<Build,Build> sysBom = new Relation<Build, Build>();
		sysBom.add(new Pair<Build,Build>(App1, X1));
		sysBom.add(new Pair<Build,Build>(App1, Y1));
		expected.add(sysBom);
		assertEquals(bom.getAllSystemBoms(), expected);
	}
	
	public void testTwoDependenciesTwoPossibilities() {
		Set<Build> builds = new Set<Build>();
		builds.add(App1);
		builds.add(A1);
		builds.add(A2);
		builds.add(Y1);
		Relation<Build,Interface> boms = new Relation<Build, Interface>();
		boms.add(new Pair<Build,Interface>(App1, IA1));
		boms.add(new Pair<Build,Interface>(App1, IY1));
		BillsOfMaterials bom = new BillsOfMaterials(builds, boms);
		Set<Relation<Build,Build>> expected = new Set<Relation<Build,Build>>();
		Relation<Build,Build> sysBom = new Relation<Build, Build>();
		sysBom.add(new Pair<Build,Build>(App1, A1));
		sysBom.add(new Pair<Build,Build>(App1, Y1));
		expected.add(sysBom);
		sysBom = new Relation<Build, Build>();
		sysBom.add(new Pair<Build,Build>(App1, A2));
		sysBom.add(new Pair<Build,Build>(App1, Y1));
		expected.add(sysBom);
		assertEquals(bom.getAllSystemBoms(), expected);
	}
	
	
	protected void setUp() {
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
	
	}

	


}
