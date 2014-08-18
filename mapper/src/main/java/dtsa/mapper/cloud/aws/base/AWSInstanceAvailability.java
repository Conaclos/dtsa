package dtsa.mapper.cloud.aws.base;

import java.io.IOException;

import dtsa.mapper.cloud.mapped.base.ServiceAvailability;

/**
 *
 * @description
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class AWSInstanceAvailability
	extends ServiceAvailability {

// Creation
	/**
	 *
	 * @param aId
	 * @param aEc2
	 * @param aTimeout - milliseconds between two status checking.
	 */
	public AWSInstanceAvailability (String aIp) {
		super ();
		ip = aIp;
		timeout = DefaultTimeout;
	}

// Constant
	public final static int DefaultTimeout = 8000;

// Access
	@Override
	public String getIp () {
		return ip;
	}

// Implementation
	/**
	 * Public IP.
	 */
	protected String ip;

	/**
	 * Timeout between two status check in milliseconds.
	 */
	protected long timeout;

	@Override
	protected void process () {

		try {
			Thread.sleep (timeout);
		}
		catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println (ip);
		Process p;
		boolean ipReachable = false;

		try {
			p = Runtime.getRuntime().exec("ping " + ip);
			ipReachable = p.waitFor() == 0;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (ipReachable) {
			isActive.set (false);

			try {
				Thread.sleep (timeout * 5);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			lock.lock ();
			isAvailable.signalAll ();
			lock.unlock ();
		}
	}

}
