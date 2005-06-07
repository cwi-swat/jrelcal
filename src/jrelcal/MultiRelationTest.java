/*
 * Created on Jan 25, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jrelcal;

import junit.framework.TestCase;

/**
 * @author storm
 * @class jrelcal.ImmutableMultiRelationTest
 */
public class MultiRelationTest extends TestCase {
	
	protected MultiRelation<Integer,Integer> emptyRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> aRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> aaRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> aaaRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> bRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> cRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> singletonRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> treeRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> diamondRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> oneOneRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> oneTwoRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> oneTwoProductRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> oneTwoOneTwoRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> oneTwoOneTwoTwoThreeRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> oneThreeOneThreeRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> twoThreeRelation = new MultiRelation<Integer,Integer>();	
	protected MultiRelation<Integer,Integer> twoOneThreeTwoRelation = new MultiRelation<Integer,Integer>();	
	protected MultiRelation<Integer,Integer> oneTwoTwoOneRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> oneTwoTwoThreeRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> twoOneRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> twoFourThreeFiveRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> oneFourTwoFiveRelation = new MultiRelation<Integer,Integer>();
	protected MultiRelation<Integer,Integer> oneTwoX6Relation = new MultiRelation<Integer,Integer>();
	
	

	protected OrderedBag<Integer> emptyBag = new OrderedBag<Integer>();
	protected OrderedBag<Integer> oneBag = new OrderedBag<Integer>();
	protected OrderedBag<Integer> oneOneBag = new OrderedBag<Integer>();
	protected OrderedBag<Integer> oneOneOneBag = new OrderedBag<Integer>();
	protected OrderedBag<Integer> twoBag = new OrderedBag<Integer>();
	protected OrderedBag<Integer> twoTwoBag = new OrderedBag<Integer>();
	protected OrderedBag<Integer> threeBag = new OrderedBag<Integer>();
	protected OrderedBag<Integer> oneTwoBag = new OrderedBag<Integer>();
	protected OrderedBag<Integer> oneTwoThreeBag = new OrderedBag<Integer>();
	protected OrderedBag<Integer> twoThreeBag = new OrderedBag<Integer>();
	protected OrderedBag<Integer> fourFiveBag = new OrderedBag<Integer>();
	
	
	public MultiRelationTest(String string) {
		super(string);
	}

	protected OrderedBag<Integer> empty() {
		return new OrderedBag<Integer>();
	}
	
	protected Pair<Integer,Integer> pair(int x, int y) {
		return new Pair<Integer,Integer>(x, y);
	}
	
