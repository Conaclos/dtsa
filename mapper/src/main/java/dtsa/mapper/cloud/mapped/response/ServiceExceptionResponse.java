package dtsa.mapper.cloud.mapped.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dtsa.util.communication.base.ExceptionResponse;

/**
 * 
 * @description Response signaling exception.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class ServiceExceptionResponse 
	extends ExceptionResponse <ServiceResponseVisitor> {
	
// Creation
	/**
	 * Create a response from `e'.
	 * @param e - {@link #getMessage ()}
	 */
	public ServiceExceptionResponse (Exception e) {
		message = e.getMessage ();
	}
	
	/**
	 * Create a response from `e'.
	 * @param aMessage - {@link #getMessage ()}
	 */
	@JsonCreator
	public ServiceExceptionResponse (@JsonProperty ("message") String aMessage) {
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
	public void accept (ServiceResponseVisitor aVisitor) {
		aVisitor.visitException (this);
	}
	
// Implementation
	/**
	 * @see #getMessage ()
	 */
	protected String message;
	
}
