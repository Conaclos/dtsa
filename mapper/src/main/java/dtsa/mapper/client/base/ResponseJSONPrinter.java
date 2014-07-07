package dtsa.mapper.client.base;

import java.io.PrintWriter;

import dtsa.mapper.client.response.Response;
import dtsa.mapper.client.response.ResponseProcessor;
import dtsa.mapper.client.response.StoreResponse;
import dtsa.mapper.util.json.TypeJsonManager;

/**
 * 
 * @description 
 * @author Victorien ELvinger
 * @date 2014/07/1
 *
 */
public class ResponseJSONPrinter
		extends ResponsePrinter
		implements ResponseProcessor {

// Creation
	/**
	 * 
	 * @param aOut - output for printing.
	 */
	public ResponseJSONPrinter (PrintWriter aOut) {
		super (aOut);
		manager = new TypeJsonManager <> ();
		manager.add (StoreResponseLabel, StoreResponse.class);
		
		assert out == aOut: "ensure: `out' set with `aOut'";
	}
	
// Constant
	/**
	 * Label for StoreResponse class.
	 */
	public final static String StoreResponseLabel = "store_response";
	
// Other
	@Override
	public void processStore (StoreResponse aResponse) {
		manager.writeValue (out, aResponse);
	}
	
// Implementation	
	/**
	 * Response to JSON manager
	 */
	protected TypeJsonManager <Response> manager;
	
}
