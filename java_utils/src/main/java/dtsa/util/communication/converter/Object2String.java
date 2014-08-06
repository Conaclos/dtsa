package dtsa.util.communication.converter;


public interface Object2String <G>
	extends Cloneable {
	
// Access
	/**
	 * String representation of `aObject'.
	 * @param aObject - the wrapped object
	 */
	public abstract String value (G aObject);

	/**
	 * @see Object#clone ()
	 */
	public abstract Object2String <G> clone ();
	
// Status
	/**
	 * 
	 * @param aType
	 * @return Is `aType' attached at least one label?
	 */
	public abstract boolean isConvertible (G aObject);
	
}
