package dtsa.util.aws;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class S3ObjectURITest {
// Test
	@Test
	public void testToSTring () {
		S3ObjectURI uri;
		
		uri = new S3ObjectURI ("region-1", "bucket", "object");
		assertTrue ("Good output for S3 object URI", uri.toString ().equals ("s3://region-1/bucket#object"));
	}

	@Test
	public void testParsing () throws MalFormedS3ObjectURIException {
		S3ObjectURI uri;
		
		uri = new S3ObjectURI ("s3://region-1/bucket#object");
		assertTrue ("URI has the expected location", uri.getLocation ().equals ("region-1"));
		assertTrue ("URI has the expected name", uri.getName ().equals ("bucket"));
		assertTrue ("URI has the expected id", uri.getId ().equals ("object"));
	}
	
}
