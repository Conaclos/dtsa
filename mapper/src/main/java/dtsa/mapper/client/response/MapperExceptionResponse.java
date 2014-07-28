package dtsa.mapper.client.response;

import dtsa.util.annotation.NonNull;
import dtsa.util.communication.base.ExceptionResponse;

/**
 * 
 * @description Response signaling exception.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class MapperExceptionResponse 
	extends ExceptionResponse <MapperResponseVisitor> {
	
// Creation
	/**
	 * Create a response from `e'.
	 * @param e - {@link #getMessage ()}
	 */
	public MapperExceptionResponse (Exception e) {
		message = e.getMessage ();
	}
	/**
	 * Create a response from `aMessage'.
	 * @param aMessage - {@link #getMessage ()}
	 */
	public MapperExceptionResponse (String aMessage) {
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
	public void accept (@NonNull MapperResponseVisitor aVisitor) {
		aVisitor.visitException (this);
	}
	
// Implementation
	/**
	 * @see #getMessage ()
	 */
	protected String message;
	
}
