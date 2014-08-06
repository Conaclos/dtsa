package dtsa.util.aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.s3.AmazonS3Client;

import dtsa.util.annotation.Nullable;
import dtsa.util.configuration.UnmatchableTypeException;
import dtsa.util.configuration.UnparsableException;

/**
 * 
 * @description High Level interface with AWS.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class AWS {
	
// Creation
	/**
	 * 
	 * @param aConfiguration - service configurations.
	 */
	public AWS (AWSConfiguration aConfiguration) {
		configuration = aConfiguration;
		
		assert configuration == aConfiguration: "ensure: `configuration' set with `aConfiguration'";
	}
	
// Access
	/**
	 * 
	 * @return Pool created from `configuration'.
	 * @throws UnparsableException
	 * @throws UnmatchableTypeException
	 */
	public EC2InstancePool newInstancePool () throws UnparsableException, UnmatchableTypeException {
		return new EC2InstancePool (configuration.getEc2 (), new AmazonEC2Client (defaultCredentials ()));
	}
	
	/**
	 * 
	 * @return Configured bucket.
	 * @throws AmazonServiceException
	 * @throws UnparsableException
	 * @throws UnmatchableTypeException
	 */
	public S3Bucket defaultBucket () throws AmazonServiceException, UnparsableException, UnmatchableTypeException {
		@Nullable S3Bucket result;
		
		result = defaultBucketCache;
		if (result == null) {
			result = new S3Bucket (configuration.getS3 (), new AmazonS3Client (defaultCredentials ()));
			defaultBucketCache = result;
		}
		
		assert result != null;
		return result;
	}
	
// Implementation
	/**
	 * AWS configuration
	 */
	protected AWSConfiguration configuration;
	
// Implementation (S3)	
	/**
	 * Cache for `defaultBucket'.
	 */
	protected @Nullable S3Bucket defaultBucketCache;
	
// Implementation (Credentials)
	/**
	 * Retrieve AWScredentials from AWS local profile.
	 * Profile name is specified into the aws configuration file with `profileKey'.
	 * @throws UnmatchableTypeException 
	 * @throws UnparsableException
	 */
	protected AWSCredentials defaultCredentials () throws UnparsableException, UnmatchableTypeException  {
		@Nullable AWSCredentials result;
		
		result = defaultCredentialsCache;
		if (result == null) {
			result = new ProfileCredentialsProvider (configuration.getProfile ()).getCredentials ();
			defaultCredentialsCache = result;
		}
		
		assert result != null: "check: credentials exists";
		return result;
	}
	
	/**
	 * Cache for `defaultCredentials'.
	 */
	protected @Nullable AWSCredentials defaultCredentialsCache;
	
}