	protected void setUp() {
		oneBag.addAdditive(1);
		oneOneBag.addAdditive(1);
		oneOneBag.addAdditive(1);
		oneOneOneBag.addAdditive(1);
		oneOneOneBag.addAdditive(1);
		oneOneOneBag.addAdditive(1);
		twoBag.addAdditive(2);
		twoTwoBag.addAdditive(2);
		twoTwoBag.addAdditive(2);
		threeBag.addAdditive(3);
		oneTwoBag.addAdditive(1);
		oneTwoBag.addAdditive(2);
		oneTwoThreeBag.addAdditive(1);
		oneTwoThreeBag.addAdditive(2);
		oneTwoThreeBag.addAdditive(3);
		twoThreeBag.addAdditive(2);
		twoThreeBag.addAdditive(3);
		fourFiveBag.addAdditive(4);
		fourFiveBag.addAdditive(5);
		
		singletonRelation.addAdditive(pair(1,1));
		
		treeRelation.addAdditive(pair(1,2));
		treeRelation.addAdditive(pair(1,3));

		diamondRelation.addAdditive(pair(1,2));
		diamondRelation.addAdditive(pair(1,3));
		diamondRelation.addAdditive(pair(2,4));
		diamondRelation.addAdditive(pair(3,4));
		
		oneTwoRelation.addAdditive(pair(1,2));
		oneOneRelation.addAdditive(pair(1,1));

		oneTwoProductRelation.addAdditive(pair(1,2));
		oneTwoProductRelation.addAdditive(pair(2,1));
		oneTwoProductRelation.addAdditive(pair(2,2));
		oneTwoProductRelation.addAdditive(pair(1,1));

		oneTwoOneTwoRelation.addAdditive(pair(1,2));
		oneTwoOneTwoRelation.addAdditive(pair(1,2));

		oneTwoOneTwoTwoThreeRelation.addAdditive(pair(1,2));
		oneTwoOneTwoTwoThreeRelation.addAdditive(pair(1,2));
		oneTwoOneTwoTwoThreeRelation.addAdditive(pair(2,3));

		oneThreeOneThreeRelation.addAdditive(pair(1,3));
		oneThreeOneThreeRelation.addAdditive(pair(1,3));

		
		twoThreeRelation.addAdditive(pair(2,3));

		twoOneThreeTwoRelation.addAdditive(pair(2,1));
		twoOneThreeTwoRelation.addAdditive(pair(3,2));

		oneTwoTwoOneRelation.addAdditive(pair(1,2));
		oneTwoTwoOneRelation.addAdditive(pair(2,1));
		
		oneTwoTwoThreeRelation.addAdditive(pair(1,2));
		oneTwoTwoThreeRelation.addAdditive(pair(2,3));
		
		twoOneRelation.addAdditive(pair(2,1));
		
		twoFourThreeFiveRelation.addAdditive(pair(2,4));
		twoFourThreeFiveRelation.addAdditive(pair(3,5));
		
		oneFourTwoFiveRelation.addAdditive(pair(1,4));
		oneFourTwoFiveRelation.addAdditive(pair(2,5));		

		for (int i = 0; i < 6; i++)
			oneTwoX6Relation.addAdditive(pair(1,2));
		
		aRelation.addAdditive(pair(1,2));
		aRelation.addAdditive(pair(3,4));
		aRelation.addAdditive(pair(4,5));
		aaRelation.addAdditive(pair(1,2));
		aaRelation.addAdditive(pair(3,4));
		aaRelation.addAdditive(pair(4,5));
		aaRelation.addAdditive(pair(1,2));
		aaRelation.addAdditive(pair(3,4));
		aaRelation.addAdditive(pair(4,5));

		aaaRelation.addAdditive(pair(1,2));
		aaaRelation.addAdditive(pair(3,4));
		aaaRelation.addAdditive(pair(4,5));
		aaaRelation.addAdditive(pair(1,2));
		aaaRelation.addAdditive(pair(3,4));
		aaaRelation.addAdditive(pair(4,5));
		aaaRelation.addAdditive(pair(1,2));
		aaaRelation.addAdditive(pair(3,4));
		aaaRelation.addAdditive(pair(4,5));
		bRelation.addAdditive(pair(10,20));
		bRelation.addAdditive(pair(20,30));
		bRelation.addAdditive(pair(20,50));
		bRelation.addAdditive(pair(40,50));
		cRelation.addAdditive(pair(100,200));
		cRelation.addAdditive(pair(333,30));
		cRelation.addAdditive(pair(1,50));
	}

	
	public void testEqualityEmpty() {
		assertTrue(emptyRelation.equals(emptyRelation));
	}


	public void testEqualityNonEmptySingleMultiplicity() {
		assertTrue(oneTwoRelation.equals(oneTwoRelation));
	}
	
	public void testEqualityMixed() {
		assertFalse(oneTwoRelation.equals(emptyRelation));
	}

	public void testEqualitySameElementsDifferentMultiplicities() {
		assertFalse(oneTwoRelation.equals(oneTwoOneTwoRelation));
	}

	public void testEqualitySameElementsSameMultiplicitiesMany() {
		assertTrue(oneTwoOneTwoRelation.equals(oneTwoOneTwoRelation));
	}

