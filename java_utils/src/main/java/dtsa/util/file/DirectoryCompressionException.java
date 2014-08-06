package dtsa.util.file;

/**
 * 
 * @description Exception for failing during file compression step.
 * @author Victorien ELvinger
 * @date 2014/06/30
 *
 */
public class DirectoryCompressionException 
	extends Exception {
	
// Creation
	/**
	 * Create with 'aPath' as `getPath'.
	 * 
	 * @param aPath - {@link #getPath()}
	 */
	public DirectoryCompressionException (String aPath) {		
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
	protected final static String message = "Compression of '%s' failed.";
	
// Access
	/**
	 * 
	 * @return Directory path.
	 */
	public String getPath () {
		return path;
	}
	
// Implementation
	/**
	 * @see #getPath ()
	 */
	protected final String path;
	
}
