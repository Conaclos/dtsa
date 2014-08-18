package dtsa.mapper.client.response;

/**
 *
 * @description
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class DistributedProjectTestingMapperResponse
	extends MapperResponse {

// Creation
	/**
	 *
	 * @param aUris {@link #getUris ()}
	 */
	public DistributedProjectTestingMapperResponse (String [] aUris) {
		uris = aUris;

		assert uris == aUris: "ensure: `uris' set with `aUris'";
	}

// Access
	/**
	 * @return Uris or results are stored.
	 */
	public String[] getUris () {
		return uris;
	}

// Visit
	@Override
	public void accept (MapperResponseVisitor aVisitor) {
		aVisitor.visitProjectTesting (this);
	}

// Implementation
	/**
	 * @see #getUris ()
	 */
	protected String [] uris;

}
