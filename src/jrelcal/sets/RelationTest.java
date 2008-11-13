package jrelcal.sets;

import jrelcal.Pair;
import junit.framework.TestCase;

import org.apache.commons.collections15.Predicate;

public abstract class RelationTest extends TestCase {
    protected Relation<Integer, Integer> emptyRelation = this.getRelation();
    protected Relation<Integer, Integer> aRelation = this.getRelation();
    protected Relation<Integer, Integer> bRelation = this.getRelation();
    protected Relation<Integer, Integer> cRelation = this.getRelation();
    protected Relation<Integer, Integer> singletonRelation = this.getRelation();
    protected Relation<Integer, Integer> treeRelation = this.getRelation();
    protected Relation<Integer, Integer> diamondRelation = this.getRelation();
    protected Relation<Integer, Integer> oneTwoRelation = this.getRelation();
    protected Relation<Integer, Integer> oneOneRelation = this.getRelation();
    protected Relation<Integer, Integer> oneTwoProductRelation = this.getRelation();
    protected Relation<Integer, Integer> twoThreeRelation = this.getRelation();
    protected Relation<Integer, Integer> oneTwoTwoOneRelation = this.getRelation();
    protected Relation<Integer, Integer> oneTwoTwoThreeRelation = this.getRelation();
    protected Relation<Integer, Integer> twoOneThreeTwoRelation = this.getRelation();
    protected Relation<Integer, Integer> twoOneRelation = this.getRelation();
    protected Relation<Integer, Integer> twoFourThreeFiveRelation = this.getRelation();
    protected Relation<Integer, Integer> oneFourTwoFiveRelation = this.getRelation();
    protected Relation<Integer, Integer> sliceRelation = this.getRelation();
    protected Relation<Integer, Integer> sliceResultRelation = this.getRelation();
    protected Relation<Integer, Integer> sliceCycleRelation = this.getRelation();
    protected Relation<Integer, Integer> sliceCycleResultRelation = this.getRelation();
    protected Relation<Integer, Integer> sliceRelation2 = this.getRelation();
    protected Relation<Integer, Integer> tcResultRelation = this.getRelation();
    protected Relation<Integer, Integer> sliceRelation3 = this.getRelation();
    protected Relation<Integer, Integer> reachResultRelation = this.getRelation();
    protected Relation<Integer, Integer> reachResultRelation2 = this.getRelation();
    protected Relation<Integer, Integer> reachResultRelation3 = this.getRelation();

    protected OrderedSet<Integer> emptySet = new OrderedSet<Integer>();
    protected OrderedSet<Integer> oneSet = new OrderedSet<Integer>();
    protected OrderedSet<Integer> twoSet = new OrderedSet<Integer>();
    protected OrderedSet<Integer> threeSet = new OrderedSet<Integer>();
    protected OrderedSet<Integer> oneTwoSet = new OrderedSet<Integer>();
    protected OrderedSet<Integer> oneTwoThreeSet = new OrderedSet<Integer>();
    protected OrderedSet<Integer> twoThreeSet = new OrderedSet<Integer>();
    protected OrderedSet<Integer> fourFiveSet = new OrderedSet<Integer>();
    protected OrderedSet<Integer> eightNineSet = new OrderedSet<Integer>();
    protected OrderedSet<Integer> tenElevenTwelveThirteenSet = new OrderedSet<Integer>();
    
    protected Predicate<Integer> greaterThanOne;

    protected abstract Relation<Integer, Integer> getRelation();

    protected OrderedSet<Integer> empty() {
        return new OrderedSet<Integer>();
    }

    protected Pair<Integer, Integer> pair(int x, int y) {
        return new Pair<Integer, Integer>(x, y);
    }

