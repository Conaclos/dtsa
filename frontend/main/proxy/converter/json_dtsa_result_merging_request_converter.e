note
	description: "Summary description for {JSON_RESULT_MERGING_REQUEST_CONVERTER}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	JSON_DTSA_RESULT_MERGING_REQUEST_CONVERTER

inherit
	JSON_CONVERTER

feature -- Conversion

	from_json (j: like to_json): detachable DTSA_RESULT_MERGING_REQUEST
			-- Convert from JSON value.
			-- Returns Void if unable to convert
		local
			l_uris: ARRAYED_LIST [READABLE_STRING_GENERAL]
		do
			if
				attached {READABLE_STRING_GENERAL} json.object (j.item (Path_key), Void) as l_path and
				attached {LIST [detachable ANY]} json.object (j.item (Uris_key), Void) as l_candidates
			then
				create l_uris.make (l_candidates.count)
				across l_candidates as ic loop
					if attached {READABLE_STRING_GENERAL} ic.item as l_item then
						l_uris.extend (l_item)
					end
				end
				create Result.make (l_path, l_uris)
			end
		end

	to_json (o: attached like from_json): JSON_OBJECT
			-- Convert to JSON value.
		do
			create Result
			Result.put (json.value (o.uris), Uris_key)
			Result.put (json.value (o.path), Path_key)
		end

feature {NONE} -- Implementation

	Path_key: JSON_STRING
			-- Label key.
		once
			Result := "path"
		end

	Uris_key: JSON_STRING
			-- Label key.
		once
			Result := "uris"
		end

end
