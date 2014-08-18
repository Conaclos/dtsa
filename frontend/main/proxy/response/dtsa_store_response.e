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
	make

feature {NONE} -- Creation

	make (a_uri: like uri)
			-- Create a store response with `a_uri' as `uri'.
		do
			uri := a_uri
		ensure
			uri_set: uri = a_uri
		end

feature -- Access

	uri: READABLE_STRING_8
			-- Stored entity URI.

feature -- Processing

	acept (a_visitor: DTSA_RESPONSE_VISITOR)
			-- <Precursor>
		do
			a_visitor.visit_store (Current)
		end

end
