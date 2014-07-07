package dtsa.mapper.client.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dtsa.mapper.client.response.StartingInstancesResponse;
import dtsa.mapper.util.annotation.Nullable;

/**
 * 
 * @description Request for launching a pool of identical instance.
 * @author Victorien ELvinger
 * @date 2014/07/1
 *
 */
public class StartingInstancesRequest 
	extends Request {
	
// Creation
	/**
	 * Create a store request with `aCount' as `getInstanceNumber'.
	 * @param aCount - Instance number to launch.
	 */
	@JsonCreator
	public StartingInstancesRequest (@JsonProperty ("instanceCount") int aCount) {
		assert aCount > 0: "require: `aCount' is strictly positive";
		
		instanceCount = aCount;
		
		assert getInstanceCount () == aCount: "ensure: `getInstanceNumber' set with `aCount'.";
	}

// Access
	/**
	 * 
	 * @return Instance number to launch.
	 */
	public int getInstanceCount () {
		return instanceCount;
	}
	
	@Override
	public @Nullable StartingInstancesResponse response () {
		return response;
	}

	@Override
	public StartingInstancesRequest partialCLone () {
		StartingInstancesRequest result;
		
		result = new StartingInstancesRequest (instanceCount);

		assert this != result: "current object and result have not the same reference.";
		assert partialEquals (result): "ensure: current object and result are partially equal.";
		return result;
	}

// Status
	@Override
	public boolean partialEquals (Object aOther) {
		boolean result;
		
		result = getClass () == aOther.getClass () && 
				instanceCount == ((StartingInstancesRequest) aOther).getInstanceCount ();
		
		assert (! result) || (getClass () == aOther.getClass ()): "ensure: equal implies same type.";
		assert getClass () == aOther.getClass () || (! result): "esnure: different type implies not equal";
		return result;
	}

// Other
	@Override
	public void process (RequestProcessor aProcessor) {
		aProcessor.processStartingInstances (this);
	}
	
// Change
	/**
	 * Set `response' with `aResponse'
	 * @param aResponse - response of this current request.
	 */
	public void setResponse (StartingInstancesResponse aResponse) {
		response = aResponse;
		
		assert response () == aResponse: "ensure: `response' set with `aResponse'.";
	}
	
// Implementation	
	/**
	 * Instance number to launch.
	 */
	protected int instanceCount;
	
	/**
	 * Answer of this current request.
	 */
	protected @Nullable StartingInstancesResponse response;
	
}