	/*
	 * additive union
	 */
	
	public void testAdditiveUnionUnitRight() {
		assertEquals(aRelation.additiveUnion(emptyRelation), aRelation);
	}
	
	public void testAdditiveUnionUnitLeft() {
		assertEquals(emptyRelation.additiveUnion(aRelation), aRelation);
	}
	
	public void testAdditiveUnionAssociativity() {
		assertEquals(aRelation.additiveUnion(bRelation.additiveUnion(cRelation)), 
					aRelation.additiveUnion(bRelation).additiveUnion(cRelation));
	}
	
	public void testAdditiveUnionCommutativity() {
		assertEquals(aRelation.additiveUnion(bRelation), bRelation.additiveUnion(aRelation));
	}
	
	public void testAdditiveUnionNonIdempotency1() {
		assertEquals(aaRelation, aRelation.additiveUnion(aRelation));
	}

	public void testAdditiveUnionNonIdempotency2() {
		assertFalse(aRelation.equals(aRelation.additiveUnion(aRelation)));
	}

	public void testAdditiveUnionNonConsumption1() {
		assertFalse(oneTwoTwoThreeRelation.equals(oneTwoTwoThreeRelation.additiveUnion(oneTwoRelation)));
	}

	public void testAdditiveUnionNonConsumption2() {
		assertEquals(aaaRelation, aaRelation.additiveUnion(aRelation));
	}

	/*
	 * maximal union
	 */
	
	public void testMaximalUnionUnitRight() {
		assertEquals(aRelation.maximalUnion(emptyRelation), aRelation);
	}
	
	public void testMaximalUnionUnitLeft() {
		assertEquals(emptyRelation.maximalUnion(aRelation), aRelation);
	}
	
	public void testMaximalUnionAssociativity() {
		assertEquals(aRelation.maximalUnion(bRelation.maximalUnion(cRelation)), 
					aRelation.maximalUnion(bRelation).maximalUnion(cRelation));
	}
	
	public void testMaximalUnionCommutativity() {
		assertEquals(aRelation.maximalUnion(bRelation), bRelation.maximalUnion(aRelation));
	}
	
	public void testMaximalUnionIdempotency() {
		assertEquals(aRelation, aRelation.maximalUnion(aRelation));
	}

	public void testMaximalUnionNonIdempotency() {
		assertEquals(aaRelation, aRelation.maximalUnion(aaRelation));
	}

	public void testMaximalUnionConsumption() {
		assertEquals(oneTwoTwoThreeRelation, oneTwoTwoThreeRelation.maximalUnion(oneTwoRelation));
	}

	
	/*
	 * difference
	 */

	public void testDifferenceEmptyMinusX() {
		assertEquals(emptyRelation, emptyRelation.difference(aRelation));
	}
	
	public void testDifferenceXMinusEmpty() {
		assertEquals(aRelation, aRelation.difference(emptyRelation));
	}
	
	public void testDifferenceXminusX() {
		assertEquals(emptyRelation, aRelation.difference(aRelation));
	}

	public void testDifferenceXxMinusXx() {
		assertEquals(emptyRelation, aaRelation.difference(aaRelation));
	}
	
	public void testDifferenceXminusSubsetY() {
		assertEquals(twoThreeRelation, oneTwoTwoThreeRelation.difference(oneTwoRelation));
	}

	public void testDifferenceXminusSupersetY() {
		assertEquals(emptyRelation, oneTwoRelation.difference(oneTwoTwoThreeRelation));
	}
	
	public void testDifferenceXminusNonOverlappingY() {
		assertEquals(aRelation.difference(bRelation), aRelation);
	}
	
	public void testDifferenceXxxMinusX() {
		assertEquals(aaRelation, aaaRelation.difference(aRelation));
	}
	
	public void testDifferenceXMinusXxx() {
		assertEquals(emptyRelation, aRelation.difference(aaaRelation));
	}
	
	
	/*
	 * intersection
	 */
	
