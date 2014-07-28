package dtsa.util.json;

import org.junit.Test;

import java.io.IOException;
import java.lang.String;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * 
 * @description Test json utility
 * @author Victorien ELvinger
 * @date 2014/06/30
 *
 */
public class JsonLabelingTest {
	
// Test
	/**
	 * Test:
	 * - TypeJsonManager.object
	 */
	@Test
	public void testSOmething () {
		JsonLabeling <Sample> manager = new JsonLabeling <> ();

		// Attach the label "store_request" with StoreRequest class.
		manager.add ("sample", Sample.class);
		assertTrue ("`sample' is an existing label", manager.has ("sample"));
		assertTrue ("Sample class is labeled", manager.isLabeled (Sample.class));
		
		String jrep = "{\"name\":\"myname\"}";
		try {
			Sample r = manager.object (jrep, "sample");
			assertTrue ("path used in json is the same in the java object",
					r.getName ().equals ("myname"));
		}
		catch (UnrecordedLabelException e) {
			assertNotNull ("class and label matched", false);
		}
		catch (InvalidJSONException e) {
			assertNotNull ("valid JSON value", false);
		}
	}
	
	/**
	 * Test:
	 * - TypeJsonManager.writeValue
	 * - TypeJsonManager.instance
	 * @throws IOException 
	 */
	@Test
	public void testTypedJson () throws IOException {
		JsonLabeling <Sample> manager = new JsonLabeling <> ();
		
		// Attached the label "store_request" with StoreRequest class
		manager.add ("sample", Sample.class);
		
		Sample r = new Sample ("mypath");
		
		String jrep = manager.value (r);
		
		if (! jrep.isEmpty ()) {
			System.out.println (jrep);
			assertTrue ("java object to labeled json value",
					"{\"label\":\"sample\",\"value\":\"{\\\"name\\\":\\\"mypath\\\"}\"}"
							.equals (jrep));
			
			try {
				r = manager.instance (jrep);
				assertTrue ("path used in json is the same in the java object", r.getName ()
						.equals ("mypath"));
			}
			catch (UnrecordedLabelException e) {
				assertNotNull ("class and label matched", false);
			}
			catch (InvalidJSONException e) {
				assertNotNull ("valid JSON value", false);
			}
		}
		else {
			assertTrue ("Get a JSON value from a java object.", false);
		}
	}
	
}
