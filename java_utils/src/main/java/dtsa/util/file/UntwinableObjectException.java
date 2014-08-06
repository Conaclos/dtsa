package dtsa.util.file;

/**
 * 
 * @description Exception for failing during duplication step between remote and local place.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class UntwinableObjectException 
	extends Exception {
	
// Creation
	/**
	 * Create with `aName' as `getName'.
	 * 
	 * @param aName - {@link #getName()}
	 * @param aStoreId - {@link #getStoreId()}
	 * 
	 */
	public UntwinableObjectException (String aName, String aStoreId, String aDirectory) {
		super (String.format (message, aName, aStoreId));
		name = aName;
		storeId = aStoreId;
		directory = aDirectory;
		
		assert getName () == aName: "ensure: `getName' set with `aName'";
		assert getStoreId () == aStoreId: "ensure: `getName' set with `aName'";
		assert getDirectory () == aDirectory: "ensure: `getDirectory' set with `aDirectory'";
	}
	
// Constant
	/**
	 * Serial version.
	 */
	protected static final long serialVersionUID = 1L;
	
	/**
	 * User message.
	 */
	protected final static String message = "Object named '%s' in store `%s' is not found.";
	
	// Access
	/**
	 * @return Unreachable object's name.
	 */
	public String getName () {
		return name;
	}
	
	/**
	 * 
	 * @return Store id where object named `getName' is expected.
	 */
	public String getStoreId () {
		return storeId;
	}
	
	/**
	 * @return Local directory for local twin.
	 */
	public String getDirectory () {
		return directory;
	}
	
// Implementation
	/**
	 * @see #getName()
	 */
	protected String name;
	
	/**
	 * @see #getStoreId ()
	 */
	protected String storeId;
	
	/**
	 * @see #getDirectory ()
	 */
	protected String directory;
	
}
