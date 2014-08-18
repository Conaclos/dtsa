package dtsa.mapped.client.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dtsa.util.communication.base.ExceptionResponse;

/**
 * 
 * @description Reponse signaling exception.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class MappedExceptionResponse 
	extends ExceptionResponse <MappedResponseVisitor> {

// Creation
	/**
	 * Create a response from `e'.
	 * @param e - {@link #getMessage ()}
	 */
	public MappedExceptionResponse (Exception e) {
		message = e.getMessage ();
	}

	/**
	 * Create a response from `aMessage'.
	 * @param aMessage - {@link #getMessage ()}
	 */
	@JsonCreator
	public MappedExceptionResponse (@JsonProperty ("message") String aMessage) {
		message = aMessage;
	}	
	
// Access
	/**
	 * 
	 * @return Exception message.
	 */
	public String getMessage () {
		return message;
	}
	
// Other
	@Override
	public void accept (MappedResponseVisitor aVisitor) {
		aVisitor.visitException (this);
	}
	
// Implementation
	/**
	 * @see #getMessage ()
	 */
	protected String message;
	
}
