package dtsa.util.file;

/**
 * 
 * @description Exception for unreachable object on a store.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class UnreachableObjectException
		extends Exception {
	
// Creation
	/**
	 * Create with `aName' as `getName'.
	 * 
	 * @param aName - {@link #getName()}
	 * @param aStoreId - {@link #getStoreId()}
	 */
	public UnreachableObjectException (String aName, String aStoreId) {
		super (String.format (message, aName, aStoreId));
		name = aName;
		storeId = aStoreId;
		
		assert getName () == aName: "ensure: `getName' set with `aName'";
		assert getStoreId () == aStoreId: "ensure: `getName' set with `aName'";
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
	
// Implementation
	/**
	 * @see #getName()
	 */
	protected String name;
	
	/**
	 * @see #getStoreId ()
	 */
	protected String storeId;
	
}
