package dtsa.util.configuration;

import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dtsa.util.annotation.Nullable;

/**
 * 
 * @description Typed resources.
 * @author Victorien ELvinger
 * @date 2014
 *
 */
public class TypedResources {
// Creation
	/**
	 * Use the 'resources' directory as directory for configurations.
	 */
	public TypedResources () {
		this ("/");
		
		assert relativePath == "/": "ensure: `relativePath' set with resources directory.";
	}
	
	/**
	 * Use `aRelativePath' as directory for configurations.
	 * @param aRelativePath - path relative to 'resources' directory. Must start with  `/' and finish with "/".
	 */
	public TypedResources (String aRelativePath) {
		assert aRelativePath.charAt (0) == '/': "require: first character of `aRelativePath' is '/'.";
		assert aRelativePath.charAt (aRelativePath.length () - 1) == '/': "require: last character of `aRelativePath' is '/'.";
		
		relativePath = aRelativePath;
		jsonMapper = new ObjectMapper ();
		
		assert relativePath () == aRelativePath: "ensure: `relativePath' set with `aRelativePath'.";
	}
	
// Access
	/**
	 * 
	 * @return Path relative to 'resources' directory.
	 */
	public String relativePath () {
		return relativePath;
	}
	
	/**
	 * 
	 * @param aLabel - Resource name.
	 * @param aType - Type expected.
	 * @return Resource `aLabel' typed as `aType'.
	 * @throws UnparsableException
	 * @throws UnmatchableTypeException
	 */
	public <T> T labeled (String aLabel, Class <T> aType) throws UnparsableException, UnmatchableTypeException {
		@Nullable URL url = getClass ().getResource (relativePath + aLabel);
		T result;
		
		if (url != null) {
			try {
				result = jsonMapper.readValue (url, aType);
			}
			catch (JsonMappingException e) {
				throw new UnmatchableTypeException (relativePath + aLabel, aType);
			}
			catch (IOException e) {
				throw new UnparsableException (relativePath + aLabel);
			}
		}
		else {
			throw new UnparsableException (relativePath + aLabel);
		}
		
		return result;
	}
	
// Implementation
	/**
	 * Path relative to 'resources' directory.
	 */
	protected String relativePath;
	
	/**
	 * JSON Mapper.
	 */
	protected ObjectMapper jsonMapper;
	
}
