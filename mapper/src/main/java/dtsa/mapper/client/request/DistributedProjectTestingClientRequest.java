package dtsa.mapper.client.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dtsa.mapper.client.response.MapperExceptionResponse;
import dtsa.mapper.client.response.DistributedProjectTestingMapperResponse;
import dtsa.util.annotation.Nullable;
import dtsa.util.communication.base.Response;
import dtsa.util.communication.base.ResponseVisitor;

public class DistributedProjectTestingClientRequest
		extends ClientRequest {
	
// Creation
	public DistributedProjectTestingClientRequest (String aUri, String aProject, String aConfiguration, String aTarget, String [][] aClusters) {
		
		uri = aUri;
		project = aProject;
		configuration = aConfiguration;
		target = aTarget;
		timeout = DefaultTimeout;
		clusters = aClusters;
		
		assert getConfiguration () == aConfiguration: "ensure:  `getConfiguration' set with `aConfiguration'";
		assert getTimeout () == DefaultTimeout: "ensure:  `getTimeout' set with `DefaultTimeout'";
		assert getClusters () == aClusters: "ensure:  `getClusters' set with `aClusters'";
		assert getProject () == aProject: "ensure:  `getProject' set with `aProject'";
		assert getTarget () == aTarget: "ensure:  `getTarget' set with `aTarget'";
		assert getUri () == aUri: "ensure:  `getUri' set with `aUri'";
	}
	
	@JsonCreator
	public DistributedProjectTestingClientRequest (@JsonProperty ("uri") String aUri, @JsonProperty ("project") String aProject, 
			@JsonProperty ("configuration") String aConfiguration, @JsonProperty ("target") String aTarget, 
			@JsonProperty ("timeout") long aTimeout, @JsonProperty ("clusters") String [][] aClusters) {
		
		uri = aUri;
		project = aProject;
		configuration = aConfiguration;
		target = aTarget;
		timeout = aTimeout;
		clusters = aClusters;
		
		assert getConfiguration () == aConfiguration: "ensure:  `getConfiguration' set with `aConfiguration'";
		assert getTimeout () == aTimeout: "ensure:  `getTimeout' set with `aTimeout'";
		assert getClusters () == aClusters: "ensure:  `getClusters' set with `aClusters'";
		assert getProject () == aProject: "ensure:  `getProject' set with `aProject'";
		assert getTarget () == aTarget: "ensure:  `getTarget' set with `aTarget'";
		assert getUri () == aUri: "ensure:  `getUri' set with `aUri'";
	}
	
// Constant
	/**
	 * Default timeout.
	 */
	public final static int DefaultTimeout = 20;
	
	
// Access
	@Override
	public @Nullable Response <? extends ResponseVisitor> response () {
		// TODO Auto-generated method stub
		return response;
	}
	
	/**
	 * @return Project location.
	 */
	public String getUri () {
		return uri;
	}
	
	/**
	 * @return Project path from `uri'.
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
	 * @return Class clusters.
	 * Null means one testing session for all classes.
	 */
	public @Nullable String [][] getClusters () {
		return clusters;
	}
	
	@Override
	public DistributedProjectTestingClientRequest partialCLone () {
		return new DistributedProjectTestingClientRequest (uri, project, configuration, target, timeout, clusters);
	}
	
// Status
	@Override
	public boolean partialEquals (Object aOther) {
		boolean result;
		
		if (getClass () == aOther.getClass ()) {
			DistributedProjectTestingClientRequest temp = (DistributedProjectTestingClientRequest) aOther;
			
			result = timeout == temp.getTimeout () && target.equals (temp.getTarget ()) &&
					uri.equals (temp.getUri ()) && configuration.equals (temp.getConfiguration ()) &&
					clusters == temp.getClusters ();
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
	public void setResponse (DistributedProjectTestingMapperResponse aResponse) {
		assert ! hasException (): "require: has not exception.";
		
		response = aResponse;
		
		assert response () == aResponse: "ensure: `response' set with `aResponse'.";
	}
	
	/**
	 * Set `exception' with `aException'.
	 * @param aException - {@link #exception()}
	 */
	public void setException (MapperExceptionResponse aException) {
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
	 * @see #response ()
	 */
	protected @Nullable DistributedProjectTestingMapperResponse response;
	
	/**
	 * @see #getUri ()
	 */
	protected String uri;
	
	/**
	 * @see #getConfiguration ()
	 */
	protected String configuration;
	
	/**
	 * @see #getProject ()
	 */
	protected String project;
	
	/**
	 * @see #getTarget ()
	 */
	protected String target;
	
	/**
	 * @see #getTimeout ()
	 */
	protected long timeout;
	
	/**
	 * @see #getClusters ()
	 */
	protected @Nullable String [][] clusters;
	
}
