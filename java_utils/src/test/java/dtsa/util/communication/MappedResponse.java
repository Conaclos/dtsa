package dtsa.util.communication;

import dtsa.util.annotation.Nullable;

/**
 * 
 * @description Server response.
 * @author Victorien ELvinger
 * @date 2014/07/1
 *
 */
public abstract class MappedResponse {
	
// Creation
	/**
	 * Create a normal response, i.e. without exception.
	 */
	public MappedResponse () {
		exception = null;
		
		assert ! hasException (): "ensure: without exception.";
	}
	
	/**
	 * Create with `aException' as `getException'.
	 * @param aException
	 */
	public MappedResponse (Exception aException) {
		exception = aException;
		
		assert getException () == aException: "ensure: `exception' set with `aException'.";
	}
	
// Access
	/**
	 * 
	 * @return Potential exception during request processing. Null if none.
	 */
	public @Nullable Exception getException () {
		return exception;
	}
	
// Status
	/**
	 * 
	 * @return Has an exception?
	 */
	public boolean hasException () {
		return exception != null;
	}
	
// Change
	/**
	 * Set `getException' with `aException'.
	 * @param aException
	 */
	public void setException (Exception aException) {
		exception = aException;
		
		assert getException () == aException: "ensure: `exception' set with `aException'.";
	}
	
// Other
	/**
	 * Visit current response with `aProcessor'.
	 * @pattern Visitor
	 * 
	 * @param aProcessor - a visitor
	 */
	public abstract void process (ResponseProcessor aProcessor);
	
// Implementation
	/**
	 * Potential exception during request processing.
	 */
	protected @Nullable Exception exception;
	
}
