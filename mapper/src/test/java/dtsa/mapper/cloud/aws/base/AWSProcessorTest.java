package dtsa.mapper.cloud.aws.base;

import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.Ignore;
import org.junit.Test;

import dtsa.mapper.client.request.StartingInstancesRequest;
import dtsa.mapper.client.request.StoreRequest;
import dtsa.mapper.util.annotation.Nullable;

/**
 * 
 * @description Test AWS facilities.
 * @author Victorien ELvinger
 * @date 2014/07/1
 *
 */
public class AWSProcessorTest {
	
// Constant
	/**
	 * File / directory o store.
	 */
	public final static String fileTest = "store/";
	
// Test
	/**
	 * Test: AWSProcessor.process_store
	 */
	@Test
	@Ignore
	public void testStore () {
		AWSProcessor processor;
		@Nullable URL url = getClass ().getResource ("/" + fileTest);
		
		if (url != null) {
			@Nullable String path = url.getPath ();
			
			if (path != null) {
				processor = new AWSProcessor ();
				processor.processStore (new StoreRequest (path));
			}
			else {
				assertTrue ("file or directory path for testing exists", false);
			}
		}
		else {
			assertTrue ("file or directory for testing exists", false);
		}
	}
	
	/**
	 * Test: AWSProcessor.processStartingInstances
	 */
	@Test
	@Ignore
	public void testStartingInstance () {
		AWSProcessor processor;
		
		processor = new AWSProcessor ();
		processor.processStartingInstances (new StartingInstancesRequest (1));
	}
	
}
