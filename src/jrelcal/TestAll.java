
package jrelcal;

import jrelcal.bags.MultiRelationTest;
import jrelcal.bags.BagTest;
import jrelcal.sets.IndexedSetTest;
import jrelcal.sets.SetTest;
import jrelcal.sets.RelationTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
 
public class TestAll extends TestCase {

    public TestAll(String test) {
        super(test);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new TestSuite(SetTest.class));
        suite.addTest(new TestSuite(IndexedSetTest.class));
        suite.addTest(new TestSuite(BagTest.class));
        suite.addTest(new TestSuite(RelationTest.class));
        suite.addTest(new TestSuite(MultiRelationTest.class));
        return suite;
    }
    
    public static void main(String argv[]) {
        junit.textui.TestRunner.run(suite());
    }
}