	public void testIntersectionEmptyThroughX() {
		assertEquals(emptyRelation, emptyRelation.intersection(aRelation));
	}
	
	public void testIntersectionXthroughEmpty() {
		assertEquals(emptyRelation, aRelation.intersection(emptyRelation));
	}

	public void testIntersectionXxthroughEmpty() {
		assertEquals(emptyRelation, aaRelation.intersection(emptyRelation));
	}

	public void testIntersectionXthroughX() {
		assertEquals(aRelation, aRelation.intersection(aRelation));
	}

	public void testIntersectionXxthroughXx() {
		assertEquals(aaRelation, aaRelation.intersection(aaRelation));
	}

	public void testIntersectionXxthroughXxx() {
		assertEquals(aaRelation, aaRelation.intersection(aaaRelation));
	}

	public void testIntersectionXthroughSubsetY() {
		assertEquals(oneTwoRelation, oneTwoTwoThreeRelation.intersection(oneTwoRelation));
	}

	public void testIntersectionXthroughSupersetY() {
		assertEquals(oneTwoRelation, oneTwoRelation.intersection(oneTwoTwoThreeRelation));
	}
	
	public void testIntersectionXthroughNonOverlappingY() {
		assertEquals(emptyRelation, aRelation.intersection(bRelation));
	}

	public void testIntersectionXthroughNonOverlappingYWithDuplicates() {
		assertEquals(emptyRelation, aaRelation.intersection(bRelation));
	}
	

	
	/*
	 * domain
	 */
	
	public void testDomainEmpty() {
		assertEquals(emptyBag, emptyRelation.domain());
	}
	
	public void testDomainSingle() {
		assertEquals(oneBag, oneTwoRelation.domain());
	}
	
	public void testDomainMany() {
		assertEquals(oneTwoBag, oneTwoTwoThreeRelation.domain());
	}

	public void testDomainManyMultiplicities() {
		assertEquals(oneOneBag, oneTwoOneTwoRelation.domain());
	}

	/*
	 * range
	 */
	
	public void testRangeEmpty() {
		assertEquals(emptyBag, emptyRelation.range());
	}
	
	public void testRangeSingle() {
		assertEquals(twoBag, oneTwoRelation.range());
	}
	
	public void testRangeMany() {
		assertEquals(twoThreeBag, oneTwoTwoThreeRelation.range());
	}

	public void testRangeManyMultiplicities() {
		assertEquals(twoTwoBag, oneTwoOneTwoRelation.range());
	}

	/*
	 * domainRestriction
	 */
	
	public void testDomainRestrictionEmptyEmpty() {
		assertEquals(emptyRelation, emptyRelation.domainRestriction(emptyBag));
	}

	public void testDomainRestrictionEmptySingle() {
		assertEquals(emptyRelation, emptyRelation.domainRestriction(oneBag));
	}

	public void testDomainRestrictionSingleEmpty() {
		assertEquals(emptyRelation, oneTwoRelation.domainRestriction(emptyBag));
	}
	
	public void testDomainRestrictionSingleSingleNotInDomain() {
		assertEquals(emptyRelation, oneTwoRelation.domainRestriction(twoBag));
	}
	
	public void testDomainRestrictionSingleSingleInDomainWithoutChange() {
		assertEquals(oneTwoRelation, oneTwoRelation.domainRestriction(oneBag));
	}

	public void testDomainRestrictionManySingleInDomainWithChange() {
		assertEquals(oneTwoRelation, oneTwoTwoThreeRelation.domainRestriction(oneBag));
	}

	public void testDomainRestrictionManyManyInDomainWithoutChange() {
		assertEquals(oneTwoTwoThreeRelation, oneTwoTwoThreeRelation.domainRestriction(oneTwoBag));
	}

	public void testDomainRestrictionManyManyInDomainWithDuplicatesWithoutChange() {
		assertEquals(oneTwoOneTwoRelation, oneTwoOneTwoRelation.domainRestriction(oneBag));
	}

