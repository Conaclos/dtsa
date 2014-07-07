package dtsa.mapper.cloud.aws.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;

import dtsa.mapper.util.configuration.LabeledConfigurations;

/**
 * @description Amazon S3 service
 * @author Victorien Elvinger
 * @date 2014/06/26
 *
 */
public class S3Service {
	
// Creation
	/**
	 * USe `aCredentials' as credentials.
	 * @param aCredentials - Amazon credentials.
	 * @param aConfig  -Service configuration.
	 */
	public S3Service (BufferedReader aInput, PrintWriter aOutput, AWSCredentials aCredentials, LabeledConfigurations aConfig) {
		s3 = new AmazonS3Client(aCredentials);
	}
	
// Other
	public void run () {
		
	}
	
// Implementation
	/**
	 * S3 service.
	 */
	protected final AmazonS3Client s3;
	
}
