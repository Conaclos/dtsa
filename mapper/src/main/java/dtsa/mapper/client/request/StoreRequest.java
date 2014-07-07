package dtsa.mapper.client.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dtsa.mapper.client.response.StoreResponse;
import dtsa.mapper.util.annotation.Nullable;

/**
 * @description Request for storing
 * @author Conaclos
 * @date 2014/06/27
 */
public class StoreRequest 
	extends Request {

// Creation	
	/**
	 * Create a store request with `aPath' as `getPath'.
	 * @param aPath -path of the entity to store.
	 */
	@JsonCreator
	public StoreRequest (@JsonProperty ("path") String aPath) {
		path = aPath;
		
		assert getPath () == aPath: "ensure: `getPath' set with `aPath'.";
	}
	
// Access
	@Override
	public @Nullable StoreResponse response () {
		return response;
	}
	
	/**
	 * @return Path of the entity to store.
	 */
	public String getPath () {
		return path;
	}
	
	@Override
	public StoreRequest partialCLone () {
		StoreRequest result;
		
		result = new StoreRequest (path);

		assert this != result: "current object and result have not the same reference.";
		assert partialEquals (result): "ensure: current object and result are partially equal.";
		return result;
	}
	
// Status
	@Override
	public boolean partialEquals (Object aOther) {
		boolean result;
		
		if (getClass () == aOther.getClass ()) {
			@Nullable String otherPath = ((StoreRequest) aOther).getPath ();
			
			if (otherPath != null) {
				result = otherPath.equals (path);
			}
			else {
				result = path != null;
			}
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
	public void setResponse (StoreResponse aResponse) {
		response = aResponse;
		
		assert response () == aResponse: "ensure: `response' set with `aResponse'.";
	}
	
// Other
	@Override
	public void process (RequestProcessor aProcessor) {		
		aProcessor.processStore (this);
		
		assert aProcessor.isReactive () == hasResponse (): "ensure: responded if `aProcessor' is reactive.";
	}
	
// Implementation
	/**
	 * Path of the entity to store.
	 */
	protected String path;
	
	/**
	 * Answer of this current request.
	 */
	protected @Nullable StoreResponse response;
	
}
