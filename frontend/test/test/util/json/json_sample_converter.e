note
	description: "Summary description for {JSON_SAMPLE_CONVERTER}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	JSON_SAMPLE_CONVERTER

inherit

	JSON_CONVERTER

feature -- Conversion

	from_json (j: like to_json): detachable SAMPLE
			-- <Precursor>
		do
			if
				attached {READABLE_STRING_GENERAL} json.object (j.item (Str_key), Void) as l_str and
				attached {INTEGER_8} json.object (j.item (Int_key), Void) as n
			then
				create Result.make (l_str, n)
			end
		end

	to_json (o: attached like from_json): JSON_OBJECT
			-- <Precursor>
		do
			create Result
			Result.put (json.value (o.str), Str_key)
			Result.put (json.value (o.int), Int_key)
		end

feature {NONE} -- Implementation

	Str_key: JSON_STRING
			-- Label key.
		once
			create Result.make_json ("str")
		end

	Int_key: JSON_STRING
			-- Label key.
		once
			create Result.make_json ("int")
		end

end
