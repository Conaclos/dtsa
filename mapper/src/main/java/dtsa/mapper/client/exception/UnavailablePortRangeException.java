package dtsa.mapper.client.exception;

/**
 * 
 * @description Exception denoting an unavailable port range.
 * @author Victorien Elvinger
 * @date 2014/06/25
 * 
 */
public class UnavailablePortRangeException 
	extends Exception {
	
// Creation
	/**
	 * Set 'name' with 'aName'.
	 * 
	 * @param aName - not null name
	 */
	public UnavailablePortRangeException (int aLowerPort, int aUpperPort) {		
		super (String.format (message, aLowerPort, aUpperPort));
		lowerPort = aLowerPort;
		upperPort = aUpperPort;
		
		assert lowerPort == aLowerPort: "ensure: `lowerPort' set with `aLowerPort'";
		assert upperPort == aUpperPort: "ensure: `upperPort' set with `aUpperPort'";
	}

// Configuration
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * User message.
	 */
	protected final static String message = "Port betwwen %i and %i are already in use.";
	
// Access
	/**
	 * Smallest port of the range.
	 */
	public int lowerPort () {
		return lowerPort;
	}

	/**
	 * Largest port of the range.
	 */
	public int upperPort () {
		return upperPort;
	}
	
// Implementation
	/**
	 * Smallest port of the range.
	 */
	protected final int lowerPort;
	
	/**
	 * Largest port of the range.
	 */
	protected final int upperPort;
	
}