    protected void setUp() {
        oneSet.add(1);
        twoSet.add(2);
        threeSet.add(3);
        oneTwoSet.add(1);
        oneTwoSet.add(2);
        oneTwoThreeSet.add(1);
        oneTwoThreeSet.add(2);
        oneTwoThreeSet.add(3);
        twoThreeSet.add(2);
        twoThreeSet.add(3);
        fourFiveSet.add(4);
        fourFiveSet.add(5);
        eightNineSet.add(8);
        eightNineSet.add(9);
        tenElevenTwelveThirteenSet.add(10);
        tenElevenTwelveThirteenSet.add(11);
        tenElevenTwelveThirteenSet.add(12);
        tenElevenTwelveThirteenSet.add(13);

        singletonRelation.add(pair(1, 1));

        treeRelation.add(pair(1, 2));
        treeRelation.add(pair(1, 3));

        diamondRelation.add(pair(1, 2));
        diamondRelation.add(pair(1, 3));
        diamondRelation.add(pair(2, 4));
        diamondRelation.add(pair(3, 4));

        oneTwoRelation.add(pair(1, 2));
        oneOneRelation.add(pair(1, 1));
        twoThreeRelation.add(pair(2, 3));

        oneTwoProductRelation.add(pair(1, 2));
        oneTwoProductRelation.add(pair(1, 1));
        oneTwoProductRelation.add(pair(2, 1));
        oneTwoProductRelation.add(pair(2, 2));

        oneTwoTwoOneRelation.add(pair(1, 2));
        oneTwoTwoOneRelation.add(pair(2, 1));

        oneTwoTwoThreeRelation.add(pair(1, 2));
        oneTwoTwoThreeRelation.add(pair(2, 3));

        twoOneThreeTwoRelation.add(pair(2, 1));
        twoOneThreeTwoRelation.add(pair(3, 2));

        twoOneRelation.add(pair(2, 1));

        twoFourThreeFiveRelation.add(pair(2, 4));
        twoFourThreeFiveRelation.add(pair(3, 5));

        oneFourTwoFiveRelation.add(pair(1, 4));
        oneFourTwoFiveRelation.add(pair(2, 5));
        
        greaterThanOne = new Predicate<Integer>() {
            public boolean evaluate(Integer i) {
              return i > 1;
            }
          };

        aRelation.add(pair(1, 2));
        aRelation.add(pair(3, 4));
        aRelation.add(pair(4, 5));
        bRelation.add(pair(10, 20));
        bRelation.add(pair(20, 30));
        bRelation.add(pair(20, 50));
        bRelation.add(pair(40, 50));
        cRelation.add(pair(100, 200));
        cRelation.add(pair(333, 30));
        cRelation.add(pair(1, 50));

        sliceRelation.add(pair(1, 2));
        sliceRelation.add(pair(1, 3));
        sliceRelation.add(pair(2, 6));
        sliceRelation.add(pair(3, 5));
        sliceRelation.add(pair(4, 3));
        sliceRelation.add(pair(8, 1));
        sliceRelation.add(pair(1, 9));

        sliceRelation2.add(pair(1, 3));
        sliceRelation2.add(pair(2, 4));
        sliceRelation2.add(pair(2, 5));
        sliceRelation2.add(pair(4, 1));
        sliceRelation2.add(pair(3, 4));
        sliceRelation2.add(pair(7, 6));
        sliceRelation2.add(pair(6, 5));
        sliceRelation2.add(pair(4, 8));
        sliceRelation2.add(pair(5, 9));
        sliceRelation2.add(pair(8, 10));
        sliceRelation2.add(pair(8, 11));
        sliceRelation2.add(pair(8, 12));
        sliceRelation2.add(pair(9, 12));
        sliceRelation2.add(pair(9, 13));
        sliceRelation2.add(pair(10, 14));
        sliceRelation2.add(pair(11, 10));
        sliceRelation2.add(pair(11, 14));
        sliceRelation2.add(pair(12, 15));
        sliceRelation2.add(pair(15, 16));

        tcResultRelation.add(PairSetRelation.transitiveClosure(sliceRelation2).asPairs());

        sliceRelation3.add(sliceRelation2.asPairs());

        sliceRelation3.add(pair(16, 13));

        sliceResultRelation.add(pair(1, 2));
        sliceResultRelation.add(pair(1, 3));
        sliceResultRelation.add(pair(2, 6));
        sliceResultRelation.add(pair(3, 5));
        sliceResultRelation.add(pair(1, 9));

        reachResultRelation.add(pair(1, 8));
        reachResultRelation.add(pair(2, 8));
        reachResultRelation.add(pair(2, 9));

        reachResultRelation2.add(pair(1, 10));
        reachResultRelation2.add(pair(1, 11));
        reachResultRelation2.add(pair(1, 12));
        reachResultRelation2.add(pair(2, 10));
        reachResultRelation2.add(pair(2, 11));
        reachResultRelation2.add(pair(2, 12));
        reachResultRelation2.add(pair(2, 13));

        reachResultRelation3.add(reachResultRelation2.asPairs());
        reachResultRelation3.add(pair(1, 13));

        sliceCycleRelation = new PairSetRelation<Integer, Integer>(sliceRelation.asPairs());
        sliceCycleRelation.add(pair(3, 2));

        sliceCycleResultRelation = new PairSetRelation<Integer, Integer>(sliceResultRelation
            .asPairs());
        sliceCycleResultRelation.add(pair(3, 2));
    }

