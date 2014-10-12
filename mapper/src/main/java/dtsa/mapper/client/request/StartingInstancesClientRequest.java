package dtsa.mapper.client.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dtsa.mapper.client.response.MapperExceptionResponse;
import dtsa.mapper.client.response.StartingInstancesMapperResponse;
import dtsa.util.annotation.Nullable;

/**
 *
 * @description Request for launching a pool of identical instance.
 * @author Victorien ELvinger
 * @date 2014/07/1
 *
 */
public class StartingInstancesClientRequest
	extends ClientRequest {

// Creation
	/**
	 * Create a store request with `aCount' as `getInstanceNumber'.
	 * @param aCount - Instance number to launch.
	 */
	@JsonCreator
	public StartingInstancesClientRequest (@JsonProperty ("instanceCount") int aCount) {
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
	public @Nullable StartingInstancesMapperResponse response () {
		return response;
	}

	@Override
	public StartingInstancesClientRequest partialCLone () {
		StartingInstancesClientRequest result;

		result = new StartingInstancesClientRequest (instanceCount);

		assert this != result: "current object and result have not the same reference.";
		assert partialEquals (result): "ensure: current object and result are partially equal.";
		return result;
	}

// Status
	@Override
	public boolean partialEquals (Object aOther) {
		boolean result;

		result = getClass () == aOther.getClass () &&
				instanceCount == ((StartingInstancesClientRequest) aOther).getInstanceCount ();

		assert (! result) || (getClass () == aOther.getClass ()): "ensure: equal implies same type.";
		assert getClass () == aOther.getClass () || (! result): "esnure: different type implies not equal";
		return result;
	}

// Other
	@Override
	public void accept (ClientRequestVisitor aProcessor) {
		aProcessor.visitStartingInstances (this);

		assert aProcessor.isReactive () == (hasResponse () || hasException ()): "ensure: responded if `aProcessor' is reactive.";
	}

// Change
	/**
	 * Set `response' with `aResponse'
	 * @param aResponse - response of this current request.
	 */
	public void setResponse (StartingInstancesMapperResponse aResponse) {
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

// Implementation
	/**
	 * Instance number to launch.
	 */
	protected int instanceCount;

	/**
	 * Answer of this current request.
	 */
	protected @Nullable StartingInstancesMapperResponse response;

}