	public void testDomainRestrictionManyManyInDomainWithDuplicatesWithChange() {
		assertEquals(oneTwoOneTwoRelation, oneTwoOneTwoTwoThreeRelation.domainRestriction(oneBag));
	}
	

	/*
	 * rangeRestriction
	 */
	
	public void testRangeRestrictionEmptyEmpty() {
		assertEquals(emptyRelation, emptyRelation.rangeRestriction(emptyBag));
	}

	public void testRangeRestrictionEmptySingle() {
		assertEquals(emptyRelation, emptyRelation.rangeRestriction(oneBag));
	}

	public void testRangeRestrictionSingleEmpty() {
		assertEquals(emptyRelation, oneTwoRelation.rangeRestriction(emptyBag));
	}
	
	public void testRangeRestrictionSingleSingleNotInRange() {
		assertEquals(emptyRelation, oneTwoRelation.rangeRestriction(oneBag));
	}
	
	public void testRangeRestrictionSingleSingleInRangeWithoutChange() {
		assertEquals(oneTwoRelation, oneTwoRelation.rangeRestriction(twoBag));
	}

	public void testRangeRestrictionManySingleInRangeWithChange() {
		assertEquals(oneTwoRelation, oneTwoTwoThreeRelation.rangeRestriction(twoBag));
	}

	public void testRangeRestrictionManyManyInDomainWithoutChange() {
		assertEquals(oneTwoTwoThreeRelation, oneTwoTwoThreeRelation.rangeRestriction(twoThreeBag));
	}
	
	/*
	 * domainExclusion
	 */
	
	public void testDomainExclusionEmptyEmpty() {
		assertEquals(emptyRelation, emptyRelation.domainExclusion(emptyBag));
	}

	public void testDomainExclusionEmptySingle() {
		assertEquals(emptyRelation, emptyRelation.domainExclusion(oneBag));
	}

	public void testDomainExclusionSingleEmpty() {
		assertEquals(oneTwoRelation, oneTwoRelation.domainExclusion(emptyBag));
	}
	
	public void testDomainExclusionSingleSingleNotInDomain() {
		assertEquals(oneTwoRelation, oneTwoRelation.domainExclusion(twoBag));
	}
	
	public void testDomainExclusionSingleSingleInDomainWithChange() {
		assertEquals(emptyRelation, oneTwoRelation.domainExclusion(oneBag));
	}

	public void testDomainExclusionManySingleInDomainWithChange() {
		assertEquals(twoThreeRelation, oneTwoTwoThreeRelation.domainExclusion(oneBag));
	}

	public void testDomainExclusionManyManyInDomainWithChange() {
		assertEquals(emptyRelation, oneTwoTwoThreeRelation.domainExclusion(oneTwoBag));
	}

	public void testDomainExclusionManyManyInDomainWithDuplicatesWithoutChange1() {
		assertEquals(oneTwoOneTwoRelation, oneTwoOneTwoRelation.domainExclusion(threeBag));
	}
	
	public void testDomainExclusionManyManyInDomainWithDuplicatesWithoutChange2() {
		assertEquals(oneTwoOneTwoTwoThreeRelation, oneTwoOneTwoTwoThreeRelation.domainExclusion(threeBag));
	}

	public void testDomainExclusionManyManyInDomainWithDuplicatesWithChange1() {
		assertEquals(emptyRelation, oneTwoOneTwoRelation.domainExclusion(oneBag));
	}

	public void testDomainExclusionManyManyInDomainWithDuplicatesWithChange2() {
		assertEquals(twoThreeRelation, oneTwoOneTwoTwoThreeRelation.domainExclusion(oneBag));
	}

	public void testDomainExclusionManyManyInDomainWithDuplicatesWithChange3() {
		assertEquals(twoThreeRelation, oneTwoOneTwoTwoThreeRelation.domainExclusion(oneOneBag));
	}


