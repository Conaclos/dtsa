note
	description: "Summary description for {JSON_DTSA_DISTRIBUTED_PROJECT_TESTING_RESPONSE_CONVERTER}."
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	JSON_DTSA_DISTRIBUTED_PROJECT_TESTING_RESPONSE_CONVERTER

inherit
	JSON_CONVERTER

feature -- Conversion

	from_json (j: like to_json): detachable DTSA_DISTRIBUTED_PROJECT_TESTING_RESPONSE
			-- Convert from JSON value.
			-- Returns Void if unable to convert
		local
			l_uris: ARRAYED_LIST [READABLE_STRING_GENERAL]
		do
			if
				attached {LIST [detachable ANY]} json.object (j.item (Uris_key), Void) as l_candidates
			then
				create l_uris.make (l_candidates.count)
				across l_candidates as ic loop
					if attached {READABLE_STRING_GENERAL} ic.item as l_item then
						l_uris.extend (l_item)
					end
				end
				create Result.make (l_uris)
			end
		end

	to_json (o: attached like from_json): JSON_OBJECT
			-- Convert to JSON value.
		do
			create Result
			Result.put (json.value (o.uris), Uris_key)
		end

feature {NONE} -- Implementation

	Uris_key: JSON_STRING
			-- Label key.
		once
			Result := "uris"
		end

end
