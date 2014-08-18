package dtsa.util.aws;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairResult;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.KeyPair;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.SecurityGroup;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;

import dtsa.util.annotation.Nullable;

/**
 *
 * @description EC2 instance
 * @author Victorien ELvinger
 * @date 2014/07/1
 *
 */
public class EC2InstancePool {

// Creation
	public EC2InstancePool (EC2InstanceConfiguration aConfiguration, AmazonEC2 aEc2) {
		securityGroup = aConfiguration.getSecurityGroup ();
		instanceType = aConfiguration.getInstanceType ();
		location = aConfiguration.getRegion ();
		imageId = aConfiguration.getImageId ();
		ids = new LinkedList <> ();
		ec2 = aEc2;
		ec2.setEndpoint ("ec2." + aConfiguration.getRegion () + ".amazonaws.com");

		assert ec2 == aEc2: "ensure: `ec2' set with `aEc2'";
		assert imageId ().equals (aConfiguration.getImageId ()): "ensure: `imageId' set with  `aConfiguration.getImageId'";
		assert location ().equals (aConfiguration.getRegion ()): "ensure: `location' set with  `aConfiguration.getRegion'";
		assert instanceType ().equals (aConfiguration.getInstanceType ()): "ensure: `instanceType' set with  `aConfiguration.getInstanceType'";
	}

// Constant
	/**
	 * Key pair prefix.
	 */
	public final static String KeyPairPrefix = "dtsa-kp_";

	/**
	 * Security group prefix.
	 */
	public final static String NewSecurityGroupPrefix = "dtsa-sg_";

	/**
	 * Security group description.
	 */
	public final static String NewSecurityGroupDescription = "Temporary security group";

// Access
	/**
	 *
	 * @return Image Id.
	 */
	public String imageId () {
		return imageId;
	}

	/**
	 *
	 * @return Instance type.
	 */
	public String instanceType () {
		return instanceType;
	}

	/**
	 *
	 * @return Location.
	 */
	public String location () {
		return location;
	}

	/**
	 *
	 * @return Security group.
	 */
	public SecuryGroupConfiguration securityGroup () {
		return securityGroup;
	}

	/**
	 *
	 * @return Number of instance running.
	 */
	public int count () {
		return count;
	}

	/**
	 *
	 * @return Private key.
	 */
	public @Nullable String privateKey () {
		return privateKey;
	}

	/**
	 * @return IDs of instances.
	 */
	public List <String> ids () {
		return ids;
	}

	/**
	 * @return list of public Ips or list with potential null value.
	 */
	public List <String> ips () {
		ArrayList <String> result = new ArrayList <> (ids.size ());

		DescribeInstancesRequest dis = new DescribeInstancesRequest();
		dis.setInstanceIds (ids);

		DescribeInstancesResult disResult = ec2.describeInstances (dis);
		List <Reservation> list  = disResult.getReservations();

		for (Reservation res : list) {
			List <Instance> instancelist = res.getInstances();
			for (Instance instance : instancelist) {
				result.add (instance.getPublicIpAddress ());
			}
		}

		return result;
	}

// Change
	/**
	 *
	 * @param aCount - number of instance to launch
	 */
	public void launch (int aCount) throws AmazonServiceException {
		assert aCount > 0: "require: `aCount' strictly positive.";

		createSecurityGroup ();
		launchInstances (aCount);
	}

	/**
	 * Terminates potential launched instances.
	 */
	public void terminate () {
		TerminateInstancesRequest instanceRequest;
		@Nullable List <String> localIds;

		localIds = ids;
		if (localIds != null) {
			instanceRequest = new TerminateInstancesRequest (localIds);
			ec2.terminateInstances (instanceRequest);
		}
	}

	/**
	 * @param aIds - {@link #ids ()}
	 */
	public void setIds (List <String> aIds) {
		assert ids ().isEmpty (): "require: `ids' not already initialized.";

		ids = aIds;

		assert ids () == aIds: "ensure: `ids' set with `aIds'.";
	}

// Implementation
	/**
	 * Amazon EC2 service.
	 */
	protected AmazonEC2 ec2;

	/**
	 * @see #imageId ()
	 */
	protected String imageId;

	/**
	 * @see #instanceType ()
	 */
	protected String instanceType;

	/**
	 * @see #location ()
	 */
	protected String location;

	/**
	 * @see #securityGroup ()
	 */
	protected SecuryGroupConfiguration securityGroup;

	/**
	 * @see #count ()
	 */
	protected int count;

	/**
	 * @see #privateKey ()
	 */
	protected @Nullable String privateKey;

	/**
	 * @see #ids()
	 */
	protected List <String> ids;

	/**
	 * Create security group `securityGroup' if not existing.
	 */
	protected void createSecurityGroup () {
		AuthorizeSecurityGroupIngressRequest authorization;
		CreateSecurityGroupRequest sgRequest;
		Predicate <SecurityGroup> predicate;
		IpPermission ipPermission;
		
		predicate = (SecurityGroup sg) -> sg.getGroupName ().equals (securityGroup.getName ());
		if (ec2.describeSecurityGroups ().getSecurityGroups ().stream ().noneMatch (predicate)) {
			sgRequest = new CreateSecurityGroupRequest()
					.withGroupName (securityGroup.getName ())
					.withDescription (NewSecurityGroupDescription);
			ec2.createSecurityGroup (sgRequest);
			
			ipPermission = new IpPermission()
					.withIpRanges (securityGroup.getPrefixedIp ())
					.withIpProtocol ("tcp")
					.withFromPort(securityGroup.getFromPort ()).withToPort (securityGroup.getToPort ());
			
			authorization = new AuthorizeSecurityGroupIngressRequest ()
					.withGroupName(securityGroup.getName ())
					.withIpPermissions (ipPermission);
			ec2.authorizeSecurityGroupIngress (authorization);
		}
	}
	
	protected void launchInstances (int aCount) {
		assert aCount > 0: "require: `aCount' strictly positive.";

		RunInstancesRequest instanceRequest;
		CreateKeyPairResult keyPairAnswer;
		RunInstancesResult instanceAnswer;
		String uniqueKeyName;
		long milliseconds;
		KeyPair keyPair;

		milliseconds = Calendar.getInstance ().getTime ().getTime ();
		uniqueKeyName = KeyPairPrefix + milliseconds;
		
		keyPairAnswer = ec2.createKeyPair(new CreateKeyPairRequest().withKeyName(uniqueKeyName));
		keyPair = keyPairAnswer.getKeyPair();
		
		instanceRequest = new RunInstancesRequest()
				.withImageId (imageId)
                .withInstanceType (instanceType)
                .withMinCount (aCount)
                .withMaxCount (aCount)
                .withKeyName (uniqueKeyName)
                .withSecurityGroups (securityGroup.getName ());

		instanceAnswer = ec2.runInstances (instanceRequest);

		for (Instance item : instanceAnswer.getReservation ().getInstances ()) {
			ids.add (item.getInstanceId ());
		}

		count = aCount;
		privateKey = keyPair.getKeyMaterial();
	}

}
