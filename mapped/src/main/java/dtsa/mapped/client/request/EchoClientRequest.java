package dtsa.mapped.client.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dtsa.mapped.client.response.EchoMappedResponse;
import dtsa.mapped.client.response.MappedExceptionResponse;
import dtsa.util.annotation.Nullable;

/**
 * 
 * @description Request for an echo message. Allow to test layout connection.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class EchoClientRequest 
	extends ClientRequest {

// Creation
	/**
	 * 
	 * @param aId - {@link #getId ()}
	 * @param aHop - {@link #getHop ()}
	 */
	@JsonCreator
	public EchoClientRequest (@JsonProperty ("id") int aId, @JsonProperty ("hop") int aHop) {
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
	public EchoClientRequest (int aId) {
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
	public @Nullable EchoMappedResponse response () {
		return response;
	}

	@Override
	public EchoClientRequest partialCLone () {
		// TODO Auto-generated method stub
		return new EchoClientRequest (id);
	}

// Status
	@Override
	public boolean partialEquals (Object aOther) {
		boolean result;
		
		if (getClass () == aOther.getClass ()) {
			EchoClientRequest temp = (EchoClientRequest) aOther;
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
	public void setResponse (EchoMappedResponse aResponse) {
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
	public void accept (ClientRequestVisitor aProcessor) {
		aProcessor.visitEcho (this);
	}
	
// Implementation
	/**
	 * @see #response ()
	 */
	protected @Nullable EchoMappedResponse response;
	
	/**
	 * @see #getId ()
	 */
	protected int id;
	
	/**
	 * @see #getHop ()
	 */
	protected int hop;
	
}
