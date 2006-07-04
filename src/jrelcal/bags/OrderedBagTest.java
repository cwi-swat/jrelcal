/*
 * Created on Jan 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jrelcal.bags;

import junit.framework.TestCase;

/**
 * @author storm
 * @class jrelcal.TreeBagTest
 */
public class OrderedBagTest extends TestCase {
	private OrderedBag<Integer> emptyBag;
	private OrderedBag<Integer> oneBag;
	private OrderedBag<Integer> oneOneBag;
	private OrderedBag<Integer> oneOneOneBag;
	private OrderedBag<Integer> twoBag;
	private OrderedBag<Integer> oneTwoThreeBag;
	private OrderedBag<Integer> oneOneTwoTwoBag;
	private OrderedBag<Integer> oneOneTwoTwoThreeBag;
	private OrderedBag<Integer> oneOneTwoTwoThreeBag2;
	private OrderedBag<Integer> oneOneTwoTwoThreeThreeBag;
	private OrderedBag<Integer> twoThreeFourBag;
	private OrderedBag<Integer> oneTwoBag;
	private OrderedBag<Integer> fourFiveSixBag;
	private VertexBag vertexBagOneTwoThree;
	private VertexBag vertexBagZeroToFive;

	public OrderedBagTest(String string) {
		super(string);
	}
	
	protected void setUp() {
		emptyBag = new OrderedBag<Integer>();
		oneBag = new OrderedBag<Integer>();
		oneOneBag = new OrderedBag<Integer>();
		oneOneOneBag = new OrderedBag<Integer>();
		twoBag = new OrderedBag<Integer>();
		oneTwoThreeBag = new OrderedBag<Integer>();
		oneOneTwoTwoBag = new OrderedBag<Integer>();
		oneOneTwoTwoThreeBag = new OrderedBag<Integer>();
		oneOneTwoTwoThreeBag2 = new OrderedBag<Integer>();
		oneOneTwoTwoThreeThreeBag = new OrderedBag<Integer>();
		twoThreeFourBag = new OrderedBag<Integer>();
		oneTwoBag = new OrderedBag<Integer>();
		fourFiveSixBag = new OrderedBag<Integer>();
		
		oneBag.add(1);
		oneOneBag.add(1);
		oneOneBag.add(1);
		oneOneOneBag.add(1);
		oneOneOneBag.add(1);
		oneOneOneBag.add(1);
		twoBag.add(2);
		
		oneOneTwoTwoBag.add(1);
		oneOneTwoTwoBag.add(1);
		oneOneTwoTwoBag.add(2);
		oneOneTwoTwoBag.add(2);
		
		for (int i = 1; i <= 3; i++) {
			oneTwoThreeBag.add(i);
			oneOneTwoTwoThreeBag.add(i);
			oneOneTwoTwoThreeBag2.add(i);
			oneOneTwoTwoThreeThreeBag.add(i);
			oneOneTwoTwoThreeThreeBag.add(i);
		}

		oneOneTwoTwoThreeBag.add(1);
		oneOneTwoTwoThreeBag.add(2);
		oneOneTwoTwoThreeBag2.add(1);
		oneOneTwoTwoThreeBag2.add(2);
		
		for (int i = 2; i <= 4; i++)
			twoThreeFourBag.add(i);

		for (int i = 1; i <= 2; i++)
			oneTwoBag.add(i);
		
		for (int i = 4; i <= 6; i++)
			fourFiveSixBag.add(i);
	
		vertexBagOneTwoThree = new VertexBag();
		vertexBagZeroToFive = new VertexBag();
		for (int i = 0; i < 3; i++) {
			vertexBagOneTwoThree.add(i);
		}
		for (int i = 0; i < 6; i++)
			vertexBagZeroToFive.add(i);

	}


	/* 
	 * cardinality 
	 */
	

	public void testCardinalityEmpty() {
		assertEquals(emptyBag.cardinality(), 0);
	}
	
	public void testCardinalityNonEmptySet() {
		assertEquals(oneTwoBag.cardinality(), 2);
	}
	
	public void testCardinalityNonEmptyBag() {
		assertEquals(oneOneTwoTwoThreeBag.cardinality(), 5);
	}
	
	/*
	 * Multiplicity
	 */

	public void testMultiplicityZero() {
		assertEquals(oneTwoThreeBag.multiplicity(4), 0);
	}

	public void testMultiplicitySingle() {
		assertEquals(oneTwoThreeBag.multiplicity(1), 1);
	}

