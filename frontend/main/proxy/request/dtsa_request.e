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
			-- if request has no answer or if request has `exception'.

	exception: detachable DTSA_EXCEPTION_RESPONSE
			-- Exception of this current request.
			-- Void if request is not already processed or
			-- if request has no answer or if request has `response'.

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

	has_exception: BOOLEAN
			-- Has an exception?
		do
			Result := exception /= Void
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

	acept (a_visitor: DTSA_REQUEST_VISITOR)
			-- Visit current request with `a_visitor'.
		note
			design_pattern: "visitor"
		require
			unanswered: not has_response and not has_exception
		deferred
		ensure
			reactive_implication: a_visitor.is_reactive implies (has_response or has_exception)
		end

feature {NONE} -- Change

	set_response (a_response: attached like response)
			-- Set `response' with `a_response'.
		require
			unspecified_response: not has_response
		do
			response := a_response
		ensure
			response_set: response = a_response
		end

	set_exception (a_exception: attached like exception)
			-- Set `response' with `a_response'.
		require
			unspecified_exception: not has_exception
		do
			exception := a_exception
		ensure
			exception_set: exception = a_exception
		end

invariant
	has_response_definition: has_response = (response /= Void)
	has_exception_definition: has_exception = (exception /= Void)

end
