package dtsa.mapper.client.response;

import dtsa.util.annotation.Nullable;

/**
 * 
 * @description Response for a store request.
 * @author Victorien ELvinger
 * @date 2014/07/1
 *
 */
public class StoreMapperResponse 
	extends MapperResponse {

// Creation
	/**
	 * Create a normal store response with `aURI' as `getURI'.
	 * 
	 * @param aURI - Stored entity URI.
	 */
	public StoreMapperResponse (String aURI) {
		URI = aURI;
		
		assert getURI () == aURI: "ensure: `getURI' set with `aURI'";
	}
	
// Access
	/**
	 * 
	 * @return Stored entity URI.
	 */
	public String getURI () {
		return URI;
	}
	
// Other
	@Override
	public void accept (MapperResponseVisitor aProcessor) {
		aProcessor.visitStore (this);
	}
	
// Implementation
	/**
	 * Stored entity URI.
	 */
	protected String URI;
	
}
