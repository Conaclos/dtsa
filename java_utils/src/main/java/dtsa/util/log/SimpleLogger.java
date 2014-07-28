package dtsa.util.log;


public class SimpleLogger
		extends Logger {
	
// Log
	@Override
	public void logException (Exception e)  {
		e.printStackTrace ();
	}

	@Override
	public void logSuccess (String aMessage) {
		System.out.println (aMessage);
	}

	@Override
	public void logWarning (String aMessage) {
		System.out.println (aMessage);
	}

	@Override
	public void logError (String aMessage) {
		System.out.println (aMessage);
	}
	
}
