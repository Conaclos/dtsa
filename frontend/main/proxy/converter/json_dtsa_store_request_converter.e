note
	description: "JSON converter for DTSA_STORE_REQUEST"
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	JSON_DTSA_STORE_REQUEST_CONVERTER

inherit
	
	JSON_CONVERTER

feature -- Conversion

	from_json (j: like to_json): detachable DTSA_STORE_REQUEST
			-- <Precursor>
		do
			if
				attached {READABLE_STRING_GENERAL} json.object (j.item (Path_key), Void) as l_path
			then
				create Result.make (l_path)
			end
		end

	to_json (o: attached like from_json): JSON_OBJECT
			-- <Precursor>
		do
			create Result
			Result.put (json.value (o.path), Path_key)
		end

feature {NONE} -- Implementation

	Path_key: JSON_STRING
			-- Store request path key.
		once
			create Result.make_json ("path")
		end

end
