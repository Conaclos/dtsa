note
	description: "Summary description for {JSON_DTSA_STORE_RESPONSE}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	JSON_DTSA_STORE_RESPONSE_CONVERTER

inherit
	JSON_CONVERTER

feature -- Conversion

	from_json (j: like to_json): detachable DTSA_STORE_RESPONSE
			-- Convert from JSON value.
			-- Returns Void if unable to convert
		do
			if
				attached {READABLE_STRING_GENERAL} json.object (j.item (Uri_key), Void) as l_uri
			then
				create Result.make (l_uri.as_string_8)
			end
		end

	to_json (o: attached like from_json): JSON_OBJECT
			-- Convert to JSON value.
		do
			create Result
			Result.put (json.value (o.uri), Uri_key)
		end

feature {NONE} -- Implementation

	Uri_key: JSON_STRING
			-- Label key.
		once
			Result := "uri"
		end

end
