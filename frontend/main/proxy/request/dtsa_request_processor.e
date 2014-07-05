note
	description: "[
			Root ancestor of all visitors of request.
		]"
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

deferred class
	DTSA_REQUEST_PROCESSOR

feature -- Access

	is_reactive: BOOLEAN
			-- Does current processor give answer for requests?
		deferred end

feature -- Processing

	process_store (a_visited: DTSA_STORE_REQUEST)
			-- Visit `a_visited'.
		note
			design_pattern: "visitor"
		deferred
		ensure
			has_response: a_visited.has_response
		end

end
