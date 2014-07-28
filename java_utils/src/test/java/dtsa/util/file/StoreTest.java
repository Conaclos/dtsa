package dtsa.util.file;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import dtsa.util.annotation.Nullable;


public class StoreTest {
	
// Constant
	/**
	 * DIrectory for testing relative to the resources directory.
	 */
	public final static String directoryTest = "store/";
	
// Test
	/**
	 * Test:
	 * - Store.storeDirectory
	 * @throws URISyntaxException - URI error
	 * @throws Exception - Unknown exception
	 * @throws DirectoryCompressionException - compression failing
	 */
	@Test
	public void testDirectoryZipping () throws URISyntaxException, DirectoryCompressionException, Exception {
		@Nullable URL url = getClass ().getResource ("/" + directoryTest);
		Store store = new StoreMock ("mystore");
		
		if (url != null) {
			File dir = new File (url.toURI());
			store.store (dir);
		}
		else {
			assertTrue ("directory for testing exists", false);
		}
	}
	
	/**
	 * Test unreachable path exception.
	 * @throws UnreachablePathException
	 * @throws DirectoryCompressionException
	 * @throws Exception
	 */
	@Test (expected = UnreachablePathException.class)  
	public void testUnreachablePath () throws UnreachablePathException, DirectoryCompressionException, Exception {
		Store store = new StoreMock ("mystore");
		
		store.storeFromPath ("/" + store.salt ()); 
	}
	
}
