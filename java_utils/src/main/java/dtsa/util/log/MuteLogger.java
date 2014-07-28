package dtsa.util.log;


public class MuteLogger 
	extends Logger {

// Log
	/**
	 * Do nothing.
	 */
	@Override
	public void logException (Exception e) {}

	/**
	 * Do nothing.
	 */
	@Override
	public void logSuccess (String aMessage) {}

	/**
	 * Do nothing.
	 */
	@Override
	public void logWarning (String aMessage) {}

	/**
	 * Do nothing.
	 */
	@Override
	public void logError (String aMessage) {}
	
}
