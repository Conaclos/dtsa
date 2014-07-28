package dtsa.mapper.base;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

/**
 * 
 * @description Remote service connection configuration.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class ServiceConfiguration {
	
// Creation
	/**
	 * 
	 * @param aPort
	 *            - {@link #getPort()}
	 */
	@JsonCreator
	@Requires ({
		"1024 <= aPort && aPort <= 65555"
	})
	@Ensures ({
		"getPort () == aPort"
	})
	public ServiceConfiguration (@JsonProperty ("port") int aPort) {
		
		assert 1024 <= aPort && aPort <= 65555: "require: `aPort' is an assignable port.";
		
		if (! (1024 <= aPort && aPort <= 65555)) {
			throw new IllegalArgumentException ("'port' should be included between 1024 and 65555");
		}
		
		port = aPort;
		
		assert getPort () == aPort: "ensure: `getPort' set with `aPort'";
	}
	
// Access
	/**
	 * 
	 * @return Server port.
	 */
	@Ensures ("result != null")
	public int getPort () {
		return port;
	}
	
// Implementation
	/**
	 * @see #getTimeout ()
	 */
	protected int port;
	
}
