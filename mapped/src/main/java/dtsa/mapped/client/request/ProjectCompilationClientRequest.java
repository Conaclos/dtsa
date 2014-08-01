package dtsa.mapped.client.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dtsa.mapped.client.response.MappedExceptionResponse;
import dtsa.mapped.client.response.ProjectCompilationMappedResponse;
import dtsa.util.annotation.NonNull;
import dtsa.util.annotation.Nullable;


public class ProjectCompilationClientRequest 
	extends ClientRequest {

// Creation
	/**
	 * 
	 * @param aUri - {@link #getUri()}
	 * @param aProject - {@link #getProject()}
	 * @param aConfiguration - {@link #getConfiguration()}
	 * @param aTarget - {@link #getTarget ()}
	 */
	@JsonCreator
	public ProjectCompilationClientRequest (@JsonProperty ("uri") String aUri, @JsonProperty ("project") String aProject, @JsonProperty ("configuration") String aConfiguration, @JsonProperty ("target") String aTarget) {

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
	public @Nullable ProjectCompilationMappedResponse response () {
		return response;
	}
	
	/**
	 * @return Project URI
	 */
	public String getUri () {
		return uri;
	}
	
	/**
	 * @return Project path.
	 */
	public String getProject () {
		return project;
	}

	/**
	 * @return Relative path from `uri' of the project configuration file.
	 */
	public String getConfiguration () {
		return configuration;
	}

	/**
	 * @return Project target.
	 */
	public String getTarget () {
		return target;
	}

	@Override
	public ProjectCompilationClientRequest partialCLone () {
		return new ProjectCompilationClientRequest (uri, project, configuration, target);
	}

// Status
	@Override
	public boolean partialEquals (@NonNull Object aOther) {
		boolean result;
		
		if (getClass () == aOther.getClass ()) {
			ProjectCompilationClientRequest temp = (ProjectCompilationClientRequest) aOther;
			
			result = target.equals (temp.getTarget ()) && uri.equals (temp.getUri ()) && 
					configuration.equals (temp.getConfiguration ());
		}
		else {
			result = false;
		}
		
		assert (! result) || (getClass () == aOther.getClass ()): "ensure: equal implies same type.";
		assert getClass () == aOther.getClass () || (! result): "esnure: different type implies not equal";
		return result;
	}
	
// Change
	/**
	 * Set `response' with `aResponse'
	 * @param aResponse - response of this current request.
	 */
	public void setResponse (ProjectCompilationMappedResponse aResponse) {
		assert ! hasException (): "require: has not exception.";
		
		response = aResponse;
		
		assert response () == aResponse: "ensure: `response' set with `aResponse'.";
	}
	
	/**
	 * Set `exception' with `aException'.
	 * @param aException - {@link #exception()}
	 */
	public void setException (MappedExceptionResponse aException) {
		assert ! hasResponse (): "require: has not response.";
		
		exception = aException;

		assert exception () == aException: "ensure: `exception' set with `aException'.";
	}

// Other
	@Override
	public void accept (ClientRequestVisitor aVisitor) {
		aVisitor.visitProjectCompilation (this);
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
	
	/**
	 * @see #response ()
	 */
	protected @Nullable ProjectCompilationMappedResponse response;
	
}
