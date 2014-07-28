note
	description: "Converter for LABELED_VALUE."
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	JSON_LABELED_VALUE_CONVERTER

inherit
	
	JSON_CONVERTER

feature -- Conversion

	from_json (j: like to_json): detachable LABELED_VALUE
			-- <Precursor>
		do
			if
				attached {READABLE_STRING_GENERAL} json.object (j.item (Label_key), Void) as l_label and
				attached {READABLE_STRING_GENERAL} json.object (j.item (Value_key), Void) as l_value
			then
				create Result.make (l_label.as_string_8, l_value.as_string_8)
			end
		end

	to_json (o: attached like from_json): JSON_OBJECT
			-- <Precursor>
		do
			create Result
			Result.put (json.value (o.label), Label_key)
			Result.put (json.value (o.value), Value_key)
		end

feature {NONE} -- Implementation

	Label_key: JSON_STRING
			-- Label key.
		once
			create Result.make_json ("label")
		end

	Value_key: JSON_STRING
			-- Label key.
		once
			create Result.make_json ("value")
		end

end
