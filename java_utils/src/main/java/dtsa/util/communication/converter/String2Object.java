package dtsa.util.communication.converter;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

public abstract interface String2Object <G> 
	extends Cloneable {
	
// Access
	/**
	 * @param aValue
	 * @return java instance represented by `aValue'.
	 * @throws Exception
	 */
	@Requires ("aValue != null")
	@Ensures ("result != null")
	public abstract G instance (String aValue) throws Exception;
	
	/**
	 * @see Object#clone ()
	 */
	public abstract String2Object <G> clone ();
	
}