    public void testEqualityEmpty() {
        assertTrue(emptyRelation.equals(emptyRelation));
    }

    public void testEqualityNonEmpty() {
        assertTrue(oneTwoRelation.equals(oneTwoRelation));
    }

    public void testEqualityMixed() {
        assertFalse(oneTwoRelation.equals(emptyRelation));
    }

    /*
     * union
     */

    public void testUnionUnitRight() {
        assertEquals(aRelation.union(emptyRelation), aRelation);
    }

    public void testUnionUnitLeft() {
        assertEquals(emptyRelation.union(aRelation), aRelation);
    }

    public void testUnionAssociativity() {
        assertEquals(aRelation.union(bRelation.union(cRelation)), aRelation.union(bRelation)
            .union(cRelation));
    }

    public void testUnionCommutativity() {
        assertEquals(aRelation.union(bRelation), bRelation.union(aRelation));
    }

    public void testUnionIdempotency() {
        assertEquals(aRelation, aRelation.union(aRelation));
    }

    public void testUnionConsumption() {
        assertEquals(oneTwoTwoThreeRelation, oneTwoTwoThreeRelation.union(oneTwoRelation));
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
        assertEquals(aRelation.difference(aRelation), emptyRelation);
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

    /*
     * intersection
     */

    public void testIntersectionEmptyThroughX() {
        assertEquals(emptyRelation.intersection(aRelation), emptyRelation);
    }

    public void testIntersectionXthroughEmpty() {
        assertEquals(aRelation.intersection(emptyRelation), emptyRelation);
    }

    public void testIntersectionXthroughX() {
        assertEquals(aRelation.intersection(aRelation), aRelation);
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

    /*
     * Relation stuff:
     * - domain, range
     * - rightImage, leftImage
     * - domainR, rangeR, domainX, rangeX
     * - composition
     * - product
     * - cardinality 
     */

    /*
     * domain
     */

    public void testDomainEmpty() {
        assertEquals(emptySet, emptyRelation.domain());
    }

    public void testDomainSingle() {
        assertEquals(oneSet, oneTwoRelation.domain());
    }

    public void testDomainMany() {
        assertEquals(oneTwoSet, oneTwoTwoThreeRelation.domain());
    }

    /*
     * range
     */

    public void testRangeEmpty() {
        assertEquals(emptySet, emptyRelation.range());
    }

    public void testRangeSingle() {
        assertEquals(twoSet, oneTwoRelation.range());
    }

    public void testRangeMany() {
        assertEquals(twoThreeSet, oneTwoTwoThreeRelation.range());
    }

    /*
     * domainRestriction
     */

    public void testDomainRestrictionEmptyEmpty() {
        assertEquals(emptyRelation, emptyRelation.domainRestriction(emptySet));
    }

    public void testDomainRestrictionEmptySingle() {
        assertEquals(emptyRelation, emptyRelation.domainRestriction(oneSet));
    }

    public void testDomainRestrictionSingleEmpty() {
        assertEquals(emptyRelation, oneTwoRelation.domainRestriction(emptySet));
    }

    public void testDomainRestrictionSingleSingleNotInDomain() {
        assertEquals(emptyRelation, oneTwoRelation.domainRestriction(twoSet));
    }

    public void testDomainRestrictionSingleSingleInDomainWithoutChange() {
        assertEquals(oneTwoRelation, oneTwoRelation.domainRestriction(oneSet));
    }

    public void testDomainRestrictionManySingleInDomainWithChange() {
        assertEquals(oneTwoRelation, oneTwoTwoThreeRelation.domainRestriction(oneSet));
    }

    public void testDomainRestrictionManyManyInDomainWithoutChange() {
        assertEquals(oneTwoTwoThreeRelation, oneTwoTwoThreeRelation
            .domainRestriction(oneTwoSet));
    }
    
    // domainRestriction with predicate
    
    public void testDomainRestrictionPredicate() {
        assertEquals(twoOneRelation, oneTwoTwoOneRelation.domainRestriction(greaterThanOne));
    }
    
    public void testDomainRestrictionEmptyResultPredicate() {
        assertEquals(emptyRelation, oneOneRelation.domainRestriction(greaterThanOne));
    }
    
    public void testDomainRestrictionOnEmptyRelationPredicate() {
        assertEquals(emptyRelation, emptyRelation.domainRestriction(greaterThanOne));
    }
    
    
    
    

    /*
     * rangeRestriction
     */

    public void testRangeRestrictionEmptyEmpty() {
        assertEquals(emptyRelation, emptyRelation.rangeRestriction(emptySet));
    }

    public void testRangeRestrictionEmptySingle() {
        assertEquals(emptyRelation, emptyRelation.rangeRestriction(oneSet));
    }

    public void testRangeRestrictionSingleEmpty() {
        assertEquals(emptyRelation, oneTwoRelation.rangeRestriction(emptySet));
    }

    public void testRangeRestrictionSingleSingleNotInRange() {
        assertEquals(emptyRelation, oneTwoRelation.rangeRestriction(oneSet));
    }

    public void testRangeRestrictionSingleSingleInRangeWithoutChange() {
        assertEquals(oneTwoRelation, oneTwoRelation.rangeRestriction(twoSet));
    }

    public void testRangeRestrictionManySingleInRangeWithChange() {
        assertEquals(oneTwoRelation, oneTwoTwoThreeRelation.rangeRestriction(twoSet));
    }

    public void testRangeRestrictionManyManyInDomainWithoutChange() {
        assertEquals(oneTwoTwoThreeRelation, oneTwoTwoThreeRelation
            .rangeRestriction(twoThreeSet));
    }

    /*
     * domainExclusion
     */

    public void testDomainExclusionEmptyEmpty() {
        assertEquals(emptyRelation, emptyRelation.domainExclusion(emptySet));
    }

    public void testDomainExclusionEmptySingle() {
        assertEquals(emptyRelation, emptyRelation.domainExclusion(oneSet));
    }

    public void testDomainExclusionSingleEmpty() {
        assertEquals(oneTwoRelation, oneTwoRelation.domainExclusion(emptySet));
    }

    public void testDomainExclusionSingleSingleNotInDomain() {
        assertEquals(oneTwoRelation, oneTwoRelation.domainExclusion(twoSet));
    }

    public void testDomainExclusionSingleSingleInDomainWithChange() {
        assertEquals(emptyRelation, oneTwoRelation.domainExclusion(oneSet));
    }

    public void testDomainExclusionManySingleInDomainWithChange() {
        assertEquals(twoThreeRelation, oneTwoTwoThreeRelation.domainExclusion(oneSet));
    }

    public void testDomainExclusionManyManyInDomainWithChange() {
        assertEquals(emptyRelation, oneTwoTwoThreeRelation.domainExclusion(oneTwoSet));
    }

    /*
     * domainExclusion
     */

    public void testRangeExclusionEmptyEmpty() {
        assertEquals(emptyRelation, emptyRelation.rangeExclusion(emptySet));
    }

    public void testRangeExclusionEmptySingle() {
        assertEquals(emptyRelation, emptyRelation.rangeExclusion(oneSet));
    }

    public void testRangeExclusionSingleEmpty() {
        assertEquals(oneTwoRelation, oneTwoRelation.rangeExclusion(emptySet));
    }

    public void testRangeExclusionSingleSingleNotInRange() {
        assertEquals(oneTwoRelation, oneTwoRelation.rangeExclusion(oneSet));
    }

    public void testRangeExclusionSingleSingleInRangeWithChange() {
        assertEquals(emptyRelation, oneTwoRelation.rangeExclusion(twoSet));
    }

    public void testRangeExclusionManySingleInRangeWithChange() {
        assertEquals(twoThreeRelation, oneTwoTwoThreeRelation.rangeExclusion(twoSet));
    }

    public void testRangeExclusionManyManyInRangeWithChange() {
        assertEquals(emptyRelation, oneTwoTwoThreeRelation.rangeExclusion(twoThreeSet));
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
        assertEquals(oneFourTwoFiveRelation, oneTwoTwoThreeRelation
            .compose(twoFourThreeFiveRelation));
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

    /*
     * Slice
     */
    public void testSliceEmpty() {
        assertEquals(emptyRelation, AbstractRelation.slice(emptySet, emptyRelation, emptySet));
    }
    
    public void testSliceEmptyTC() {
        assertEquals(emptyRelation, AbstractRelation.sliceTC(emptySet, emptyRelation, emptySet));
    }

    public void testEasySlice() {
        assertEquals(sliceResultRelation, AbstractRelation.slice(oneSet, sliceRelation,
            emptySet));
    }
    
    public void testEasySliceTC() {
        assertEquals(sliceResultRelation, AbstractRelation.sliceTC(oneSet, sliceRelation,
            emptySet));
    }

    public void testCycleSlice() {
        assertEquals(sliceCycleResultRelation, AbstractRelation.slice(oneSet,
            sliceCycleRelation, emptySet));
    }
    
    public void testCycleSliceTC() {
        assertEquals(sliceCycleResultRelation, AbstractRelation.sliceTC(oneSet,
            sliceCycleRelation, emptySet));
    }

    /*
     * Lazy Slice (connectivity)
     */

    public void testReach() {
        assertEquals(reachResultRelation, AbstractRelation.reach(oneTwoSet,
            sliceRelation2, eightNineSet));
    }

    public void testReachConsistentTC() {
        assertEquals(reachResultRelation, AbstractRelation.transitiveClosure(
            sliceRelation2).domainRestriction(oneTwoSet).rangeRestriction(eightNineSet));
    }

    public void testReach2() {
        assertEquals(reachResultRelation2, AbstractRelation.reach(oneTwoSet,
            sliceRelation2, tenElevenTwelveThirteenSet));
    }

    public void testReachConsistentTC2() {
        assertEquals(reachResultRelation2, AbstractRelation.transitiveClosure(
            sliceRelation2).domainRestriction(oneTwoSet).rangeRestriction(
            tenElevenTwelveThirteenSet));
    }

    public void testReachReachableAfterStop() {
        assertEquals(reachResultRelation3, AbstractRelation.reach(oneTwoSet,
            sliceRelation3, tenElevenTwelveThirteenSet));
    }

    public void testReachConsistentTC3() {
        assertEquals(reachResultRelation3, AbstractRelation.transitiveClosure(
            sliceRelation3).domainRestriction(oneTwoSet).rangeRestriction(
            tenElevenTwelveThirteenSet));
    }

    public void testReachCycleBackToSeed() {
        sliceRelation3.add(pair(1, 14));
        assertEquals(reachResultRelation3, AbstractRelation.reach(oneTwoSet,
            sliceRelation3, tenElevenTwelveThirteenSet));
    }

    public void testReachConsistentTC4() {
        sliceRelation3.add(pair(1, 14));
        assertEquals(reachResultRelation3, AbstractRelation.transitiveClosure(
            sliceRelation3).domainRestriction(oneTwoSet).rangeRestriction(
            tenElevenTwelveThirteenSet));
    }

    public void testReachNonExistentSeed() {
        assertEquals(emptyRelation, AbstractRelation.reach(new OrderedSet<Integer>(666),
            sliceRelation3, tenElevenTwelveThirteenSet));
    }

    public void testReachConsistentTC5() {
        assertEquals(emptyRelation, AbstractRelation.transitiveClosure(sliceRelation3)
            .domainRestriction(new OrderedSet<Integer>(666)).rangeRestriction(
                tenElevenTwelveThirteenSet));
    }

    public void testReachEmptySeeds() {
        assertEquals(emptyRelation, AbstractRelation.reach(emptySet, sliceRelation3,
            tenElevenTwelveThirteenSet));
    }

    public void testReachEmptySinks() {
        assertEquals(emptyRelation, AbstractRelation
            .reach(oneTwoSet, sliceRelation3, emptySet));
    }

    public void testReachEmptySeedsAndSinks() {
        assertEquals(emptyRelation, AbstractRelation.reach(emptySet, sliceRelation3, emptySet));
    }

    public void testReachConsistentTC6() {
        assertEquals(emptyRelation, AbstractRelation.transitiveClosure(sliceRelation3)
            .domainRestriction(emptySet).rangeRestriction(emptySet));
    }
    
    
    
    
    /*
     * Transitive Closure
     */
    public void testTransitiveClosure1() {
        Relation<Integer, Integer> testResult = AbstractRelation.transitiveClosure(aRelation);
        aRelation.add(pair(3, 5));
        assertEquals(aRelation, testResult);
    }

    public void testTransitiveClosure2() {
        Relation<Integer, Integer> testResult = AbstractRelation.transitiveClosure(bRelation);
        bRelation.add(pair(10, 50));
        bRelation.add(pair(10, 30));
        assertEquals(bRelation, testResult);
    }
    
    public void testReflexiveTransitiveClosure() {
        Relation<Integer, Integer> testResult = AbstractRelation.reflexiveTransitiveClosure(bRelation);
        bRelation.add(pair(10, 50));
        bRelation.add(pair(10, 30));
        for (Integer i :testResult.domain().union(testResult.range()))
            bRelation.add(pair(i,i));
        assertEquals(bRelation, testResult);
    }
}
