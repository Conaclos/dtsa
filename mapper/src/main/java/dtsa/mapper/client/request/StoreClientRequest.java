package dtsa.mapper.client.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dtsa.mapper.client.response.MapperExceptionResponse;
import dtsa.mapper.client.response.StoreMapperResponse;
import dtsa.util.annotation.Nullable;

/**
 * @description Request for storing
 * @author Conaclos
 * @date 2014/06/27
 */
public class StoreClientRequest
	extends ClientRequest {

// Creation
	/**
	 * Create a store request with `aPath' as `getPath'.
	 * @param aPath -path of the entity to store.
	 */
	@JsonCreator
	public StoreClientRequest (@JsonProperty ("path") String aPath) {
		path = aPath;

		assert getPath () == aPath: "ensure: `getPath' set with `aPath'.";
	}

// Access
	@Override
	public @Nullable StoreMapperResponse response () {
		return response;
	}

	/**
	 * @return Path of the entity to store.
	 */
	public String getPath () {
		return path;
	}

	@Override
	public StoreClientRequest partialCLone () {
		StoreClientRequest result;

		result = new StoreClientRequest (path);

		assert this != result: "current object and result have not the same reference.";
		assert partialEquals (result): "ensure: current object and result are partially equal.";
		return result;
	}

// Status
	@Override
	public boolean partialEquals (Object aOther) {
		boolean result;

		if (getClass () == aOther.getClass ()) {
			StoreClientRequest temp = (StoreClientRequest) aOther;

			result = path.equals (temp.getPath ());
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
	 * Set `response' with `aResponse'.
	 * @param aResponse - {@link #response()}
	 */
	public void setResponse (StoreMapperResponse aResponse) {
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
	public void accept (ClientRequestVisitor aProcessor) {
		aProcessor.visitStore (this);

		assert aProcessor.isReactive () == (hasResponse () || hasException ()): "ensure: responded if `aProcessor' is reactive.";
	}

// Implementation
	/**
	 * Path of the entity to store.
	 */
	protected String path;

	/**
	 * Answer of this current request.
	 */
	protected @Nullable StoreMapperResponse response;

}
