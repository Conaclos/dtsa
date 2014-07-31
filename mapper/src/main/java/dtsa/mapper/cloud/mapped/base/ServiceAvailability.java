package dtsa.mapper.cloud.mapped.base;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import dtsa.util.communication.base.RepeatableTask;


public abstract class ServiceAvailability 
	extends RepeatableTask {
	
// Initialization
	public ServiceAvailability () {
		lock = new ReentrantLock ();
		isAvailable = lock.newCondition ();
	}

// Access
	/**
	 * @return Public IP.
	 */
	public abstract String getIp ();

// Other
	public void waitAvailability () throws InterruptedException {
		lock.lock ();
		
		try {
			while (isActive.get ()) {
				isAvailable.await ();
			}
		}
		finally {
			lock.unlock ();
		}
	}

	/**
	 * 
	 * @param aTimeout - in seconds
	 * @throws InterruptedException
	 */
	public void waitAvailabilityUntil (long aTimeout) throws InterruptedException {
		lock.lock ();
		
		try {
			while (isActive.get ()) {
				isAvailable.await (aTimeout, TimeUnit.SECONDS);
			}
		}
		finally {
			lock.unlock ();
		}
	}
	
// Lock
	protected Lock lock;

	protected Condition isAvailable;
	
}
