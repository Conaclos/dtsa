package dtsa.util.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import dtsa.util.annotation.Nullable;
import dtsa.util.communication.converter.Object2String;
import dtsa.util.communication.converter.String2Object;

/**
 * @description Mapping between java types and labels with conversion facility.
 * It is not thread-safe, use then `clone'.
 * @author Victorien Elvinger
 * @date 2014/06/27
 *
 * @param <G> - NonNull based type for the manager.
 */
public class JsonLabeling <G> 
	implements String2Object <G>, Object2String <G> {

// Creation
	/**
	 * Create a default manager.
	 */
	public JsonLabeling () {
		this (new HashMap <> ());
	}
	
	/**
	 * Create a manager with `aMapping' as `mapping'.
	 * @param aMapping
	 */
	protected JsonLabeling (Map <String, Class <? extends G>> aMapping) {
		mapping = aMapping;
		jsonMapper = new ObjectMapper ();
	}
	
// Access
	/**
	 * Wrap `aObject' with a LabeledValue and then
	 * get its JSON representation.
	 * @param aObject - the wrapped object
	 */
	@Override
	public String value (G aObject) {
		assert isConvertible (aObject): "require: aObject's class is labeled.";
		
		@Nullable LabeledValue wrap;
		ByteArrayOutputStream out;
		@Nullable String result;
		
		result = null;
		out = new ByteArrayOutputStream ();
		try {
			jsonMapper.writeValue (out, wrapped (aObject));
			result = out.toString ();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assert result != null: "check: result not null.";
		
		System.out.println (result);
		
		return result;
	}
	
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
				e.printStackTrace ();
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
	@Override
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
	
	@Override
	public JsonLabeling <G> clone () {
		return new JsonLabeling <> (mapping);
	}
	
// Status
	/**
	 * @param aLabel
	 * @return IS `aLabel' already in use?
	 */
	public boolean has (String aLabel) {
		return mapping.containsKey (aLabel);
	}

	@Override
	public boolean isConvertible (G aObject) {
		return mapping.containsValue ((Class <? extends G>) aObject.getClass ());
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
	
// Implementation
	/**
	 * Mapping between a label and a java class.
	 */
	protected Map <String, Class <? extends G>> mapping;
	
	/**
	 * Json Mapper.
	 */
	protected ObjectMapper jsonMapper;
	
	/**
	 * @param aObject
	 * @return Wrapped version of `aObject' or Null if none label for type of `aObject'.
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
