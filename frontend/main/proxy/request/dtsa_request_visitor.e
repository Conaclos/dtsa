note
	description: "[
			Root ancestor of all visitors of request.
		]"
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

deferred class
	DTSA_REQUEST_VISITOR

feature -- Status

	is_reactive: BOOLEAN
			-- Does current processor give answer for requests?
		deferred end

feature -- Visit

	visit_store (a_visited: DTSA_STORE_REQUEST)
			-- Visit `a_visited'.
		note
			design_pattern: "visitor"
		deferred
		ensure
			has_response: is_reactive implies
				(a_visited.has_response xor a_visited.has_exception)
		end

	visit_project_compilation (a_visited: DTSA_PROJECT_COMPILATION_REQUEST)
			-- Visit `a_visited'.
		note
			design_pattern: "visitor"
		deferred
		ensure
			has_response: is_reactive implies
				(a_visited.has_response xor a_visited.has_exception)
		end

	visit_project_testing (a_visited: DTSA_PROJECT_TESTING_REQUEST)
			-- Visit `a_visited'.
		note
			design_pattern: "visitor"
		deferred
		ensure
			has_response: is_reactive implies
				(a_visited.has_response xor a_visited.has_exception)
		end

	visit_distributed_project_testing (a_visited: DTSA_DISTRIBUTED_PROJECT_TESTING_REQUEST)
			-- Visit `a_visited'.
		note
			design_pattern: "visitor"
		deferred
		ensure
			has_response: is_reactive implies
				(a_visited.has_response xor a_visited.has_exception)
		end

	visit_echo (a_visited: DTSA_ECHO_REQUEST)
			-- Visit `a_visited'.
		note
			design_pattern: "visitor"
		deferred
		ensure
			has_response: is_reactive implies
				(a_visited.has_response xor a_visited.has_exception)
		end

	visit_retrieve (a_visited: DTSA_RETRIEVE_REQUEST)
			-- Visit `a_visited'.
		note
			design_pattern: "visitor"
		deferred
		ensure
			has_response: is_reactive implies
				(a_visited.has_response xor a_visited.has_exception)
		end

	visit_result_merging (a_visited: DTSA_RESULT_MERGING_REQUEST)
			-- Visit `a_visited'.
		note
			design_pattern: "visitor"
		deferred
		ensure
			has_response: is_reactive implies
				(a_visited.has_response xor a_visited.has_exception)
		end

end
