package dtsa.util.aws;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dtsa.util.annotation.Nullable;

/**
 * 
 * @description AWS general configuration
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class AWSConfiguration {
	
// Creation
	/**
	 * 
	 * @param aProfile - {@link #getProfile()}
	 * @param aS3 - {@link #getS3()}
	 * @param aEc2 - {@link #getEc2()}
	 */
	@JsonCreator
	public AWSConfiguration (@JsonProperty ("profile") String aProfile, 
			@JsonProperty ("s3") S3BucketConfiguration aS3,
			@JsonProperty ("ec2") EC2InstanceConfiguration aEc2) {
		
		if (aProfile == null) {
			throw new IllegalArgumentException ("'profile' should be not null");
		}
		else if (aS3 == null) {
			throw new IllegalArgumentException ("'s3Configuration' should be not null");
		}
		else if (aEc2 == null) {
			throw new IllegalArgumentException ("'ec2' should be not null");
		}
		
		profile = aProfile;
		ec2 = aEc2;
		s3 = aS3;
		
		assert getProfile () == aProfile: "ensure: `getProfile' set with `aProfile'";
		assert getEc2 () == aEc2: "ensure: `getEc2' set with `aEc2'";
		assert getS3 () == aS3: "ensure: `getS3' set with `aS3'";
	}
	
// Access
	/**
	 * @return profie name.
	 */
	public String getProfile () {
		return profile;
	}
	
	/**
	 * @return AWS S3 configuration.
	 */
	public @Nullable S3BucketConfiguration getS3 () {
		return s3;
	}
	
	/**
	 * @return AWS EC2 configuration.
	 */
	public @Nullable EC2InstanceConfiguration getEc2 () {
		return ec2;
	}
	
// Change
	/**
	 * 
	 * @param aS3 - {@link #getS3 ()}
	 */
	public void setS3 (S3BucketConfiguration aS3) {
		assert s3 == null: "require: `s3' is not initialized";
		
		s3 = aS3;
	}
	
	/**
	 * 
	 * @param aEc2 - {@link #getEc2 ()}
	 */
	public void setEc2 (EC2InstanceConfiguration aEc2) {
		assert ec2 == null: "require: `s3' is not initialized";
		
		ec2 = aEc2;
	}
	
// Implementation
	/**
	 * @see #getProfile()
	 */
	protected String profile;
	
	/**
	 * @see #getS3 ()
	 */
	protected @Nullable S3BucketConfiguration s3;
	
	/**
	 * @see #getEc2 ()
	 */
	protected @Nullable EC2InstanceConfiguration ec2;
	
}
