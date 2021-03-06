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

	visit_project_compilation (a_visited: DTSA_PROJECT_COMPILATION_RESPONSE)
			-- Visit `a_visited'.
		note
			design_pattern: "visitor"
		deferred end

	visit_project_testing (a_visited: DTSA_PROJECT_TESTING_RESPONSE)
			-- Visit `a_visited'.
		note
			design_pattern: "visitor"
		deferred end

	visit_distributed_project_testing (a_visited: DTSA_DISTRIBUTED_PROJECT_TESTING_RESPONSE)
			-- Visit `a_visited'.
		note
			design_pattern: "visitor"
		deferred end

	visit_echo (a_visited: DTSA_ECHO_RESPONSE)
			-- Visit `a_visited'.
		deferred end

	visit_retrieve (a_visited: DTSA_RETRIEVE_RESPONSE)
			-- Visit `a_visited'.
		deferred end

end
