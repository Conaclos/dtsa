package dtsa.mapper.client.base;

import java.io.PrintWriter;

import dtsa.mapper.client.response.StoreResponse;

/**
 * 
 * @description Response logger
 * @author Victorien ELvinger
 * @date 2014/07/2
 *
 */
public class ResponseLogger 
	extends ResponsePrinter {
	
// Creation
	/**
	 * 
	 * @param aOut - output for printing.
	 */
	public ResponseLogger (PrintWriter aOut) {
		super (aOut);
		
		assert out == aOut: "ensure: `out' set with `aOut'";
	}
	
// Flags
	public final static String StoreFlag = "StoreResponse";

// Other
	@Override
	public void processStore (StoreResponse aResponse) {
		String representation;
		
		representation = "";
		if (aResponse.hasException ()) {
			
		}
		else {
			
		}
		
		out.println (representation);
	}
	
}

