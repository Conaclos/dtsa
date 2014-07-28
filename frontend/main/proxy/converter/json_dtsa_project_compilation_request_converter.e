note
	description: "Summary description for {JSON_DTSA_PROJECT_COMPILATION_CONVERTER}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	JSON_DTSA_PROJECT_COMPILATION_REQUEST_CONVERTER

inherit

	JSON_CONVERTER

feature -- Conversion

	from_json (j: like to_json): detachable DTSA_PROJECT_COMPILATION_REQUEST
			-- Convert from JSON value.
			-- Returns Void if unable to convert
		do
			if
				attached {READABLE_STRING_GENERAL} json.object (j.item (Uri_key), Void) as l_uri and
				attached {READABLE_STRING_GENERAL} json.object (j.item (Project_key), Void) as l_project and
				attached {READABLE_STRING_GENERAL} json.object (j.item (Configuration_key), Void) as l_config and
				attached {READABLE_STRING_GENERAL} json.object (j.item (Target_key), Void) as l_target
			then
				create Result.make (l_uri.as_string_8, l_project.as_string_8, l_config.as_string_8, l_target.as_string_8)
			end
		end

	to_json (o: attached like from_json): JSON_OBJECT
			-- Convert to JSON value.
		do
			create Result
			Result.put (json.value (o.uri), Uri_key)
			Result.put (json.value (o.project), Project_key)
			Result.put (json.value (o.configuration), Configuration_key)
			Result.put (json.value (o.target), Target_key)
		end

feature {NONE} -- Implementation

	Uri_key: JSON_STRING
			-- Label key.
		once
			Result := "uri"
		end

	Project_key: JSON_STRING
			-- Label key.
		once
			Result := "project"
		end

	Configuration_key: JSON_STRING
			-- Label key.
		once
			Result := "configuration"
		end

	Target_key: JSON_STRING
			-- Label key.
		once
			Result := "target"
		end

end
