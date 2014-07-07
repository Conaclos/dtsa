package dtsa.mapper.client.base;

import java.io.PrintWriter;

import dtsa.mapper.client.response.ResponseProcessor;

/**
 * 
 * @description Root class for response printer.
 * @author Victorien ELvinger
 * @date 2014/07/2
 *
 */
public abstract class ResponsePrinter
		implements ResponseProcessor {

// Creation
	/**
	 * 
	 * @param aOut - output for printing.
	 */
	public ResponsePrinter (PrintWriter aOut) {
		out = aOut;
		
		assert out == aOut: "ensure: `out' set with `aOut'";
	}
	
// Implementation
	/**
	 * Output for printing.
	 */
	protected PrintWriter out;
	
}
