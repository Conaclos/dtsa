package dtsa.mapper.cloud.aws.base;

import java.util.Map;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.s3.AmazonS3Client;

import dtsa.mapper.client.request.RequestProcessor;
import dtsa.mapper.client.request.StartingInstancesRequest;
import dtsa.mapper.client.request.StoreRequest;
import dtsa.mapper.client.response.StartingInstancesResponse;
import dtsa.mapper.client.response.StoreResponse;
import dtsa.mapper.cloud.aws.exception.ProfileException;
import dtsa.mapper.util.annotation.Nullable;
import dtsa.mapper.util.configuration.ConfigurationParsingException;
import dtsa.mapper.util.configuration.UnspecifiedParameterException;
import dtsa.mapper.util.file.DirectoryCompressionException;
import dtsa.mapper.util.file.UnreachablePathException;
import static dtsa.mapper.util.configuration.SharedLabeledConfigurations.configurations;

/**
 * 
 * @description Processor for Amazon Web Service
 * @author Victorien ELvinger
 * @date 2014/06/26
 *
 */
public class AWSRequestProcessor
		implements RequestProcessor {
	
// Creation
	public AWSRequestProcessor () {
		
	}
	
// Constant (Global)
	/**
	 * Label for global AWS configuration.
	 */
	public final static String AWSConfigurationLabel = "aws";
	
	/**
	 * Profile configuration key.
	 */
	public final static String AWSProfileKey = "profile";

// Constant (S3)
	/**
	 * Label for S3 configuration.
	 */
	public final static String S3ConfigurationLabel = "s3";
	
	/**
	 * Bucket configuration key.
	 */
	public final static String  S3BucketKey = "bucket";
	
	/**
	 * Bucket location configuration key.
	 */
	public final static String  S3RegionKey = "region";
	
	/**
	 * Bucket access configuration key.
	 */
	public final static String  S3AccessKey = "access";
	
// Constant (EC2)
	/**
	 * Label for EC2 configuration.
	 */
	public final static String EC2ConfigurationLabel = "ec2";
	
	/**
	 * Image Id configuration key.
	 */
	public final static String  EC2ImageIdKey = "imageId";
	
	/**
	 * Instance type configuration key.
	 */
	public final static String  EC2InstanceTypeKey = "instanceType";
	
	/**
	 * Bucket location configuration key.
	 */
	public final static String  EC2RegionKey = "region";
	
	/**
	 * Security group configuration key.
	 */
	public final static String  EC2SecurityGroupKey = "securityGroup";
	
// Status
	/**
	 * @return Does current processor give answer for requests?
	 * Answer: true
	 */
	@Override
	public boolean isReactive () {
		return true;
	}
	
// Processing
	@Override
	public void processStore (StoreRequest aRequest) {
		S3Bucket bucket;
		
		try {
			bucket = defaultBucket ();
			bucket.storeFromPath (aRequest.getPath ());
			aRequest.setResponse (new StoreResponse (bucket.lastStoredURI (), bucket.lastStoredMd5 ())); // TODO set URI
		}
		catch (AmazonServiceException e) {
			aRequest.setResponse (new StoreResponse (e));
			// TODO create an exception processor for logging
			e.printStackTrace();
		}
		catch (UnreachablePathException e) {
			aRequest.setResponse (new StoreResponse (e));
			// TODO create an exception processor for logging
			e.printStackTrace();
		}
		catch (DirectoryCompressionException e) {
			aRequest.setResponse (new StoreResponse (e));
			// TODO create an exception processor for logging
			e.printStackTrace();
		}
		catch (UnspecifiedParameterException e) {
			aRequest.setResponse (new StoreResponse (e));
			// TODO create an exception processor for logging
			e.printStackTrace();
		}
		catch (ConfigurationParsingException e) {
			aRequest.setResponse (new StoreResponse (e));
			// TODO create an exception processor for logging
			e.printStackTrace();
		}
		catch (ProfileException e) {
			aRequest.setResponse (new StoreResponse (e));
			// TODO create an exception processor for logging
			e.printStackTrace();
		}
		
		assert aRequest.hasResponse (): "ensure: `aRequest' has a response.";
	}
	
	@Override
	public void processStartingInstances (StartingInstancesRequest aRequest) {
		EC2Instance instance;
		
		try {
			instance = defaultInstance ();
			instance.launch (aRequest.getInstanceCount ());
			aRequest.setResponse (new StartingInstancesResponse (instance.count));
		}
		catch (AmazonServiceException e) {
			aRequest.setResponse (new StartingInstancesResponse (e));
			// TODO create an exception processor for logging
			e.printStackTrace();
		}
		catch (ConfigurationParsingException e) {
			aRequest.setResponse (new StartingInstancesResponse (e));
			// TODO create an exception processor for logging
			e.printStackTrace();
		}
		catch (UnspecifiedParameterException e) {
			aRequest.setResponse (new StartingInstancesResponse (e));
			// TODO create an exception processor for logging
			e.printStackTrace();
		}
		catch (ProfileException e) {
			aRequest.setResponse (new StartingInstancesResponse (e));
			// TODO create an exception processor for logging
			e.printStackTrace();
		}
		
		assert aRequest.hasResponse (): "ensure: `aRequest' has a response.";
	}
	
// Implementation
	/**
	 * 
	 * @return Bucket specified in S3 configuration file.
	 * @throws UnspecifiedParameterException
	 * @throws ConfigurationParsingException
	 * @throws ProfileException
	 */
	protected S3Bucket defaultBucket () throws UnspecifiedParameterException, ConfigurationParsingException, ProfileException {
		@Nullable Map <String, String> props;
		@Nullable String bucketName, regionName, access;
		@Nullable Region location;
		AmazonS3Client s3Client;
		S3Bucket result;
		
		props = configurations.labeled (S3ConfigurationLabel);
		if (props != null) {
			bucketName = props.get (S3BucketKey);
			regionName = props.get (S3RegionKey);
			access = props.get (S3AccessKey);
			
			if (regionName == null) {
				throw new UnspecifiedParameterException (S3ConfigurationLabel, S3RegionKey);
			}
			else {
				location = Region.getRegion (Regions.fromName (regionName));
				
				if (bucketName == null) {
					throw new UnspecifiedParameterException (S3ConfigurationLabel, S3BucketKey);
				}
				else if (location == null) {
					throw new UnspecifiedParameterException (S3ConfigurationLabel, S3RegionKey);
				}
				else if (access == null) {
					throw new UnspecifiedParameterException (S3ConfigurationLabel, S3AccessKey);
				}
				else {
					s3Client = new AmazonS3Client (credentials ());
					s3Client.setEndpoint ("s3." + regionName);
					s3Client.setRegion (location);
					result = new S3Bucket (bucketName, regionName, access, s3Client);
				}
			}
		}
		else {
			throw new UnspecifiedParameterException (S3ConfigurationLabel, S3BucketKey);
		}
		
		return result;
	}
	
	/**
	 * 
	 * @return Instance specified in EC2 configuration file.
	 * @throws ConfigurationParsingException
	 * @throws UnspecifiedParameterException
	 * @throws ProfileException
	 */
	protected EC2Instance defaultInstance () throws ConfigurationParsingException, UnspecifiedParameterException, ProfileException {
		@Nullable Map <String, String> props;
		@Nullable String instanceType, imageId, securityGroup, regionName;
		AmazonEC2Client ec2Client;
		@Nullable Region location;
		EC2Instance result;
		
		props = configurations.labeled (EC2ConfigurationLabel);
		if (props != null) {
			imageId = props.get (EC2ImageIdKey);
			instanceType = props.get (EC2InstanceTypeKey);
			regionName = props.get (EC2RegionKey);
			securityGroup = props.get (EC2SecurityGroupKey);
			
			if (regionName == null) {
				throw new UnspecifiedParameterException (EC2ConfigurationLabel, EC2RegionKey);
			}
			else {
				location = Region.getRegion (Regions.fromName (regionName));
			
				if (imageId == null) {
					throw new UnspecifiedParameterException (EC2ConfigurationLabel, EC2ImageIdKey);
				}
				else if (instanceType == null) {
					throw new UnspecifiedParameterException (EC2ConfigurationLabel, EC2InstanceTypeKey);
				}
				else if (location == null) {
					throw new UnspecifiedParameterException (EC2ConfigurationLabel, EC2RegionKey);
				}
				else if (securityGroup == null) {
					throw new UnspecifiedParameterException (EC2ConfigurationLabel, EC2SecurityGroupKey);
				}
				else {
					ec2Client = new AmazonEC2Client (credentials ());
					ec2Client.setEndpoint ("ec2." + regionName);
					ec2Client.setRegion (location);
					result = new EC2Instance (imageId, instanceType, regionName, securityGroup, ec2Client);
				}
			}
		}
		else {
			throw new UnspecifiedParameterException (EC2ConfigurationLabel, EC2ImageIdKey);
		}
		
		return result;
	}
	
// Implementation (Credentials)
	/**
	 * Retrieve AWScredentials from AWS local profile.
	 * Profile name is specified into the aws configuration file with `profileKey'.
	 * @throws ConfigurationParsingException 
	 * @throws UnspecifiedParameterException
	 * @throws ProfileException
	 */
	protected AWSCredentials credentials () throws ConfigurationParsingException, UnspecifiedParameterException, ProfileException {
		@Nullable Map <String, String> props;
		@Nullable AWSCredentials result;
		@Nullable String profileName;
		
		result = credentialsCache;
		if (result == null) {
			props = configurations.labeled (AWSConfigurationLabel);
			if (props != null) {
				
				profileName = props.get (AWSProfileKey);
				if (profileName != null) {
					try {
						result = new ProfileCredentialsProvider (profileName).getCredentials ();
						credentialsCache = result;
					}
					catch (Exception e) {
						throw new ProfileException (profileName);
					}
				}
				else {
					throw new UnspecifiedParameterException (AWSConfigurationLabel, AWSProfileKey);
				}
			}
			else {
				throw new UnspecifiedParameterException (AWSConfigurationLabel, AWSProfileKey);
			}
		}
		
		assert result != null: "check: credentials exists";
		return result;
	}
	
	/**
	 * AWS credentials.
	 */
	protected @Nullable AWSCredentials credentialsCache;
	
}
