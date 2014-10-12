note
	description: "Summary description for {DTSA_PROJECT_TESTING_RESPONSE}."
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	DTSA_DISTRIBUTED_PROJECT_TESTING_RESPONSE

inherit

	DTSA_RESPONSE

create
	make_singleton,
	make

feature {NONE} -- Creation

	make_singleton (a_uri: READABLE_STRING_GENERAL)
		do
			create {ARRAYED_LIST [READABLE_STRING_GENERAL]} uris.make (1)
			uris.extend (a_uri)
		ensure
			uri_added: uris [1].same_string (a_uri)
		end

	make (a_uris: like uris)
			-- Create a store response with `a_uris' as `uris'.
		do
			uris := a_uris
		ensure
			a_uris_set: uris = a_uris
		end

feature -- Access

	uris: ARRAYED_LIST [READABLE_STRING_GENERAL]
			-- Results of the distributed testing session.

feature -- Processing

	acept (a_visitor: DTSA_RESPONSE_VISITOR)
			-- <Precursor>
		do
			a_visitor.visit_distributed_project_testing (Current)
		end

end
