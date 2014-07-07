package dtsa.mapper.util.json;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.lang.String;

import dtsa.mapper.client.request.Request;
import dtsa.mapper.client.request.StoreRequest;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import dtsa.mapper.util.annotation.Nullable;

/**
 * 
 * @description Test json utility
 * @author Victorien ELvinger
 * @date 2014/06/30
 *
 */
public class TypeJsonManagerTest {
	
// Test
	/**
	 * Test:
	 * - TypeJsonManager.object
	 */
	@Test
	public void testSOmething () {
		TypeJsonManager <Request> manager = new TypeJsonManager <> ();

		// Attach the label "store_request" with StoreRequest class.
		manager.add ("store_request", StoreRequest.class);
		assertTrue ("`store_request' is an existing label", manager.has ("store_request"));
		assertTrue ("StoreRequest class is labeled", manager.isLabeled (StoreRequest.class));
		
		String jrep = "{\"path\":\"mypath\"}";
		try {
			Request r = manager.object (jrep, "store_request");
			if (r instanceof StoreRequest) {
				StoreRequest sr = (StoreRequest) r;
				assertTrue ("path used in json is the same in the java object",
						sr.getPath ().equals ("mypath"));
				
			}
			else {
				assertTrue ("StoreRequest JSON converted to a java object.", false);
			}
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
	 */
	@Test
	public void testTypedJson () {
		TypeJsonManager <Request> manager = new TypeJsonManager <> ();
		
		// Attached the label "store_request" with StoreRequest class
		manager.add ("store_request", StoreRequest.class);
		
		Request r = new StoreRequest ("mypath");
		
		ByteArrayOutputStream outStream = new ByteArrayOutputStream ();
		manager.writeValue (new PrintWriter (outStream), r);
		String jrep = outStream.toString ();
		
		if (! jrep.isEmpty ()) {
			System.out.println (jrep);
			assertTrue ("java object to labeled json value",
					"{\"label\":\"store_request\",\"value\":\"{\\\"path\\\":\\\"mypath\\\"}\"}"
							.equals (jrep));
			
			try {
				r = manager.instance (jrep);
				if (r instanceof StoreRequest) {
					StoreRequest sr = (StoreRequest) r;
					assertTrue ("path used in json is the same in the java object", sr.getPath ()
							.equals ("mypath"));
				}
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
