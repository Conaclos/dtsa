package dtsa.mapper.client.response;

import dtsa.mapper.util.annotation.NonNull;

/**
 * 
 * @description Response for starting instances request.
 * @author Victorien ELvinger
 * @date 2014/07/2
 *
 */
public class StartingInstancesResponse
	extends Response {
	
// Creation
	/**
	 * Create a normal starting instances response, i.e. without exception.
	 */
	public StartingInstancesResponse () {
		super ();
		
		assert ! hasException (): "ensure: without exception.";
	}
	
	/**
	 * Create a normal starting instances response with `aCount' as `getCount'.
	 * 
	 * @param aURI - Number of instances finally launched.
	 */
	public StartingInstancesResponse (int aCount) {
		count = aCount;
		
		assert getCount () == aCount: "ensure: `getCount' set with `aCount'";
	}
	
	/**
	 * Create a starting instances response with `aException' as `getException'.
	 * @param aException
	 */
	public StartingInstancesResponse (Exception aException) {
		super (aException);
		
		assert getException () == aException: "ensure: `exception' set with `aException'.";
	}

// Access
	/**
	 * Number of instances finally launched.
	 */
	public int getCount () {
		return count;
	}
	
// Other
	@Override
	public void process (@NonNull ResponseProcessor aProcessor) {
		// TODO Auto-generated method stub
		
	}
	
// Implementation
	/**
	 * Number of instances finally launched.
	 */
	protected int count;
	
}
