package dtsa.util.communication.session;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @description Configuration for client sessions.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class ServerSessionConfiguration {

// Creation
	/**
	 * 
	 * @param aTimeout
	 * @param aConcurrent
	 */
	@JsonCreator
	public ServerSessionConfiguration (@JsonProperty ("timeout") int aTimeout, @JsonProperty ("concurrent") boolean aConcurrent) {
		assert aTimeout > 0: "require: `aTimeout > 0' strictly positive";
		
		if (aTimeout <= 0) {
			throw new IllegalArgumentException ("`timeout' should be strictly positive");
		}
		
		timeout = aTimeout;
		concurrent = aConcurrent;
		
		assert getTimeout () == aTimeout: "ensure: `getTimeout' set with `aTimeout'.";
		assert isConcurrent () == aConcurrent: "ensure: `isConcurrent' set with `aConcurrent'.";
	}
	
// Access
	/**
	 * This time should be chosen according to the value of `isConcurrent'
	 * @return Time in seconds to wait a new request.
	 */
	public int getTimeout () {
		return timeout;
	}

	/**
	 * 
	 * @return Is request and response processing concurrent?
	 */
	public boolean isConcurrent () {
		return concurrent;
	}
	
// Implementation
	/**
	 * @see #getTimeout ()
	 */
	protected int timeout;
	
	/**
	 * @see #isConcurrent()
	 */
	protected boolean concurrent;
	
}
