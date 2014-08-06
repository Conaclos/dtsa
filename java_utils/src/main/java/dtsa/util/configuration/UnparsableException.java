package dtsa.util.configuration;

/**
 * 
 * @description Exception for resource not found or parsing errors.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class UnparsableException 
	extends Exception {
	
// Creation
	/**
	 * 
	 * @param aResourcePath - {@link #getResourcePath ()}
	 */
	public UnparsableException (String aResourcePath) {
		super (String.format (message, aResourcePath));
		
		resourcePath = aResourcePath;
		
		assert getResourcePath () == aResourcePath: "ensure: `getResourcePath' set with `aResourcePath'";
	}

// Constant
	/**
	 * Srial number.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * User message.
	 */
	protected final static String message = "Resource '%s' is not existing or has a syntax error.";
	
// Access
	/**
	 * 
	 * @return Path of the resource, relative to 'resources' directory.
	 */
	public String getResourcePath () {
		return resourcePath;
	}
	
// Implementation
	/**
	 * @see #getResourcePath ()
	 */
	protected String resourcePath;
}
