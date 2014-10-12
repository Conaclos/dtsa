package dtsa.mapper.client.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dtsa.mapper.client.response.MapperExceptionResponse;
import dtsa.mapper.client.response.RetrieveMapperResponse;
import dtsa.util.annotation.Nullable;

/**
 * @description Request for retrieving
 * @author Conaclos
 * @date 2014/06/27
 */
public class RetrieveClientRequest
	extends ClientRequest {

// Creation
	/**
	 * Create a retrieve request with `aPath' as `getPath'.
	 * @param aPath -path of the entity to store.
	 */
	@JsonCreator
	public RetrieveClientRequest (@JsonProperty ("path") String aPath, @JsonProperty ("source") String aSource) {
		path = aPath;
		source = aSource;

		assert getPath () == aPath: "ensure: `getPath' set with `aPath'.";
		assert getSource () == aSource: "ensure: `getSource' set with `aSource'.";
	}

// Access
	@Override
	public @Nullable RetrieveMapperResponse response () {
		return response;
	}

	/**
	 * @return Path where the entity should be stored.
	 */
	public String getPath () {
		return path;
	}

	/**
	 * @return Entity source.
	 */
	public String getSource () {
		return source;
	}

	@Override
	public RetrieveClientRequest partialCLone () {
		RetrieveClientRequest result;

		result = new RetrieveClientRequest (path, source);

		assert this != result: "current object and result have not the same reference.";
		assert partialEquals (result): "ensure: current object and result are partially equal.";
		return result;
	}

// Status
	@Override
	public boolean partialEquals (Object aOther) {
		boolean result;

		if (getClass () == aOther.getClass ()) {
			RetrieveClientRequest temp = (RetrieveClientRequest) aOther;

			result = path.equals (temp.getPath ()) && source.equals (temp.getSource ());
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
	public void setResponse (RetrieveMapperResponse aResponse) {
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
		aProcessor.visitRetrieve (this);

		assert aProcessor.isReactive () == (hasResponse () || hasException ()): "ensure: responded if `aProcessor' is reactive.";
	}

// Implementation
	/**
	 * @see #getPath ()
	 */
	protected String path;

	/**
	 * @see #getSource ()
	 */
	protected String source;

	/**
	 * Answer of this current request.
	 */
	protected @Nullable RetrieveMapperResponse response;

}
