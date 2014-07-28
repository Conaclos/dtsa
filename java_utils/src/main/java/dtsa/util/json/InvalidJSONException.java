package dtsa.util.json;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

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
	@Requires ({
		"aJson != null",
		"aType != null"
	})
	@Ensures ({
		"getJson () == aJson",
		"getType () == aType"
	})
	public InvalidJSONException (String aJson, String aType) {		
		super (String.format (message, aJson, aType));
		json = aJson;
		type = aType;
		
		assert getJson () == aJson: "ensure: `json' set with `aJson'";
		assert getType () == aType: "ensure: `getType' set with `aType'";
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
	@Ensures ("result != null")
	public String getJson () {
		return json;
	}
	
	/**
	 * @return Type expected.
	 */
	@Ensures ("result != null")
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
