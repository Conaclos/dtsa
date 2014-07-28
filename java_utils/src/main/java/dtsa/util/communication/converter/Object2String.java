package dtsa.util.communication.converter;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;


public interface Object2String <G>
	extends Cloneable {
	
// Access
	/**
	 * String representation of `aObject'.
	 * @param aObject - the wrapped object
	 */
	@Requires ({
		"aObject != null",
		"isConvertible (aObject)"
	})
	@Ensures ({
		"result != null"
	})
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
	@Requires ("aType != null")
	public abstract boolean isConvertible (G aObject);
	
}
