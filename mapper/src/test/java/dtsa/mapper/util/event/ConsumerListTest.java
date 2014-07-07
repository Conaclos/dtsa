package dtsa.mapper.util.event;

import org.junit.Test;

import dtsa.mapper.util.annotation.Nullable;
import static org.junit.Assert.assertTrue;

/**
 * 
 * @description COnsuler list test
 * @author Victorien ELvinger
 * @date 2014/06/30
 *
 */
public class ConsumerListTest {
	
// Test
	/**
	 * Test:
	 * - ConsumerList.add
	 * - ConsumerList.run
	 */
	@Test
	public void test () {
		ConsumerList <String> testedEvent = new ConsumerList <> ();
		int i;
		
		assertTrue ("no items", testedEvent.isEmpty ());
		
		i = testedEvent.size ();
		testedEvent.add ((String s) -> field = s);
		assertTrue ("at least one item", ! testedEvent.isEmpty ());
		assertTrue ("One elemnt added", testedEvent.size () == (i + 1));
		
		field = null;
		testedEvent.run ("data");
		assertTrue ("Event launched", "data".equals (field));
		
		field = null;
		
	}
	
	/**
	 * Test:
	 * - ConsumerList.run
	 */
	@Test
	public void testRun () {
		ConsumerList <Integer> testedEvent = new ConsumerList <> ();
		
		testedEvent.add ((Integer c) -> field2 = field2 + c);
		testedEvent.add ((Integer c) -> field2 = field2 - c*2);
		assertTrue ("", testedEvent.size () == 2);
		
		int i = 2;
		testedEvent.run (i);
		assertTrue ("Alle action launched", field2 == (i - i*2));
	}

// Implementation
	/**
	 * A simple string field.
	 */
	protected @Nullable String field;
	
	/**
	 * A simple integer field.
	 */
	protected int field2;
	
}
