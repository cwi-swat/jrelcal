/*
 * Created on Jan 19, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 *
 */
package jrelcal.sets;

import jrelcal.Pair;
import junit.framework.TestCase;

/**
 * @author storm
 * @class jrelcal.ImmutableRelationTest
 */
public class AdjacencyTableRelationTest extends TestCase {
    protected Relation<Integer,Integer> emptyRelation = new AdjacencyTableRelation<Integer,Integer>();
    protected Relation<Integer,Integer> aRelation = new AdjacencyTableRelation<Integer,Integer>();
    protected Relation<Integer,Integer> bRelation = new AdjacencyTableRelation<Integer,Integer>();
    protected Relation<Integer,Integer> cRelation = new AdjacencyTableRelation<Integer,Integer>();
    protected Relation<Integer, Integer> singletonRelation = new AdjacencyTableRelation<Integer,Integer>();
    protected Relation<Integer, Integer> treeRelation = new AdjacencyTableRelation<Integer,Integer>();
    protected Relation<Integer, Integer> diamondRelation = new AdjacencyTableRelation<Integer,Integer>();
    protected Relation<Integer,Integer> oneTwoRelation = new AdjacencyTableRelation<Integer,Integer>();
    protected Relation<Integer, Integer> oneOneRelation = new AdjacencyTableRelation<Integer,Integer>();
    protected Relation<Integer, Integer> oneTwoProductRelation = new AdjacencyTableRelation<Integer,Integer>();
    protected Relation<Integer, Integer> twoThreeRelation = new AdjacencyTableRelation<Integer,Integer>();  
    protected Relation<Integer, Integer> oneTwoTwoOneRelation = new AdjacencyTableRelation<Integer,Integer>();
    protected Relation<Integer,Integer> oneTwoTwoThreeRelation = new AdjacencyTableRelation<Integer,Integer>();
    protected Relation<Integer, Integer> twoOneThreeTwoRelation = new AdjacencyTableRelation<Integer,Integer>();
    protected Relation<Integer, Integer> twoOneRelation = new AdjacencyTableRelation<Integer,Integer>();
    protected Relation<Integer,Integer> twoFourThreeFiveRelation = new AdjacencyTableRelation<Integer,Integer>();
    protected Relation<Integer, Integer> oneFourTwoFiveRelation = new AdjacencyTableRelation<Integer,Integer>();
    

    protected OrderedSet<Integer> emptySet = new OrderedSet<Integer>();
    protected OrderedSet<Integer> oneSet = new OrderedSet<Integer>();
    protected OrderedSet<Integer> twoSet = new OrderedSet<Integer>();
    protected OrderedSet<Integer> threeSet = new OrderedSet<Integer>();
    protected OrderedSet<Integer> oneTwoSet = new OrderedSet<Integer>();
    protected OrderedSet<Integer> oneTwoThreeSet = new OrderedSet<Integer>();
    protected OrderedSet<Integer> twoThreeSet = new OrderedSet<Integer>();
    protected OrderedSet<Integer> fourFiveSet = new OrderedSet<Integer>();
    
    
    public AdjacencyTableRelationTest(String string) {
        super(string);
    }

    protected OrderedSet<Integer> empty() {
        return new OrderedSet<Integer>();
    }
    
    protected Pair<Integer,Integer> pair(int x, int y) {
        return new Pair<Integer,Integer>(x, y);
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
        
        singletonRelation.add(pair(1,1));
        
        treeRelation.add(pair(1,2));
        treeRelation.add(pair(1,3));

        diamondRelation.add(pair(1,2));
        diamondRelation.add(pair(1,3));
        diamondRelation.add(pair(2,4));
        diamondRelation.add(pair(3,4));
        
        oneTwoRelation.add(pair(1,2));
        oneOneRelation.add(pair(1,1));
        twoThreeRelation.add(pair(2,3));

        oneTwoProductRelation.add(pair(1,2));
        oneTwoProductRelation.add(pair(1,1));
        oneTwoProductRelation.add(pair(2,1));
        oneTwoProductRelation.add(pair(2,2));

        oneTwoTwoOneRelation.add(pair(1,2));
        oneTwoTwoOneRelation.add(pair(2,1));
        
        oneTwoTwoThreeRelation.add(pair(1,2));
        oneTwoTwoThreeRelation.add(pair(2,3));
        
        twoOneThreeTwoRelation.add(pair(2,1));
        twoOneThreeTwoRelation.add(pair(3,2));
        
        twoOneRelation.add(pair(2,1));
        
        twoFourThreeFiveRelation.add(pair(2,4));
        twoFourThreeFiveRelation.add(pair(3,5));
        
        oneFourTwoFiveRelation.add(pair(1,4));
        oneFourTwoFiveRelation.add(pair(2,5));      
        
        aRelation.add(pair(1,2));
        aRelation.add(pair(3,4));
        aRelation.add(pair(4,5));
        bRelation.add(pair(10,20));
        bRelation.add(pair(20,30));
        bRelation.add(pair(20,50));
        bRelation.add(pair(40,50));
        cRelation.add(pair(100,200));
        cRelation.add(pair(333,30));
        cRelation.add(pair(1,50));
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
        assertEquals(aRelation.union(bRelation.union(cRelation)), 
                    aRelation.union(bRelation).union(cRelation));
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
        assertEquals(oneTwoTwoThreeRelation, oneTwoTwoThreeRelation.domainRestriction(oneTwoSet));
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
        assertEquals(oneTwoTwoThreeRelation, oneTwoTwoThreeRelation.rangeRestriction(twoThreeSet));
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
        assertEquals(oneFourTwoFiveRelation, oneTwoTwoThreeRelation.compose(twoFourThreeFiveRelation));
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
     * Cartesian product
     */
    
    public void testCartesianProductEmptyxEmpty() {
        assertEquals(emptyRelation, AdjacencyTableRelation.cartesianProduct(emptySet, emptySet));
    }
    
    public void testCartesianProductEmptyxX() {
        assertEquals(emptyRelation, AdjacencyTableRelation.cartesianProduct(emptySet, oneTwoSet));
    }
    
    public void testCartesianProductXxY() {
        assertEquals(oneTwoProductRelation, AdjacencyTableRelation.cartesianProduct(oneTwoSet, oneTwoSet));
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

