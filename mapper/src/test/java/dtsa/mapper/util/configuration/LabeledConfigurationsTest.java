package dtsa.mapper.util.configuration;

import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import dtsa.mapper.util.annotation.Nullable;

/**
 * 
 * @description Test labeled configuration
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class LabeledConfigurationsTest {

// Test
	/**
	 * Test:
	 * - LabeledConfigurations.labeled
	 * @throws ConfigurationParsingException 
	 */
	@Test
	public void testConfigurationRetrieving () throws ConfigurationParsingException {
		LabeledConfigurations config = new LabeledConfigurations ("configurations/");
		
		@Nullable Map <String, String> props = config.labeled ("test");
		if (props != null) {
			assertTrue ("property `item' retrieved", props.get ("item1").equals ("1"));
		}
		else {
			assertTrue ("Configuration exists", false);
		}
	}
	
}