	public void testMultiplicityMany() {
		assertEquals(oneOneTwoTwoThreeBag.multiplicity(1), 2);
	}

	/*
	 * Equality
	 */
	
	public void testEqualityEmpty() {
		assertTrue(emptyBag.equals(emptyBag));
	}

	public void testEqualityEmptyNillaryConstructor() {
		assertTrue(emptyBag.equals(new OrderedBag<Integer>()));
	}
	

	public void testEqualityNonEmpty() {
		assertTrue(oneTwoBag.equals(oneTwoBag));
	}
	
	public void testEqualityMixed() {
		assertFalse(oneTwoBag.equals(emptyBag));
	}

	public void testEqualityDuplicates() {
		assertEquals(oneOneTwoTwoThreeBag, oneOneTwoTwoThreeBag2);
	}
	
	/*
	 * maximalUnion
	 */
	
	public void testMaximalUnionUnitRight() {
		assertEquals(oneTwoBag.maximalUnion(emptyBag), oneTwoBag);
	}
	
	public void testMaximalUnionUnitLeft() {
		assertEquals(emptyBag.maximalUnion(oneTwoBag), oneTwoBag);
	}
	
	public void testMaximalUnionAssociativity() {
		assertEquals(oneTwoBag.maximalUnion(twoThreeFourBag.maximalUnion(fourFiveSixBag)), 
					oneTwoBag.maximalUnion(twoThreeFourBag).maximalUnion(fourFiveSixBag));
	}
	
	public void testMaximalUnionCommutativity() {
		assertEquals(oneTwoBag.maximalUnion(twoThreeFourBag), twoThreeFourBag.maximalUnion(oneTwoBag));
	}
	
	public void testMaximalUnionIdempotency() {
		assertEquals(oneTwoBag, oneTwoBag.maximalUnion(oneTwoBag));
	}

	public void testMaximalUnionNonIdempotency1() {
		assertEquals(oneOneTwoTwoBag, oneTwoBag.maximalUnion(oneOneTwoTwoBag));
	}

	public void testMaximalUnionNonIdempotency2() {
		assertEquals(oneOneTwoTwoBag, oneOneTwoTwoBag.maximalUnion(oneTwoBag));
	}

	
	public void testMaximalUnionConsumption() {
		assertEquals(oneTwoThreeBag, oneTwoThreeBag.maximalUnion(oneTwoBag));
	}

	public void testMaximalUnionNonConsumption1() {
		assertEquals(oneOneTwoTwoThreeBag, oneTwoThreeBag.maximalUnion(oneOneTwoTwoBag));
	}

	public void testMaximalUnionNonConsumption2() {
		assertEquals(oneOneTwoTwoThreeBag, oneOneTwoTwoBag.maximalUnion(oneTwoThreeBag));
	}

	
	/*
	 * additiveUnion
	 */
	
	public void testAdditiveUnionUnitRight() {
		assertEquals(oneTwoBag.additiveUnion(emptyBag), oneTwoBag);
	}
	
	public void testAdditiveUnionUnitLeft() {
		assertEquals(emptyBag.additiveUnion(oneTwoBag), oneTwoBag);
	}
	
	public void testAdditiveUnionAssociativity() {
		assertEquals(oneTwoBag.additiveUnion(twoThreeFourBag.additiveUnion(fourFiveSixBag)), 
					oneTwoBag.additiveUnion(twoThreeFourBag).additiveUnion(fourFiveSixBag));
	}
	
	public void testAdditiveUnionCommutativity() {
		assertEquals(oneTwoBag.additiveUnion(twoThreeFourBag), twoThreeFourBag.additiveUnion(oneTwoBag));
	}
	
	public void testAdditiveUnionNonIdempotency() {
		assertEquals(oneOneTwoTwoBag, oneTwoBag.additiveUnion(oneTwoBag));
	}

	
	public void testAdditiveUnionNonConsumption() {
		assertEquals(oneOneTwoTwoThreeBag, oneTwoThreeBag.additiveUnion(oneTwoBag));
	}

	
	
	/*
	 * difference
	 */

	public void testDifferenceEmptyMinusX() {
		assertEquals(emptyBag, emptyBag.difference(oneTwoBag));
	}
	
	public void testDifferenceXMinusEmpty() {
		assertEquals(oneTwoBag, oneTwoBag.difference(emptyBag));
	}
	
	public void testDifferenceXminusX() {
		assertEquals(oneTwoBag.difference(oneTwoBag), emptyBag);
	}
	
