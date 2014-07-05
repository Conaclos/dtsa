note
	description: "Response for a store request."
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	DTSA_STORE_RESPONSE

inherit
	DTSA_RESPONSE

create
	make,
	make_exception

feature {NONE} -- Creation

	make (a_uri: attached like uri)
			-- Create a store response with `a_uri' as `uri'.
		do
			uri := a_uri
		ensure
			uri_set: uri = a_uri
		end

	make_exception (a_exception: attached like exception)
			-- Create a store response with  `a_exception' as `exception'.
		do
			exception := a_exception
		ensure
			exception_set: exception = a_exception
		end

feature -- Access

	uri: detachable READABLE_STRING_GENERAL
			-- Stored entity URI.
			-- Void if storing is failed.

feature -- Processing

	process (a_visitor: DTSA_RESPONSE_PROCESSOR)
			-- <Precursor>
		note
			design_pattern: "visitor"
		do
			a_visitor.process_store (Current)
		end

invariant
	exception_or_uri: (uri /= Void) xor (exception /= Void)
end
