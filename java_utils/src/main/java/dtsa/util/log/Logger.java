package dtsa.util.log;


public abstract class Logger {
	
// Log
	/**
	 * @param aMessage - success message.
	 */
	public abstract void logSuccess (String aMessage);

	/**
	 * @param aMessage - warning message.
	 */
	public abstract void logWarning (String aMessage);
	
	/**
	 * @param aMessage - error message.
	 */
	public abstract void logError (String aMessage);
	
	/**
	 * Handle `e'.
	 * @param e
	 */
	public abstract void logException (Exception e);
	
}
