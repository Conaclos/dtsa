package dtsa.mapped.client.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @description 
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class ProjectCompilationMappedResponse 
	extends MappedResponse {

// Creation
	/**
	 * 
	 * @param aUri - {@link #getUri ()}
	 */
	@JsonCreator
	public ProjectCompilationMappedResponse (@JsonProperty ("uri") String aUri) {
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
		aVisitor.visitProjectCompilation (this);
	}
	
// Implementation
	/**
	 * @see #getUri ()
	 */
	protected String uri;
	
}
