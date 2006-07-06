/*
 * Created on Jan 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jrelcal.sets;

import junit.framework.TestCase;

/**
 * @author storm
 * @class jrelcal.OrderedSetTest
 */
public class SetTest extends TestCase {
	protected Set<Integer> emptySet;
	protected Set<Integer> oneSet;
	protected Set<Integer> twoSet;
	protected Set<Integer> oneTwoThreeSet;
	protected Set<Integer> twoThreeFourSet;
	protected Set<Integer> oneTwoSet;
	protected Set<Integer> fourFiveSixSet;
	
	
	public SetTest(String string) {
		super(string);
	}
	
	protected void setUp() {
		emptySet = new Set<Integer>();
		oneSet = new Set<Integer>();
		twoSet = new Set<Integer>();
		oneTwoThreeSet = new Set<Integer>();
		twoThreeFourSet = new Set<Integer>();
		oneTwoSet = new Set<Integer>();
		fourFiveSixSet = new Set<Integer>();
		
		oneSet.add(1);
		twoSet.add(2);
		
		for (int i = 1; i <= 3; i++)
			oneTwoThreeSet.add(i);

		for (int i = 2; i <= 4; i++)
			twoThreeFourSet.add(i);

		for (int i = 1; i <= 2; i++)
			oneTwoSet.add(i);
		
		for (int i = 4; i <= 6; i++)
			fourFiveSixSet.add(i);
	
	}
	
	public void testEqualityEmpty() {
		assertTrue(emptySet.equals(emptySet));
	}


	public void testEqualityNonEmpty() {
		assertTrue(oneTwoSet.equals(oneTwoSet));
	}
	
	public void testEqualityMixed() {
		assertFalse(oneTwoSet.equals(emptySet));
	}

	/*
	 * union
	 */
	
	public void testUnionUnitRight() {
		assertEquals(oneTwoSet.union(emptySet), oneTwoSet);
	}
	
	public void testUnionUnitLeft() {
		assertEquals(emptySet.union(oneTwoSet), oneTwoSet);
	}
	
	public void testUnionAssociativity() {
		assertEquals(oneTwoSet.union(twoThreeFourSet.union(fourFiveSixSet)), 
					oneTwoSet.union(twoThreeFourSet).union(fourFiveSixSet));
	}
	
	public void testUnionCommutativity() {
		assertEquals(oneTwoSet.union(twoThreeFourSet), twoThreeFourSet.union(oneTwoSet));
	}
	
	public void testUnionIdempotency() {
		assertEquals(oneTwoSet, oneTwoSet.union(oneTwoSet));
	}
	
	public void testUnionConsumption() {
		assertEquals(oneTwoThreeSet, oneTwoThreeSet.union(oneTwoSet));
	}
	
	/*
	 * difference
	 */

	public void testDifferenceEmptyMinusX() {
		assertEquals(emptySet, emptySet.difference(oneTwoSet));
	}
	
	public void testDifferenceXMinusEmpty() {
		assertEquals(oneTwoSet, oneTwoSet.difference(emptySet));
	}
	
	public void testDifferenceXminusX() {
		assertEquals(oneTwoSet.difference(oneTwoSet), emptySet);
	}
	
	public void testDifferenceXminusSubsetY() {
		assertEquals(oneTwoSet.difference(oneSet), twoSet);
	}

	public void testDifferenceXminusSupersetY() {
		assertEquals(twoSet.difference(oneTwoSet), emptySet);
	}
	
	public void testDifferenceXminusNonOverlappingY() {
		assertEquals(oneTwoThreeSet.difference(fourFiveSixSet), oneTwoThreeSet);
	}
	
	/*
	 * intersection
	 */
	
	public void testIntersectionEmptyThroughX() {
		assertEquals(emptySet.intersection(oneTwoSet), emptySet);
	}
	
	public void testIntersectionXthroughEmpty() {
		assertEquals(oneTwoSet.intersection(emptySet), emptySet);
	}
	
	public void testIntersectionXthroughX() {
		assertEquals(oneTwoSet.intersection(oneTwoSet), oneTwoSet);
	}
	
	public void testIntersectionXthroughSubsetY() {
		assertEquals(oneTwoSet.intersection(oneSet), oneSet);
	}

	public void testIntersectionXthroughSupersetY() {
		assertEquals(twoSet.intersection(oneTwoSet), twoSet);
	}
	
	public void testIntersectionXthroughNonOverlappingY() {
		assertEquals(oneTwoThreeSet.intersection(fourFiveSixSet), emptySet);
	}
	

}
