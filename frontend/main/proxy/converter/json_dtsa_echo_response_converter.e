note
	description: "Summary description for {JSON_DTSA_ECHO_RESPONSE_CONVERTER}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	JSON_DTSA_ECHO_RESPONSE_CONVERTER

inherit
	JSON_CONVERTER

feature -- Conversion

	from_json (j: like to_json): detachable DTSA_ECHO_RESPONSE
			-- Convert from JSON value.
			-- Returns Void if unable to convert
		do
			if
				attached {INTEGER_8} json.object (j.item (Id_key), Void) as l_id
			then
				create Result.make (l_id)
			end
		end

	to_json (o: attached like from_json): JSON_OBJECT
			-- Convert to JSON value.
		do
			create Result
			Result.put (json.value (o.id), Id_key)
		end

feature {NONE} -- Implementation

	Id_key: JSON_STRING
			-- Label key.
		once
			Result := "id"
		end

end
