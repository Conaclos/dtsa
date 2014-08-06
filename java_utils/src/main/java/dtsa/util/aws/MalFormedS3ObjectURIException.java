package dtsa.util.aws;

/**
 * 
 * @description Use for mal formed S3ObjectURI.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class MalFormedS3ObjectURIException 
	extends Exception {

// Creation
	/**
	 * Create with `aCandidate' as `getCandidate'.
	 * 
	 * @param aStoreId - {@link #getCandidate()}
	 */
	public MalFormedS3ObjectURIException (String aCandidate) {
		super (String.format (message, aCandidate, (new S3ObjectURI ("region", "bucket", "object")).toString ()));
		candidate = aCandidate;
		
		assert getCandidate () == aCandidate: "ensure: `getCandidate' set with `aCandidate'";
	}
	
// Constant
	/**
	 * Serial version.
	 */
	protected static final long serialVersionUID = 1L;
	
	/**
	 * User message.
	 */
	protected final static String message = "URI provided: %s. The URI is malformed, it should be formed as %s.";
	
// Access	
	/**
	 * 
	 * @return Store id where object named `getName' is expected.
	 */
	public String getCandidate () {
		return candidate;
	}
	
// Implementation	
	/**
	 * @see #getCandidate ()
	 */
	protected String candidate;
	
}
