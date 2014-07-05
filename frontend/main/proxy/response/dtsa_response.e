note
	description: "[
			Root ancestor of all server response.
			Response are simple objects for reception.
		]"
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

deferred class
	DTSA_RESPONSE

feature -- Access

	exception: detachable EXCEPTION
			-- Exception which occurred during the request processing
			-- VOid if none.

feature -- Status report

	is_exception: BOOLEAN
			-- Does response contain an exception?
			-- True means current response failed.
		do
			Result := exception = Void
		end

feature -- Processing

	process (a_visitor: DTSA_RESPONSE_PROCESSOR)
			-- Visit current response with `a_visitor'.
		note
			design_pattern: "visitor"
		deferred end

invariant
	is_exception_definition: is_exception = (exception = Void)
end
