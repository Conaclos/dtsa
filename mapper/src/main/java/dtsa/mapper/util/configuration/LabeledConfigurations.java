package dtsa.mapper.util.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.net.URL;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import dtsa.mapper.util.annotation.Nullable;

/**
 * @description Labeled configuration stored into `resources' directory.
 * @author Victorien Elvinger
 * @date 2014/07/1
 *
 */
public class LabeledConfigurations {
	
// Creation
	/**
	 * Create an empty configuration instance with `aRelativePath' as `configurationDirectory'.
	 * 
	 * @param aRelativePath
	 *            - Path of the configuration directory relative to the resources directory.
	 */
	public LabeledConfigurations (String aRelativePath) {
		jsonMapper = new ObjectMapper ();
		configurationCache = new HashMap <> ();
		configurationDirectory = "/" + aRelativePath;
		
		assert configurationDirectory.equals ("/" + aRelativePath): "ensure: `configurationDirectory' set with /`aRelativePath'";
	}
	
// Access
	/**
	 * @param aLabel
	 * @return Configuration attached with `aLabel' if existing.
	 * @throws ConfigurationParsingException
	 *             - triggered when a parsing error of the configuration file occurs.
	 */
	public synchronized @Nullable Map <String, String> labeled (String aLabel)
			throws ConfigurationParsingException {
		@Nullable Map <String, String> result;
		
		result = configurationCache.get (aLabel);
		if (result == null) {
			final @Nullable URL url = getClass ().getResource (
					configurationDirectory + aLabel + ".json");
			if (url != null) {
				try {
					result = jsonMapper.readValue (url,
							new TypeReference <Map <String, String>> () {});
				}
				catch (IOException e) {
					throw new ConfigurationParsingException (aLabel);
				}
				configurationCache.put (aLabel, result);
			}
		}
		
		assert result == null || configurationCache.get (aLabel) != null: "ensure: configuration is cached.";
		return result;
	}
	
// Change
	/**
	 * Reload configuration attached with `aLabel' for the next retrieving.
	 * 
	 * @param aLabel
	 */
	public synchronized void reset (String aLabel) {
		configurationCache.remove (aLabel);
	}
	
// Implementation
	/**
	 * JSON Mapper.
	 */
	protected final ObjectMapper jsonMapper;
	
	/**
	 * Path of the configuration directory relative to the resources directory.
	 */
	protected final String configurationDirectory;
	
	/**
	 * Cache of configurations.
	 */
	protected final Map <String, Map <String, String>> configurationCache;
	
}
