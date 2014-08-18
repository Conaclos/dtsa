package dtsa.mapper.client.response;


/**
 *
 * @description Response for a retrieve request.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class RetrieveMapperResponse
	extends MapperResponse {

// Creation
	/**
	 * Create a normal store response with `aURI' as `getURI'.
	 *
	 * @param aURI - Stored entity URI.
	 */
	public RetrieveMapperResponse (String aUri) {
		uri = aUri;

		assert getUri () == aUri: "ensure: `getURI' set with `aURI'";
	}

// Access
	/**
	 *
	 * @return Stored entity URI.
	 */
	public String getUri () {
		return uri;
	}

// Other
	@Override
	public void accept (MapperResponseVisitor aProcessor) {
		aProcessor.visitRetrieve (this);
	}

// Implementation
	/**
	 * Stored entity URI.
	 */
	protected String uri;

}
