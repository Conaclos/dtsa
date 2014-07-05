note
	description: "[
			Root ancestor of all resquests to the server.
			Requests are simple objects for transmission.
		]"
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

deferred class
	DTSA_REQUEST

feature -- Access

	response: detachable DTSA_RESPONSE
			-- Answer of this current request.
			-- Void if request is not already processed or
			-- if request has no answer.
		deferred end

	partial_twin: like Current
			-- New object partially equal to `Current'.
			--
			-- Forget `response'.
		deferred
		ensure
			partially_equal: is_partial_equal (Result)
		end

feature -- Status report

	has_response: BOOLEAN
			-- Has an answer?
		do
			Result := response /= Void
		end

	is_partial_equal (a_other: DTSA_REQUEST): BOOLEAN
			-- Is `a_other' considered
			-- partially equal to current object?
			--
			-- Forget `response'.
		deferred
		ensure
			same_type: Result implies same_type (a_other)
			different_type: not same_type (a_other) implies not Result
			symetric_equal: Result = a_other.is_partial_equal (Current)
		end

feature -- Processing

	process (a_visitor: DTSA_REQUEST_PROCESSOR)
			-- Visit current request with `a_visitor'.
		note
			design_pattern: "visitor"
		deferred
		ensure
			response_for_reactive_processor: a_visitor.is_reactive = has_response
		end

invariant
	has_response_definition: has_response = (response /= Void)
end
