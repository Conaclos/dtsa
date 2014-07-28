package dtsa.mapper.cloud.mapped.request;

import dtsa.mapper.cloud.mapped.response.ServiceResponse;
import dtsa.util.annotation.NonNull;
import dtsa.util.annotation.Nullable;
import dtsa.util.communication.base.Request;

/**
 * 
 * @description 
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class ProjectCompilationMapperRequest 
	extends MapperRequest {
	
// Creation
	/**
	 * 
	 * @param aUri - {@link #getUri()}
	 * @param aProject - {@link #getProject()}
	 * @param aConfiguration {@link #getConfiguration()}
	 * @param aTarget - {@link #getTarget()}
	 */
	public ProjectCompilationMapperRequest (String aUri, String aProject, String aConfiguration, String aTarget) {
		uri = aUri;
		project = aProject;
		configuration = aConfiguration;
		target = aTarget;
		
		assert getUri () == aUri: "ensure: `getUri' set with `aUri'";
		assert getProject () == aProject: "ensure: `getProject' set with `aProject'";
		assert getConfiguration () == aConfiguration: "ensure: `getConfiguration' set with `aConfiguration'";
		assert getTarget () == aTarget: "ensure: `getTarget' set with `aTarget'";
	}
	
// Access
	@Override
	public @Nullable ServiceResponse response () {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @return Project URI.
	 */
	public String getUri () {
		return uri;
	}

	/**
	 * @return Project path from {@link #getUri()}.
	 */
	public String getProject () {
		return project;
	}

	/**
	 * @return Eiffel Configuration File path from {@link #getUri()}.
	 */
	public String getConfiguration () {
		return configuration;
	}

	/**
	 * @return Project target available in {@link #getConfiguration()}.
	 */
	public String getTarget () {
		return target;
	}

	@Override
	public @NonNull Request <MapperRequestVisitor> partialCLone () {
		// TODO Auto-generated method stub
		return null;
	}

// Status
	@Override
	public boolean partialEquals (@NonNull Object aOther) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void accept (@NonNull MapperRequestVisitor aProcessor) {
		// TODO Auto-generated method stub
		
	}
	
// Implementation
	/**
	 * @see #getUri ()
	 */
	protected String uri;
	
	/**
	 * @see #getProject ()
	 */
	protected String project;
	
	/**
	 * @see #getConfiguration ()
	 */
	protected String configuration;
	
	/**
	 * @see #getTarget ()
	 */
	protected String target;
	
}
