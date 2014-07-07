package dtsa.mapper.client.response;

import dtsa.mapper.util.annotation.Nullable;

/**
 * 
 * @description Response for a store request.
 * @author Victorien ELvinger
 * @date 2014/07/1
 *
 */
public class StoreResponse 
	extends Response {

// Creation
	/**
	 * Create a normal store response, i.e. without exception.
	 */
	public StoreResponse () {
		super ();
		URI = null;
		
		assert ! hasException (): "ensure: without exception.";
	}
	
	/**
	 * Create a normal store response with `aURI' as `getURI'.
	 * 
	 * @param aURI - Stored entity URI.
	 */
	public StoreResponse (String aURI) {
		URI = aURI;
		
		assert getURI () == aURI: "ensure: `getURI' set with `aURI'";
	}
	
	/**
	 * Create a store response with `aException' as `getException'.
	 * @param aException
	 */
	public StoreResponse (Exception aException) {
		super (aException);
		URI = null;
		
		assert getException () == aException: "ensure: `exception' set with `aException'.";
	}
	
// Access
	/**
	 * 
	 * @return Stored entity URI.
	 */
	public String getURI () {
		return URI;
	}
	
// Change
	/**
	 * Set `getURI' with `aURI'.
	 * @param aURI - Stored entity URI.
	 */
	public void setURI (String aURI) {
		URI = aURI;
		
		assert getURI () == aURI: "ensure: `getURI' set with `aURI'.";
	}
	
// Other
	@Override
	public void process (ResponseProcessor aProcessor) {
		aProcessor.processStore (this);
	}
	
// Implementation
	/**
	 * Stored entity URI.
	 */
	protected @Nullable String URI;
	
}
