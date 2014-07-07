package dtsa.mapper.util.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import dtsa.mapper.util.annotation.Nullable;

/**
 * @description Mapping between java types and labels with conversion facility.
 * @author Victorien Elvinger
 * @date 2014/06/27
 *
 * @param <G> - based type for the manager.
 */
public class TypeJsonManager <G> {
// Creation
	/**
	 * Create a default manager.
	 */
	public TypeJsonManager () {
		mapping = new HashMap <> ();
		jsonMapper = new ObjectMapper();
	}
	
// Access
	/**
	 * @param aValue - json plain string
	 * @param aLabel - USe type attached to `aLabel' for conversion.
	 * @return java instance represented by `aValue' conforming to `aLabel'.
	 * @throws UnrecordedLabelException 
	 * @throws InvalidJSONException 
	 */
	public G object (String aValue, String aLabel) throws UnrecordedLabelException, InvalidJSONException {
		@Nullable Class<? extends G> type;
		G result;
		
		type = mapping.get (aLabel);
		if (type != null) {
			try {
				result = jsonMapper.readValue (aValue, type);
			}
			catch (IOException e) {
				throw new InvalidJSONException (aValue, aLabel);
			}
		}
		else {
			throw new UnrecordedLabelException (aLabel);
		}
		
		return result;
	}
	
	/**
	 * Java instance from a typed value (generated with `value')
	 * @param aValue
	 * @return java instance represented by `aValue'.
	 * @throws InvalidJSONException 
	 * @throws UnrecordedLabelException 
	 */
	public G instance (String aValue) throws InvalidJSONException, UnrecordedLabelException {
		@Nullable String label, value;
		LabeledValue wrap;
		G result;
		
		try {
			wrap = jsonMapper.readValue (aValue, LabeledValue.class);
			label = wrap.getLabel ();
			value = wrap.getValue ();
			if (label != null && value != null) {
				result = object (value, label);
			}
			else {
				throw new InvalidJSONException (aValue, LabeledValue.class.getName ());
			}
		}
		catch (IOException e) {
			throw new InvalidJSONException (aValue, LabeledValue.class.getName ());
		}
		
		return result;
	}
	
// Status
	/**
	 * @param aLabel
	 * @return IS `aLabel' already in use?
	 */
	public boolean has (String aLabel) {
		return mapping.containsKey (aLabel);
	}
	
	/**
	 * 
	 * @param aType
	 * @return Is `aType' attached at least one label?
	 */
	public boolean isLabeled (Class <? extends G> aType) {
		return mapping.containsValue (aType);
	}
	
// Change
	/**
	 * Attached `aType' with a new label `aLabel'.
	 * @param aLabel - New label
	 * @param aType - java type
	 */
	public void add (String aLabel, Class <? extends G> aType) {
		assert ! has (aLabel): "require: `aLabel' is not already use.";
		assert ! isLabeled (aType): "require: `aType' is not already labeled.";
		assert ! aType.isInterface (): "require: Is not an interface.";
		
		mapping.put (aLabel, aType);
		
		assert has (aLabel): "require: `aLabel' has an attached class.";
		assert isLabeled (aType): "require: `aType' has a label.";
	}
	
	/**
	 * Wrap `aObject' with a LabeledValue and then
	 * write its JSON representation in `aOut' if the parsing step is not failing.
	 * @param aOut
	 * @param aObject - the wrapped object
	 */
	public void writeValue (PrintWriter aOut, G aObject) {
		assert isLabeled ((Class <? extends G>) aObject.getClass ()): "require: aObject'sclass is labeled.";
		
		@Nullable LabeledValue wrap;
		
		try {
			wrap = wrapped (aObject);
			if (wrap != null) {
				jsonMapper.writeValue (aOut, wrapped (aObject));
			}
		}
		catch (IOException e1) {
			// TODO Logging
			e1.printStackTrace();
		}
	}
	
// Implementation
	/**
	 * Mapping between a label and a java class.
	 */
	protected Map <String, Class <? extends G>> mapping;
	
	/**
	 * Json Mapper.
	 */
	protected final ObjectMapper jsonMapper;
	
	/**
	 * @param aObject
	 * @return Wrapped version of `aObject'
	 */
	protected @Nullable LabeledValue wrapped (G aObject) throws IOException {
		Class <? extends G> type;
		@Nullable LabeledValue result;
		Optional <Map.Entry <String, Class <? extends G>>> filtered;
		
		result = null;
		type = (Class <? extends G>) aObject.getClass ();
		filtered = mapping.entrySet ().stream ().filter ((Map.Entry <String, Class <? extends G>> e) -> e.getValue ().equals (type)).findFirst ();
		
		if (filtered.isPresent ()) {
			ByteArrayOutputStream out = new ByteArrayOutputStream ();
			try {
				jsonMapper.writeValue (out, aObject);
				result = new LabeledValue (filtered.get ().getKey (), out.toString ());
			}
			catch (IOException e) {
				throw e;
			}
		}
		
		return result;
	}
	
}
