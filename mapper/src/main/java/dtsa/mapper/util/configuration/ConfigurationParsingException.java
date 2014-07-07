package dtsa.mapper.util.configuration;

/**
 * 
 * @description Exception for parsing error.
 * @author Victorien ELvinger
 * @date 2014/07/1
 *
 */
public class ConfigurationParsingException
		extends Exception {

// Creation
	/**
	 * Create with `aLabel' as `getLabel'.
	 * 
	 * @param aPath - COnfiguration file.
	 */
	public ConfigurationParsingException (String aLabel) {		
		super (String.format (message, aLabel));
		label = aLabel;
		
		assert getLabel () == aLabel: "ensure: `getLabel' set with `aLabel'";
	}

// Constant
	/**
	 * Serial version.
	 */
	protected static final long serialVersionUID = 1L;
	
	/**
	 * User message.
	 */
	protected final static String message = "Parsing of the configuration '%s' failed.";
	
// Access
	/**
	 * 
	 * @return Configuration label
	 */
	public String getLabel () {
		return label;
	}
	
// Implementation
	/**
	 * Configuration label.
	 */
	protected final String label;

}
