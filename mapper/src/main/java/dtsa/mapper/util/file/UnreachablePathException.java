package dtsa.mapper.util.file;

/**
 * 
 * @description Exception for unreachable file or directory.
 * @author Victorien ELvinger
 * @date 2014/06/30
 *
 */
public class UnreachablePathException
		extends Exception {
	
// Creation
	/**
	 * Create with `aPath' as `getPath'.
	 * 
	 * @param aPath - unreachable path.
	 */
	public UnreachablePathException (String aPath) {		
		super (String.format (message, aPath));
		path = aPath;
		
		assert getPath () == aPath: "ensure: `getPath' set with `aPath'";
	}

// Constant
	/**
	 * Serial version.
	 */
	protected static final long serialVersionUID = 1L;
	
	/**
	 * User message.
	 */
	protected final static String message = "Path '%s' is not reachable.";
	
// Access
	/**
	 * @return Unreachable path.
	 */
	public String getPath () {
		return path;
	}
	
// Implementation
	/**
	 * Unreachable not reachable.
	 */
	protected final String path;
	
}
