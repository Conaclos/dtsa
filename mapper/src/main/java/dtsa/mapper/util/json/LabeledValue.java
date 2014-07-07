package dtsa.mapper.util.json;

import dtsa.mapper.util.annotation.Nullable;

/**
 * @description Labeled string
 * @author Victorien Elvinger
 * @date 2014/06/27
 */
public class LabeledValue {
// Creation
	/**
	 * CReate an empty label and an empty value.
	 */
	public LabeledValue () {
		label = null;
		value = null;
		
		assert getLabel () == null: "ensure: `getLabel' is null.";
		assert getValue () == null: "ensure: `getValue' is null.";
	}
	
	/**
	 * Create with `aLabel' as `getLabel' and `aValue' as `getValue'.
	 * @param aLabel
	 * @param aValue
	 */
	public LabeledValue (String aLabel, @Nullable String aValue) {
		label = aLabel;
		value = aValue;
		
		assert getLabel () == aLabel: "ensure: `getLabel' set with `aLabel'.";
		assert getValue () == aValue: "ensure: `getValue' set with `aValue'.";
	}
	
// Access
	/**
	 * @return Label.
	 */
	public String getLabel () {
		return label;
	}
	
	/**
	 * @return Value attached with `label'.
	 */
	public @Nullable String getValue () {
		return value;
	}
	
// Change
	/**
	 * Set `getLabel' with `aLabel'.
	 * @param aLabel
	 */
	public void setType (String aLabel) {
		label = aLabel;
		
		assert getLabel () == aLabel: "ensure: `getLabel' set with `aLabel'.";
	}
	
	/**
	 * Set `getValue' with `aValue'.
	 * @param aValue
	 */
	public void setValue (String aValue) {
		value = aValue;
		
		assert getValue () == aValue: "ensure: `getValue' set with `aValue'.";
	}
	
// Implementation
	/**
	 * Label.
	 */
	protected @Nullable String label;
	
	/**
	 * Value.
	 */
	protected @Nullable String value;
	
}
