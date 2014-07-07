package dtsa.mapper.util.configuration;

/**
 * 
 * @description Exception for missing parameter in a configuration file.
 * @author Victorien ELvinger
 * @date 2014/07/1
 * 
 */
public class UnspecifiedParameterException
		extends Exception {

// Creation
	/**
	 * Create with `aLabel' as `getLabel' and `aKey' as `getKey' .
	 * 
	 * @param aPath - directory path.
	 */
	public UnspecifiedParameterException (String aLabel, String aKey) {		
		super (String.format (message, aKey, aLabel));
		label = aLabel;
		key = aKey;
		
		assert getLabel () == aLabel: "ensure: `getLabel' set with `aPath'";
		assert getKey () == aKey: "ensure: `getKey' set with `aKey'";
	}

// Constant
	/**
	 * Serial version.
	 */
	protected static final long serialVersionUID = 1L;
	
	/**
	 * User message.
	 */
	protected final static String message = "Parameter '%s' is unspecified in the configuration '%s'.";
	
// Access
	/**
	 * 
	 * @return Configuration label.
	 */
	public String getLabel () {
		return label;
	}
	
	/**
	 * 
	 * @return Parameter key.
	 */
	public String getKey () {
		return key;
	}
	
// Implementation
	/**
	 * Configuration label.
	 */
	protected final String label;
	
	/**
	 * Parameter key.
	 */
	protected final String key;
	
}
