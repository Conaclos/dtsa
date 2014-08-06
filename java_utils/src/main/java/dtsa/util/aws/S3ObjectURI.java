package dtsa.util.aws;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @description Represents a virtual S3 bucket URI.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class S3ObjectURI {

// Creation
	/**
	 * 
	 * @param aLocation - {@link #getLocation ()}
	 * @param aName - {@link #getName ()}
	 * @param aId - {@link #getId ()}
	 */
	public S3ObjectURI (String aLocation, String aName, String aId) {
		location = aLocation;
		name = aName;
		id = aId;
		
		assert getLocation () == aLocation: "ensure: `getName' set with `aName'";
		assert getName () == aName: "ensure: `getName' set with `aName'";
		assert getId () == aId: "ensure: `getId' set with `aId'";
	}
	
	/**
	 * Create a s3 bucket URI from `aCandidate'.
	 * @param aCandidate - {@link #toString()}
	 * @throws MalFormedS3ObjectURIException - Triggered if {@link #regex} is not matched.
	 */
	public S3ObjectURI (String aCandidate) throws MalFormedS3ObjectURIException {
		Pattern p = Pattern.compile (regex);
		Matcher m = p.matcher (aCandidate);
		if (m.find ()) {
			location = m.group (1);
			name = m.group (2);
			id = m.group (3);
		}
		else {
			throw new MalFormedS3ObjectURIException (aCandidate);
		}
	}
	
// Constant
	/**
	 * Protocol.
	 */
	public final static String protocol = "s3";
	
	/**
	 * URI pattern.
	 */
	public final static String scheme = protocol + "://%s/%s#%s";

	/**
	 * URI regex.
	 */
	public final static String regex = protocol + "://([-\\.\\w]+)/([-\\.\\w]+)#([-\\.\\w]+)";
	
// Access
	/**
	 * @return Bucket location.
	 */
	public String getLocation () {
		return location;
	}
	
	/**
	 * @return Bucket Name.
	 */
	public String getName () {
		return name;
	}
	
	/**
	 * @return Object id.
	 */
	public String getId () {
		return id;
	}
	
	@Override
	public String toString () {
		return String.format (scheme, location, name, id);
	}
	
// Implementation
	/**
	 * @see #getLocation ()
	 */
	protected String location;
	
	/**
	 * @see #getName ()
	 */
	protected String name;
	
	/**
	 * @see #getId ()
	 */
	protected String id;
	
}
