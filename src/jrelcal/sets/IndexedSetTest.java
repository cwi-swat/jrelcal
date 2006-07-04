/*
 * Created on Jan 19, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jrelcal.sets;


/**
 * @author storm
 * @class jrelcal.IndexedSetTest
 */
public class IndexedSetTest extends OrderedSetTest {
	private IndexedSet<Integer> fourFiveSixIndexedSet;
	
	public IndexedSetTest(String string) {
		super(string);
	}
	
	protected void setUp() {
		fourFiveSixIndexedSet = new IndexedSet<Integer>();
		for (int i = 4; i <= 6; i++)
			fourFiveSixIndexedSet.add(i);
		super.setUp();
	}
	
	/*
	 * elementAt & indexOf & iteration
	 */
	
	public void testElementAt() {
		int i = 0;
		boolean b = true;
		for (Integer n: fourFiveSixIndexedSet) {
			b &= fourFiveSixIndexedSet.elementAt(i).equals(n);
			i++;
		}
		assertTrue(b);
	}
	
	public void testIndexOf() {
		int i = 0;
		boolean b = true;
		for (Integer n: fourFiveSixIndexedSet) {
			b &= fourFiveSixIndexedSet.indexOf(n) == i;
			i++;
		}
		assertTrue(b);
	}
	
	
}

