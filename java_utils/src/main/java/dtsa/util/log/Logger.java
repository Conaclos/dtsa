package dtsa.util.log;

import com.google.java.contract.Requires;


public abstract class Logger {
	
// Log
	/**
	 * @param aMessage - success message.
	 */
	@Requires ("aMessage != null")
	public abstract void logSuccess (String aMessage);

	/**
	 * @param aMessage - warning message.
	 */
	@Requires ("aMessage != null")
	public abstract void logWarning (String aMessage);
	
	/**
	 * @param aMessage - error message.
	 */
	@Requires ("aMessage != null")
	public abstract void logError (String aMessage);
	
	/**
	 * Handle `e'.
	 * @param e
	 */
	@Requires ("e != null")
	public abstract void logException (Exception e);
	
}
