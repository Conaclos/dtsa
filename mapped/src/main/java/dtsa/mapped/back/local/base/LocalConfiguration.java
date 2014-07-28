package dtsa.mapped.back.local.base;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @description  Configuration for LocalClientRequestVisitor
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class LocalConfiguration {

// Creation
	/**
	 * 
	 * @param aWorkspace - {@link #getWorkspace ()}
	 */
	@JsonCreator
	public LocalConfiguration (@JsonProperty ("workspace") String aWorkspace, @JsonProperty ("ec") String aEc) {
		assert aWorkspace != null: "require: `aWorkspace' is not null";
		assert aEc != null: "require: `aEc' is not null";
		
		if (aWorkspace == null) {
			throw new IllegalArgumentException ("'workspace' should be not null");
		}
		else if (aEc == null) {
			throw new IllegalArgumentException ("'ec' should be not null");
		}
		
		workspace = aWorkspace;
		ec = aEc;
		
		assert getWorkspace () == aWorkspace: "ensure: Set `getWorkspace' with `aWorkspace'.";
		assert getEc () == aEc: "ensure: Set `getEc' with `aEc'.";
	}
	
// Access
	/**
	 * 
	 * @return Work directory.
	 */
	public String getWorkspace () {
		return workspace;
	}
	
	/**
	 * @return Eiffel Studio Command
	 */
	public String getEc () {
		return ec;
	}
	
// Implementation
	/**
	 * @see #getWorkspace ()
	 */
	protected String workspace;
	
	/**
	 * @see #getEc ()
	 */
	protected String ec;
	
}
