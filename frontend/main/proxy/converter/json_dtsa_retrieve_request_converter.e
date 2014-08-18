note
	description: "Summary description for {JSON_DTSA_RETRIEVE_REQUEST_CONVERTER}."
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	JSON_DTSA_RETRIEVE_REQUEST_CONVERTER

inherit

	JSON_CONVERTER

feature -- Conversion

	from_json (j: like to_json): detachable DTSA_RETRIEVE_REQUEST
			-- <Precursor>
		do
			if
				attached {READABLE_STRING_GENERAL} json.object (j.item (Path_key), Void) as l_path and
				attached {READABLE_STRING_GENERAL} json.object (j.item (Source_key), Void) as l_source
			then
				create Result.make (l_path, l_source)
			end
		end

	to_json (o: attached like from_json): JSON_OBJECT
			-- <Precursor>
		do
			create Result
			Result.put (json.value (o.path), Path_key)
			Result.put (json.value (o.source), Source_key)
		end

feature {NONE} -- Implementation

	Path_key: JSON_STRING
			-- Store request path key.
		once
			create Result.make_json ("path")
		end

	Source_key: JSON_STRING
			-- Store request path key.
		once
			create Result.make_json ("source")
		end

end
