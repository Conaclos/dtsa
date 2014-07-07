package dtsa.mapper.util.json;

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
	 * Create an empty Sample.
	 */
	public Sample () {
		name = null;
	}
	
	/**
	 * Create a Sample with `aName' as `getName'.
	 * @param aName
	 */
	public Sample (String aName) {
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
