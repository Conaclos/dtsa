package dtsa.util.configuration;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

/**
 * 
 * @description Exception for unmatchable type with the object.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class UnmatchableTypeException
		extends Exception {
	
// Creation
	/**
	 * 
	 * @param aResourcePath
	 *            - {@link #getResourcePath ()}
	 * @param aType
	 *            - {@link #getType ()}
	 */
	@Requires ({
		"aResourcePath != null",
		"aType != null"
	})
	@Ensures ({
		"getResourcePath () == aResourcePath",
		"getType () == aType"
	})
	public UnmatchableTypeException (String aResourcePath, Class <?> aType) {
		super (String.format (message, aResourcePath, aType.getName ()));
		
		resourcePath = aResourcePath;
		type = aType;
		
		assert getResourcePath () == aResourcePath: "ensure: `getResourcePath' set with `aResourcePath'";
		assert getType () == aType: "ensure: `getType' set with `aType'";
	}
	
// Constant
	/**
	 * SErial number.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * User message.
	 */
	protected final static String message = "Resource '%s' is not convertible to type '%s'.";
	
// Access
	/**
	 * 
	 * @return Path of the resource, relative to 'resources' directory.
	 */
	@Ensures ("result != null")
	public String getResourcePath () {
		return resourcePath;
	}
	
	/**
	 * 
	 * @return Type expected of the resource.
	 */
	@Ensures ("result != null")
	public Class <?> getType () {
		return type;
	}
	
// Implementation
	/**
	 * @see #getResourcePath ()
	 */
	protected String resourcePath;
	
	/**
	 * @see #getType ()
	 */
	protected Class <?> type;
	
}
