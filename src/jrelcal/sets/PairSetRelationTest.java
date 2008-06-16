package jrelcal.sets;


public class PairSetRelationTest extends RelationTest {
    protected Relation<Integer, Integer> getRelation() {        
        return new PairSetRelation<Integer, Integer>();
    }
   
}