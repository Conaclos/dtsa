package dtsa.util.file;

import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;
import com.google.java.contract.Requires;

/**
 * 
 * @description Exception for failing during file compression step.
 * @author Victorien ELvinger
 * @date 2014/06/30
 *
 */
@Invariant ("getPath () != null")
public class DirectoryCompressionException 
	extends Exception {
	
// Creation
	/**
	 * Create with 'aPath' as `getPath'.
	 * 
	 * @param aPath - {@link #getPath()}
	 */
	@Requires ("aPath != null")
	@Ensures ("getPath () == aPath")
	public DirectoryCompressionException (String aPath) {		
		super (String.format (message, aPath));
		path = aPath;
		
		assert getPath () == aPath: "ensure: `getPath' set with `aPath'";
	}

// Constant
	/**
	 * Serial version.
	 */
	protected static final long serialVersionUID = 1L;
	
	/**
	 * User message.
	 */
	protected final static String message = "Compression of '%s' failed.";
	
// Access
	/**
	 * 
	 * @return Directory path.
	 */
	@Ensures ("result != null")
	public String getPath () {
		return path;
	}
	
// Implementation
	/**
	 * @see #getPath ()
	 */
	protected final String path;
	
}
