note
	description: "Summary description for {DTSA_RETRIEVE_RESPONSE}."
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	DTSA_RETRIEVE_RESPONSE

inherit
	DTSA_RESPONSE

create
	make

feature {NONE} -- Creation

	make (a_uri: like uri)
			-- Create with `a_uri' as `uri'.
		do
			uri := a_uri
		ensure
			uri_set: uri = a_uri
		end

feature -- Acceess

	uri: READABLE_STRING_GENERAL
			-- Stored entity URI.

feature -- Processing

	acept (a_visitor: DTSA_RESPONSE_VISITOR)
			-- <Precursor>
		do
			a_visitor.visit_retrieve (Current)
		end

end
