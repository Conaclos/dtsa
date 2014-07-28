package dtsa.mapped.client.response;

import dtsa.util.annotation.NonNull;
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
