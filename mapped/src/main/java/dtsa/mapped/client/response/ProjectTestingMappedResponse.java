package dtsa.mapped.client.response;

/**
 * 
 * @description 
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class ProjectTestingMappedResponse 
	extends MappedResponse {

// Creation
	/**
	 * 
	 * @param aUri - {@link #getUri ()}
	 */
	public ProjectTestingMappedResponse (String aUri) {
		uri = aUri;
		
		assert getUri () == aUri: "ensure: `getUri' set with `aUri'.";
	}
	
// Access
	/**
	 * 
	 * @return URI of the compilation result.
	 */
	public String getUri () {
		return uri;
	}
	
// Other
	@Override
	public void accept (MappedResponseVisitor aVisitor) {
		aVisitor.visitProjectTesting (this);
	}
	
// Implementation
	/**
	 * @see #getUri ()
	 */
	protected String uri;
	
}
