package dtsa.util.aws;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @description 
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class SecuryGroupConfiguration {
	
// Creation
	@JsonCreator
	public SecuryGroupConfiguration (@JsonProperty ("name") String aName, @JsonProperty ("prefixedIp") String aPrefixedIp, 
			@JsonProperty ("fromPort") int aFromPort, @JsonProperty ("toPort") int aToPort) {
		
		assert 1 <= aFromPort && aFromPort <= 65555: "require: `aFromPort' is valid.";
		assert 1 <= aToPort && aToPort <= 65555: "require: `aToPort' is valid.";
		assert aFromPort <= aToPort: "require:  `aToPort' is greater or equal to `aFromPort'";
		
		if (aName == null) {
			throw new IllegalArgumentException ("'name' should be not null");
		}
		else if (aPrefixedIp == null) {
			throw new IllegalArgumentException ("'prefixedIp' should be not null");
		}
		else if (! (1 <= aFromPort && aFromPort <= 65555)) {
			throw new IllegalArgumentException ("'fromPort' should be included between 1 and 65555");
		}
		else if (! (1 <= aToPort && aToPort <= 65555)) {
			throw new IllegalArgumentException ("'toPort' should be included between 1 and 65555");
		}
		else if (aToPort < aFromPort) {
			throw new IllegalArgumentException ("'toPort' should be greater or equal to 'fromPort'");
		}
		
		name = aName;
		prefixedIp = aPrefixedIp;
		fromPort = aFromPort;
		toPort = aToPort;
		
		assert getName () == aName: "ensure: `getName' set with `aName'";
		assert getPrefixedIp () == aPrefixedIp: "ensure: `getPrefixedIp' set with `aPrefixedIp'";
		assert getFromPort () == aFromPort: "ensure: `getFromPort' set with `aFromPort'";
		assert getToPort () == aToPort: "ensure: `getToPort' set with `aToPort'";
	}
	
// Access
	/**
	 * 
	 * @return Security Group name.
	 */
	public String getName () {
		return name;
	}

	/**
	 * 
	 * @return Prefixed IP
	 */
	public String getPrefixedIp () {
		return prefixedIp;
	}

	/**
	 * 
	 * @return Minimal port authorized.
	 */
	public int getFromPort () {
		return fromPort;
	}

	/**
	 * 
	 * @return Maximal port authorized.
	 */
	public int getToPort () {
		return toPort;
	}
	
// Implementation
	/**
	 * @see #getName ()
	 */
	protected String name;
	
	/**
	 * @see #getPrefixedIp ()
	 */
	protected String prefixedIp;
	
	/**
	 * @see #getFromPort ()
	 */
	protected int fromPort;
	
	/**
	 * @see #getToPort ()
	 */
	protected int toPort;
	
}
