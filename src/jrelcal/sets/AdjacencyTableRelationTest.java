package jrelcal.sets;

public class AdjacencyTableRelationTest extends RelationTest {
    @Override
    protected Relation<Integer, Integer> getRelation() {
        return new AdjacencyTableRelation<Integer, Integer>();
    }
}