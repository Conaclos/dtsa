package dtsa.mapped.client.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dtsa.mapped.client.response.MappedExceptionResponse;
import dtsa.mapped.client.response.ProjectTestingMappedResponse;
import dtsa.util.annotation.NonNull;
import dtsa.util.annotation.Nullable;


public class ProjectTestingClientRequest 
	extends ClientRequest {

// Creation
	/**
	 * 
	 * @param aUri - {@link #getUri()}
	 * @param aProject - {@link #getProject()}
	 * @param aConfiguration - {@link #getConfiguration()}
	 * @param aTarget - {@link #getTarget ()}
	 * @param aTimeout - {@link #getTimeout ()}
	 * @param aClasses - {@link #getClasses ()}
	 */
	@JsonCreator
	public ProjectTestingClientRequest (@JsonProperty ("uri") String aUri, @JsonProperty ("project") String aProject, 
			@JsonProperty ("configuration") String aConfiguration, @JsonProperty ("target") String aTarget, 
			@JsonProperty ("timeout") long aTimeout, @Nullable @JsonProperty ("classes") String [] aClasses) {

		uri = aUri;
		project = aProject;
		configuration = aConfiguration;
		target = aTarget;
		classes = aClasses;
		if (aTimeout > 0) {
			timeout = aTimeout;
		}
		else {
			timeout = DefaultTimeout;
		}

		assert getUri () == aUri: "ensure: `getUri' set with `aUri'";
		assert getProject () == aProject: "ensure: `getProject' set with `aProject'";
		assert getConfiguration () == aConfiguration: "ensure: `getConfiguration' set with `aConfiguration'";
		assert getTarget () == aTarget: "ensure: `getTarget' set with `aTarget'";
		assert aTimeout <= 0 || getTimeout () == aTimeout: "ensure:  `getTimeout' set with `aTimeout' if `aTimeout' is strictly positive";
		assert aTimeout > 0 || getTimeout () == DefaultTimeout: "ensure:  `getTimeout' set with `DefaultTimeout' if `aTimeout' is negative";
		assert getClasses () == aClasses: "ensure: `getClasses' set with `aClasses'";
	}
	
// Constant
	/**
	 * Default timeout.
	 */
	public final static int DefaultTimeout = 20;
	
// Access
	@Override
	public @Nullable ProjectTestingMappedResponse response () {
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
	
	/**
	 * @return Time for testing.
	 */
	public long getTimeout () {
		return timeout;
	}
	
	/**
	 * @return CLasses under testing.
	 * Null means all project classes.
	 */
	public @Nullable String [] getClasses () {
		return classes;
	}

	@Override
	public ProjectTestingClientRequest partialCLone () {
		return new ProjectTestingClientRequest (uri, project, configuration, target, timeout, classes);
	}

// Status
	@Override
	public boolean partialEquals (@NonNull Object aOther) {
		boolean result;
		
		if (getClass () == aOther.getClass ()) {
			ProjectTestingClientRequest temp = (ProjectTestingClientRequest) aOther;
			
			result = target.equals (temp.getTarget ()) && uri.equals (temp.getUri ()) && 
					configuration.equals (temp.getConfiguration ()) && timeout == temp.getTimeout ()
					&& classes == temp.getClasses ();
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
	public void setResponse (ProjectTestingMappedResponse aResponse) {
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
		aVisitor.visitProjectTesting (this);
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
	 * @see #getTimeout ()
	 */
	protected long timeout;
	
	/**
	 * @see #getClasses ()
	 */
	protected @Nullable String [] classes;
	
	/**
	 * @see #response ()
	 */
	protected @Nullable ProjectTestingMappedResponse response;
	
}
