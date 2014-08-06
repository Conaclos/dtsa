package dtsa.util.file;

/**
 * 
 * @description Exception for fail in the storing step.
 * @author Victorien ELvinger
 * @date 2014/06/30
 *
 */
public class UnstorableFileException
		extends Exception {
	
// Creation
	/**
	 * Create with `aPath' as `getPath' and `aName' as `getName'.
	 * 
	 * @param aName - {@link #getName()}
	 * @param aPath - {@link #getPath()}
	 */
	public UnstorableFileException (String aName, String aPath) {
		super (String.format (message, aPath, aName));
		name = aName;
		path = aPath;

		assert getName () == aName: "ensure: `getName' set with `aName'";
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
	protected final static String message = "Storing of '%s' in the storage '%s' failed.";
	
// Access
	/**
	 * @return Path of the file to store.
	 */
	public String getName () {
		return name;
	}
	
	/**
	 * @return Path of the file to store.
	 */
	public String getPath () {
		return path;
	}
	
// Implementation
	/**
	 * @see #getName ()
	 */
	protected String name;
	
	/**
	 * @see #getPath ()
	 */
	protected String path;
	
}
