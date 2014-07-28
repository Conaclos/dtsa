package dtsa.util.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @description Simple class for json testing.
 * @author Victorien ELvinger
 * @date 2014/06/30
 *
 */
public class Sample {
// Creation
	/**
	 * Create a Sample with `aName' as `getName'.
	 * @param aName
	 */
	@JsonCreator
	public Sample (@JsonProperty ("name") String aName) {
		name = aName;
		
		assert getName () == aName: "ensure: `getName' set with `aName'";
	}
	
// Access
	/**
	 * @return name.
	 */
	public String getName () {
		return name;
	}
	
// Chnage
	/**
	 * Set `getName' with `aName'.
	 * @param aName
	 */
	public void setName (String aName) {
		name = aName;
		
		assert getName () == aName: "ensure: `getName' set with `aName'";
	}
	
// Implementation
	/**
	 * Name.
	 */
	protected String name;
	
}
