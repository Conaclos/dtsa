note
	description: "COnveretr for {DTSA_ECHO_REQUEST}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	JSON_DTSA_ECHO_REQUEST_CONVERTER

inherit
	JSON_CONVERTER

feature -- Conversion

	from_json (j: like to_json): detachable DTSA_ECHO_REQUEST
			-- Convert from JSON value.
			-- Returns Void if unable to convert
		do
			if
				attached {INTEGER_8} json.object (j.item (Id_key), Void) as l_id and
				attached json.object (j.item (Hop_key), Void) as l_candidate
			then
				if attached {INTEGER_8} l_candidate as l_hop then
					create Result.make_with_hop (l_id, l_hop.as_natural_8)
				elseif attached {INTEGER_16} l_candidate as l_hop then
					create Result.make_with_hop (l_id, l_hop.as_natural_8)
				end
			end
		end

	to_json (o: attached like from_json): JSON_OBJECT
			-- Convert to JSON value.
		do
			create Result
			Result.put (json.value (o.id), Id_key)
			Result.put (json.value (o.hop), Hop_key)
		end

feature {NONE} -- Implementation

	Id_key: JSON_STRING
			-- Label key.
		once
			Result := "id"
		end

	Hop_key: JSON_STRING
			-- Label key.
		once
			Result := "hop"
		end

end
