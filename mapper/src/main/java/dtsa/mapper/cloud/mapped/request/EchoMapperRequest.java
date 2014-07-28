package dtsa.mapper.cloud.mapped.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dtsa.mapper.client.response.EchoMapperResponse;
import dtsa.mapper.client.response.MapperExceptionResponse;
import dtsa.mapper.cloud.mapped.response.EchoServiceResponse;
import dtsa.mapper.cloud.mapped.response.ServiceExceptionResponse;
import dtsa.util.annotation.NonNull;
import dtsa.util.annotation.Nullable;

/**
 * 
 * @description Request for an echo message. Allow to test layout connection.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class EchoMapperRequest 
	extends MapperRequest {

// Creation
	/**
	 * 
	 * @param aId - {@link #getId ()}
	 * @param aHop - {@link #getHop ()}
	 */
	@JsonCreator
	public EchoMapperRequest (@JsonProperty ("id") int aId, @JsonProperty ("hop") int aHop) {
		id = aId;
		if (aHop < MinimalHop) {
			hop = MinimalHop;
		}
		else {
			hop = aHop;
		}
		
		assert id == aId: "ensure: set `id' with `aId'.";
		assert aHop >= MinimalHop || hop == MinimalHop: "ensure: `hop' set with `MinimalHop'.";
		assert aHop < MinimalHop || hop == aHop: "ensure: `hop' set with `aHop'.";
	}
	/**
	 * 
	 * @param aId - {@link #getId ()}
	 */
	public EchoMapperRequest (int aId) {
		id = aId;
		hop = MinimalHop;
		
		assert id == aId: "ensure: set `id' with `aId'.";
		assert hop == MinimalHop: "ensure: `hop' set with `MinimalHop'.";
	}
	
// Access
	/**
	 * @return Message id.
	 */
	public int getId () {
		return id;
	}
	
	public final static int MinimalHop = 1;
	
	/**
	 * @return Hop Number.
	 */
	public int getHop () {
		return hop;
	}
	
	@Override
	public @Nullable EchoServiceResponse response () {
		return response;
	}

	@Override
	public EchoMapperRequest partialCLone () {
		// TODO Auto-generated method stub
		return new EchoMapperRequest (id);
	}

// Status
	@Override
	public boolean partialEquals (Object aOther) {
		boolean result;
		
		if (getClass () == aOther.getClass ()) {
			EchoMapperRequest temp = (EchoMapperRequest) aOther;
			result = temp.getHop () == hop && temp.getId () == id;
		}
		else {
			result = false;
		}
		
		return result;
	}
	
// Change
	/**
	 * Set `response' with `aResponse'
	 * @param aResponse - response of this current request.
	 */
	public void setResponse (EchoServiceResponse aResponse) {
		assert ! hasException (): "require: has not exception.";
		
		response = aResponse;
		
		assert response () == aResponse: "ensure: `response' set with `aResponse'.";
	}
	
	/**
	 * Set `exception' with `aException'.
	 * @param aException - {@link #exception()}
	 */
	public void setException (ServiceExceptionResponse aException) {
		assert ! hasResponse (): "require: has not response.";
		
		exception = aException;

		assert exception () == aException: "ensure: `exception' set with `aException'.";
	}

// Other
	@Override
	public void accept (MapperRequestVisitor aProcessor) {
		aProcessor.visitEcho (this);
	}
	
// Implementation
	/**
	 * @see #response ()
	 */
	protected @Nullable EchoServiceResponse response;
	
	/**
	 * @see #getId ()
	 */
	protected int id;
	
	/**
	 * @see #getHop ()
	 */
	protected int hop;
	
}
