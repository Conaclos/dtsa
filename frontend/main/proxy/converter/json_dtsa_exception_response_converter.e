note
	description: "Summary description for {JSON_DTSA_EXCEPTION_RESPONSE_CONVERTER}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	JSON_DTSA_EXCEPTION_RESPONSE_CONVERTER

inherit
	JSON_CONVERTER

feature -- Conversion

	from_json (j: like to_json): detachable DTSA_EXCEPTION_RESPONSE
			-- Convert from JSON value.
			-- Returns Void if unable to convert
		do
			if attached {READABLE_STRING_GENERAL} json.object (j.item (Message_key), Void) as l_message then
				create Result.make (l_message)
			end
		end

	to_json (o: attached like from_json): JSON_OBJECT
			-- Convert to JSON value.
		do
			create Result
			Result.put (json.value (o.message), Message_key)
		end

feature {NONE} -- Implementation

	Message_key: JSON_STRING
			-- Label key.
		once
			Result := "message"
		end

end
