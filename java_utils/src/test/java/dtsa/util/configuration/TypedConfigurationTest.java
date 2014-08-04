package dtsa.util.configuration;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import dtsa.util.aws.AWSConfiguration;
import dtsa.util.aws.EC2InstanceConfiguration;
import dtsa.util.aws.S3BucketConfiguration;
import dtsa.util.communication.session.ServerConfiguration;

public class TypedConfigurationTest {
// Constant
	/**
	 * Directory for configuration files.
	 */
	public final static String ResourceConfiguration = "/configurations/";
	
	/**
	 * AWS configuration filename.
	 */
	public final static String AWSConfiguration = "aws.json";
	
	/**
	 * Server configuration filename.
	 */
	public final static String ServerConfiguration = "server.json";
	
// Test
	@Test
	@Ignore
	public void testConfigurationRetrieving () throws UnparsableException, UnmatchableTypeException {
		TypedResources configurations;
		
		configurations = new TypedResources (ResourceConfiguration);
		
		ServerConfiguration config = configurations.labeled (ServerConfiguration, ServerConfiguration.class);
		assertEquals ("same port", config.getPort (), 2014);
		assertTrue ("same exceptionLogging", config.isExceptionLogging ());
		
		AWSConfiguration config1 = configurations.labeled (AWSConfiguration, AWSConfiguration.class);
		assertEquals ("same profile", config1.getProfile (), "default");
		
		EC2InstanceConfiguration config2 = config1.getEc2 ();
		assertEquals ("same image id", config2.getImageId (), "ami-4518d032");
		assertEquals ("same instance type", config2.getInstanceType (), "m3.medium");
		assertEquals ("same region", config2.getRegion (), "eu-west-1");
		assertEquals ("same security group", config2.getSecurityGroup (), "dtsa");
		
		S3BucketConfiguration config3 = config1.getS3 ();
		assertEquals ("same bucket name", config3.getBucket (), "dtsa");
		assertEquals ("same region", config3.getRegion (), "eu-west-1");
		assertEquals ("same access visibility", config3.getAccess (), "private");
	}
	
	@Test (expected = UnmatchableTypeException.class)
	public void testUnmatchableType () throws UnparsableException, UnmatchableTypeException {
		TypedResources configurations;
		
		configurations = new TypedResources (ResourceConfiguration);
		S3BucketConfiguration config3 = configurations.labeled ("s3-unmatchable.json", S3BucketConfiguration.class);
	}
	
	@Test (expected = UnparsableException.class)
	public void testNotFound () throws UnparsableException, UnmatchableTypeException {
		TypedResources configurations;
		
		configurations = new TypedResources (ResourceConfiguration);
		S3BucketConfiguration config3 = configurations.labeled ("s3-unmatchable_json", S3BucketConfiguration.class);
	}
	
}
