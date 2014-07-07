package dtsa.mapper.cloud.aws.exception;

/**
 * 
 * @description Exception for profile errors.
 * @author Victorien ELvinger
 * @date 2014/06/30
 *
 */
public class ProfileException
		extends Exception {

// Creation
	/**
	 * Create with `aProfileName' as `getProfileName'.
	 * 
	 * @param aProfileName - Profile name.
	 */
	public ProfileException (String aProfileName) {		
		super (String.format (message, aProfileName));
		profileName = aProfileName;
		
		assert getProfileName () == aProfileName: "ensure: `getProfileName' set with `aProfileName'";
	}

// Constant
	/**
	 * Serial version.
	 */
	protected static final long serialVersionUID = 1L;
	
	/**
	 * User message.
	 */
	protected final static String message = "Profile '%s' is inexistent or have erroneous credentials.";
	
// Access
	/**
	 * 
	 * @return Profile name.
	 */
	public String getProfileName () {
		return profileName;
	}
	
// Implementation
	/**
	 * Profile name.
	 */
	protected final String profileName;
}
