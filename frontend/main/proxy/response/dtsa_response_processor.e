note
	description: "[
			Root ancestor of all visitors of response.
		]"
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

deferred class
	DTSA_RESPONSE_PROCESSOR

feature -- Processing

	process_store (a_visited: DTSA_STORE_RESPONSE)
			-- Visit `a_visited'.
		note
			design_pattern: "visitor"
		deferred end

end
