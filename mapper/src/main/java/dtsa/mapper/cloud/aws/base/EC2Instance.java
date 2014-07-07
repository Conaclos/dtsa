package dtsa.mapper.cloud.aws.base;

import java.util.Calendar;
import java.util.function.Predicate;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Region;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairResult;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.KeyPair;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.SecurityGroup;

import dtsa.mapper.util.annotation.Nullable;

/**
 * 
 * @description EC2 instance
 * @author Victorien ELvinger
 * @date 2014/07/1
 *
 */
public class EC2Instance {
	
// Creation
	public EC2Instance (String aImageId, String aInstanceType, Region aLocation, String aSecurityGroup, AmazonEC2 aEc2) {
		securityGroup = aSecurityGroup;
		instanceType = aInstanceType;
		location = aLocation;
		imageId = aImageId;
		ec2 = aEc2;
		
		for (SecurityGroup sg : ec2.describeSecurityGroups ().getSecurityGroups ()) {
			System.out.println (sg.getGroupName ());
		}
		
		assert ec2 == aEc2: "ensure: `ec2' set with `aEc2'";
		assert imageId ().equals (aImageId): "ensure: `imageId' set with  `aImageId'";
		assert location ().equals (aLocation): "ensure: `location' set with  `aLocation'";
		assert instanceType ().equals (aInstanceType): "ensure: `instanceType' set with  `aInstanceType'";
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
	public Region location () {
		return location;
	}
	
	/**
	 * 
	 * @return Security group.
	 */
	public String securityGroup () {
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
	
// Change
	/**
	 * 
	 * @param aCount - number of instance to launch
	 */
	public void launch (int aCount) throws AmazonServiceException {
		assert aCount > 0: "require: `aCount' strictly positive.";
		
		ec2.setRegion (location);
		createSecurityGroup ();
		launchInstances (aCount);
	}
	
// Implementation
	/**
	 * Image name.
	 */
	protected String imageId;
	
	/**
	 * Instance type.
	 */
	protected String instanceType;
	
	/**
	 * Bucket location.
	 */
	protected Region location;
	
	/**
	 * Security group.
	 */
	protected String securityGroup;
	
	/**
	 * Amazon EC2 service.
	 */
	protected AmazonEC2 ec2;
	
	/**
	 * Number of instance running.
	 */
	protected int count;
	
	/**
	 * Private key.
	 */
	protected @Nullable String privateKey;
	
	/**
	 * Create security group `securityGroup' if not existing.
	 */
	protected void createSecurityGroup () {
		AuthorizeSecurityGroupIngressRequest authorization;
		CreateSecurityGroupRequest sgRequest;
		Predicate <SecurityGroup> predicate;
		IpPermission ipPermission;
		
		predicate = (SecurityGroup sg) -> sg.getGroupName ().equals (securityGroup);
		if (ec2.describeSecurityGroups ().getSecurityGroups ().stream ().noneMatch (predicate)) {
			sgRequest = new CreateSecurityGroupRequest()
					.withGroupName (securityGroup)
					.withDescription (NewSecurityGroupDescription);
			ec2.createSecurityGroup (sgRequest);
			
			ipPermission = new IpPermission()
					.withIpRanges ("0.0.0.0/0")
					.withIpProtocol ("tcp")
					.withFromPort(22);
			
			authorization = new AuthorizeSecurityGroupIngressRequest ()
					.withGroupName(securityGroup)
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
                .withSecurityGroups (securityGroup);
		
		instanceAnswer = ec2.runInstances (instanceRequest);
		
		count = aCount;
		privateKey = keyPair.getKeyMaterial();
	}
	
}