	/*
	 * rangeExclusion
	 */
	
	public void testRangeExclusionEmptyEmpty() {
		assertEquals(emptyRelation, emptyRelation.rangeExclusion(emptyBag));
	}

	public void testRangeExclusionEmptySingle() {
		assertEquals(emptyRelation, emptyRelation.rangeExclusion(oneBag));
	}

	public void testRangeExclusionSingleEmpty() {
		assertEquals(oneTwoRelation, oneTwoRelation.rangeExclusion(emptyBag));
	}
	
	public void testRangeExclusionSingleSingleNotInRange() {
		assertEquals(oneTwoRelation, oneTwoRelation.rangeExclusion(oneBag));
	}
	
	public void testRangeExclusionSingleSingleInRangeWithChange() {
		assertEquals(emptyRelation, oneTwoRelation.rangeExclusion(twoBag));
	}

	public void testRangeExclusionManySingleInRangeWithChange() {
		assertEquals(twoThreeRelation, oneTwoTwoThreeRelation.rangeExclusion(twoBag));
	}

	public void testRangeExclusionManyManyInRangeWithChange() {
		assertEquals(emptyRelation, oneTwoTwoThreeRelation.rangeExclusion(twoThreeBag));
	}
	
	/*
	 * Composition
	 */
	
	public void testCompositionEmptyWithEmpty() {
		assertEquals(emptyRelation, emptyRelation.compose(emptyRelation));
	}

	public void testCompositionXWithEmpty() {
		assertEquals(emptyRelation, aRelation.compose(emptyRelation));
	}

	public void testCompositionEmptyWithX() {
		assertEquals(emptyRelation, emptyRelation.compose(aRelation));
	}

	public void testCompositionXwithYUnrelated() {
		assertEquals(emptyRelation, aRelation.compose(bRelation));
	}
	
	public void testCompositionXwithYRelated() {
		assertEquals(oneFourTwoFiveRelation, oneTwoTwoThreeRelation.compose(twoFourThreeFiveRelation));
	}
	
	public void testCompositionXwithYRelatedWithDuplicates() {
		assertEquals(oneThreeOneThreeRelation, oneTwoOneTwoRelation.compose(twoThreeRelation));
	}
	
	/*
	 * Cardinality
	 */
	
	public void testCardinalityEmpty() {
		assertEquals(0, emptyRelation.cardinality());
	}
	
	public void testCardinalitySingle() {
		assertEquals(1, singletonRelation.cardinality());
	}
	
	public void testCardinalityMany() {
		assertEquals(3, aRelation.cardinality());
	}

	public void testCardinalityManyWithDuplicates() {
		assertEquals(6, aaRelation.cardinality());
	}

	/*
	 * Cartesian product
	 */
	
	public void testCartesianProductEmptyxEmpty() {
		assertEquals(emptyRelation, MultiRelation.cartesianProduct(emptyBag, emptyBag));
	}
	
	public void testCartesianProductEmptyxX() {
		assertEquals(emptyRelation, MultiRelation.cartesianProduct(emptyBag, oneTwoBag));
	}
	
	public void testCartesianProductXxY() {
		assertEquals(oneTwoProductRelation, MultiRelation.cartesianProduct(oneTwoBag, oneTwoBag));
	}
	
	public void testCartesianProductXXXxYY() {
		assertEquals(oneTwoX6Relation, MultiRelation.cartesianProduct(oneOneOneBag, twoTwoBag));
	}
	
	/*
	 * Inverse
	 */
	
	public void testInverseEmpty() {
		assertEquals(emptyRelation, emptyRelation.inverse());
	}

	public void testInverseIdentity() {
		assertEquals(oneOneRelation, oneOneRelation.inverse());
	}

	public void testInverseDiverse() {
		assertEquals(twoOneThreeTwoRelation, oneTwoTwoThreeRelation.inverse());
	}

	

}
