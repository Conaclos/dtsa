note
	description: "[
			Root ancestor of all visitors of response.
		]"
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

deferred class
	DTSA_RESPONSE_VISITOR

feature -- Processing

	visit_store (a_visited: DTSA_STORE_RESPONSE)
			-- Visit `a_visited'.
		note
			design_pattern: "visitor"
		deferred end

	visit_exception (a_visited: DTSA_EXCEPTION_RESPONSE)
			-- Visit `a_visited'.
		note
			design_pattern: "visitor"
		deferred end

	visit_echo (a_visited: DTSA_ECHO_RESPONSE)
			-- Visit `a_visited'.
		deferred end

end
