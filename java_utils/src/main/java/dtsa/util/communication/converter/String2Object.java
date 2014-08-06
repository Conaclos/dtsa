package dtsa.util.communication.converter;

public abstract interface String2Object <G> 
	extends Cloneable {
	
// Access
	/**
	 * @param aValue
	 * @return java instance represented by `aValue'.
	 * @throws Exception
	 */
	public abstract G instance (String aValue) throws Exception;
	
	/**
	 * @see Object#clone ()
	 */
	public abstract String2Object <G> clone ();
	
}
