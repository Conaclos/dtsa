package dtsa.util.aws;

import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.Ignore;
import org.junit.Test;

import com.amazonaws.AmazonServiceException;

import dtsa.util.annotation.Nullable;
import dtsa.util.configuration.TypedResources;
import dtsa.util.configuration.UnmatchableTypeException;
import dtsa.util.configuration.UnparsableException;
import dtsa.util.file.DirectoryCompressionException;
import dtsa.util.file.UnreachableObjectException;
import dtsa.util.file.UnreachablePathException;
import dtsa.util.file.UntwinableObjectException;


public class S3BucketTest {
	
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
	 * File / directory o store.
	 */
	public final static String FileTest = "/store/";
	
	/**
	 * File for retrievement.
	 */
	public final static String RetrievingFile = "/retrieved/";
	
// Test
	/**
	 * 
	 * !!! WARNING !!! This test is money consuming.
	 * 
	 */
	@Test
	@Ignore
	public void testStoreAndRetrieveFile () 
			throws UnparsableException, UnmatchableTypeException, AmazonServiceException, 
			UnreachablePathException, DirectoryCompressionException, UnreachableObjectException, 
			UntwinableObjectException {
		
		TypedResources configurations;
		@Nullable URL url;
		S3Bucket s3;
		String name;
		
		url = getClass ().getResource (FileTest);
		if (url != null) {
			configurations = new TypedResources (ResourceConfiguration);
			AWSConfiguration config = configurations.labeled (AWSConfiguration, AWSConfiguration.class);
			
			s3 = (new AWS (config)).defaultBucket ();
			name = "test_" + s3.salt () + ".zip";
			s3.storeFromPathAs (url.getPath (), name);
			
			url = getClass ().getResource (RetrievingFile);
			if (url != null) {
				s3.storeToPath (name, url.getPath ());
			}
			else {
				assertTrue ("retrieved file for testing exists", false);
			}
		}
		else {
			assertTrue ("file or directory for testing exists", false);
		}
	}
	
}
