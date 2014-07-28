package dtsa.mapper.cloud.mapped.base;

import dtsa.util.communication.base.RepeatableTask;


public abstract class ServiceAvailability 
	extends RepeatableTask {
	
// Access
	/**
	 * @return Public IP.
	 */
	public abstract String getIp ();

// Status

	public void waitAvailability () throws InterruptedException {
		if (isActive.get ()) {
			wait ();
		}
	}

	public void waitAvailabilityUntil (long aTimeout) throws InterruptedException {
		if (isActive.get ()) {
			wait (aTimeout);
		}
	}

}
