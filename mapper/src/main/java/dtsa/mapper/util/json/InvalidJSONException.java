package dtsa.mapper.util.json;

/**
 * 
 * @description Invalid JSON value.
 * @author Victorien ELvinger
 * @date 2014/07/2
 *
 */
public class InvalidJSONException 
	extends Exception {
	
// Creation
	/**
	 * Create with `aJson' as `getJson'.
	 * 
	 * @param aJson - Invalid JSON value.
	 * @param aType - Type expected.
	 */
	public InvalidJSONException (String aJson, String aType) {		
		super (String.format (message, aJson, aType));
		json = aJson;
		type = aType;
		
		assert getJson () == aJson: "ensure: `json' set with `aJson'";
	}

// Constant
	/**
	 * Serial version.
	 */
	protected static final long serialVersionUID = 1L;
	
	/**
	 * User message.
	 */
	protected final static String message = "'%s' should be a json value conforming to '%s'.";
	
// Access
	/**
	 * @return Invalid JSON value.
	 */
	public String getJson () {
		return json;
	}
	
	/**
	 * @return Type expected.
	 */
	public String getType () {
		return type;
	}
	
// Implementation
	/**
	 * Invalid JSON value.
	 */
	protected final String json;
	
	/**
	 * Type expected.
	 */
	protected final String type;
	
}