	public void testDifferenceXminusSubsetY() {
		assertEquals(oneTwoBag.difference(oneBag), twoBag);
	}

	public void testDifferenceXminusSupersetY() {
		assertEquals(twoBag.difference(oneTwoBag), emptyBag);
	}
	
	public void testDifferenceXminusNonOverlappingY() {
		assertEquals(oneTwoThreeBag.difference(fourFiveSixBag), oneTwoThreeBag);
	}

	public void testDifferenceWithDuplicatesEmptyMinusX() {
		assertEquals(emptyBag, emptyBag.difference(oneOneTwoTwoBag));
	}
	
	public void testDifferenceWithDuplicatesXMinusEmpty() {
		assertEquals(oneOneTwoTwoBag, oneOneTwoTwoBag.difference(emptyBag));
	}
	
	public void testDifferenceWithDuplicatesXminusX() {
		assertEquals(oneOneTwoTwoBag.difference(oneOneTwoTwoBag), emptyBag);
	}
	
	public void testDifferenceWithDuplicatesXminusSubsetY() {
		assertEquals(oneOneTwoTwoBag.difference(oneTwoBag), oneTwoBag);
	}

	public void testDifferenceWithDuplicatesXminusSupersetY() {
		assertEquals(oneOneTwoTwoBag.difference(oneOneTwoTwoThreeBag), emptyBag);
	}
	
	public void testDifferenceWithDuplicatesXminusNonOverlappingY() {
		assertEquals(oneOneTwoTwoBag.difference(fourFiveSixBag), oneOneTwoTwoBag);
	}

	/*
	 * intersection
	 */
	
	public void testIntersectionEmptyThroughX() {
		assertEquals(emptyBag.intersection(oneTwoBag), emptyBag);
	}
	
	public void testIntersectionXthroughEmpty() {
		assertEquals(oneTwoBag.intersection(emptyBag), emptyBag);
	}
	
	public void testIntersectionXthroughX() {
		assertEquals(oneTwoBag.intersection(oneTwoBag), oneTwoBag);
	}
	
	public void testIntersectionXthroughSubsetY() {
		assertEquals(oneTwoBag.intersection(oneBag), oneBag);
	}

	public void testIntersectionXthroughSupersetY() {
		assertEquals(twoBag.intersection(oneTwoBag), twoBag);
	}
	
	public void testIntersectionXthroughNonOverlappingY() {
		assertEquals(oneTwoThreeBag.intersection(fourFiveSixBag), emptyBag);
	}
	
	public void testIntersectionWithDuplicatesEmptyThroughX() {
		assertEquals(emptyBag.intersection(oneOneTwoTwoBag), emptyBag);
	}
	
	public void testIntersectionWithDuplicatesXthroughEmpty() {
		assertEquals(oneOneTwoTwoBag.intersection(emptyBag), emptyBag);
	}
	
	public void testIntersectionWithDuplicatesXthroughX() {
		assertEquals(oneTwoBag.intersection(oneTwoBag), oneTwoBag);
	}
	
	public void testIntersectionWithDuplicatesXthroughSubsetY() {
		assertEquals(oneOneTwoTwoBag.intersection(oneTwoBag), oneTwoBag);
	}

	public void testIntersectionWithDuplicatesXthroughSupersetY() {
		assertEquals(oneTwoBag.intersection(oneOneTwoTwoBag), oneTwoBag);
	}
	
	public void testIntersectionWithDuplicatesXthroughNonOverlappingY() {
		assertEquals(oneOneTwoTwoThreeBag.intersection(fourFiveSixBag), emptyBag);
	}
	
	
	/*
	 * containsAll
	 */
	
	public void testContainsAllXEmpty() {
		assertTrue(oneTwoBag.containsAll(emptyBag));
	}
	
	public void testContainsAllEmptyX() {
		assertFalse(emptyBag.containsAll(oneTwoBag));
	}
	
	
	public void testContainsAllXY() {
		assertTrue(oneTwoBag.containsAll(oneBag));
	}
	
	public void testContainsAllYX() {
		assertFalse(oneBag.containsAll(oneTwoBag));
	}
	
	public void testContainsAllWithDuplicatesXY() {
		assertTrue(oneOneTwoTwoBag.containsAll(oneTwoBag));
	}

	public void testContainsAllWithDuplicatesYX() {
		assertFalse(oneTwoBag.containsAll(oneOneTwoTwoBag));
	}
	

	
}
