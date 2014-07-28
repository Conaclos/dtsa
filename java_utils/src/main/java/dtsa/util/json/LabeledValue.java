package dtsa.util.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

import dtsa.util.annotation.Nullable;

/**
 * @description Value attached with a label.
 * @author Victorien Elvinger
 * @date 2014/06/27
 */
public class LabeledValue {
// Creation
	/**
	 * Create with `aLabel' as `getLabel' and `aValue' as `getValue'.
	 * @param aLabel
	 * @param aValue
	 */
	@Requires ({
		"aLabel != null",
		"aValue != null"
	})
	@Ensures ({
		"getLabel () == aLabel",
		"getValue () == aValue"
	})
	@JsonCreator
	public LabeledValue (@JsonProperty ("label") String aLabel, @JsonProperty ("value") String aValue) {
		label = aLabel;
		value = aValue;
		
		assert getLabel () == aLabel: "ensure: `getLabel' set with `aLabel'.";
		assert getValue () == aValue: "ensure: `getValue' set with `aValue'.";
	}
	
// Access
	/**
	 * @return Label attached to `getValue'.
	 */
	@Ensures ("result != null")
	public String getLabel () {
		return label;
	}
	
	/**
	 * @return Main content.
	 */
	@Ensures ("result != null")
	public @Nullable String getValue () {
		return value;
	}
	
// Implementation
	/**
	 * Label.
	 */
	protected String label;
	
	/**
	 * Value.
	 */
	protected String value;
	
}
