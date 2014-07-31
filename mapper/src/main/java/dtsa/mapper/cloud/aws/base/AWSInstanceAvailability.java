package dtsa.mapper.cloud.aws.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;

import dtsa.mapper.cloud.mapped.base.ServiceAvailability;


public class AWSInstanceAvailability 
	extends ServiceAvailability {

// Creation
	/**
	 * 
	 * @param aId
	 * @param aEc2
	 * @param aTimeout - milliseconds between two status checking.
	 */
	public AWSInstanceAvailability (String aId, AmazonEC2Client aEc2, int aTimeout) {
		super ();
		id = new ArrayList <> (1);
		id.add (aId);
		ec2 = aEc2;
		timeout = aTimeout;
		ip = "";
	}
	
// Access
	@Override
	public String getIp () {
		return ip;
	}
	
// Implementation
	/**
	 * Instance Id to watch.
	 */
	protected List <String> id;
	
	/**
	 * Public IP.
	 */
	protected String ip;
	
	/**
	 * EC2
	 */
	protected AmazonEC2Client ec2;
	
	/**
	 * Timeout between two status check in milliseconds.
	 */
	protected long timeout;
	
	protected final static String RunningStatus = "running";
	
	@Override
	protected void process () {
		String status = instanceStatus ();
		Process p;
		boolean ipReachable;
		
		System.out.println ("status : " + status);
		
		if (status.equals (RunningStatus)) {
			try {
				do {
					System.out.println ("pending ip");
					Thread.sleep (timeout);
					
					p = Runtime.getRuntime().exec("ping " + ip);
					ipReachable = p.waitFor() == 0;
				} while (! ipReachable);
				
				System.out.println ("before");
				Thread.sleep (timeout * 10);
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			isActive.set (false);
			
			lock.lock ();
			isAvailable.signalAll ();
			lock.unlock ();
		}
		else {
			try {
				Thread.sleep (timeout);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected String instanceStatus () {
		String result;
		
		DescribeInstancesRequest dis = new DescribeInstancesRequest();
		dis.setInstanceIds (id);
		
		DescribeInstancesResult disResult = ec2.describeInstances (dis);
		List <Reservation> list  = disResult.getReservations();
		
		result = "";
		for (Reservation res : list) {
			List <Instance> instancelist = res.getInstances();
			for (Instance instance : instancelist) {
				result = instance.getState ().getName ();
				ip = instance.getPublicIpAddress ();
			}
		}
		
		return result;
	}
	
}
