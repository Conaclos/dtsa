note
	description: "Summary description for {DTSA_RESULT_MERGING_REQUEST}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	DTSA_RESULT_MERGING_REQUEST

inherit

	DTSA_REQUEST
		export
			{ANY} set_response, set_exception
		redefine
			response
		end

create
	make

feature {NONE} -- Creation

	make (a_path: like path; a_uris: like uris)
			-- Create a store request with `a_path' as `path'.
		do
			path := a_path
			uris := a_uris
		ensure
			path_set: path = a_path
			uris_set: uris = a_uris
			has_not_response: not has_response
		end

feature -- Access

	response: detachable DTSA_RETRIEVE_RESPONSE
			-- <Precursor>

	path: READABLE_STRING_GENERAL
			-- Path where the entity should be stored.

	uris: ARRAYED_LIST [READABLE_STRING_GENERAL]
			-- Results locations.

	partial_twin: like Current
			-- <Precursor>
		do
			create Result.make (path, uris)
		end

feature -- Status report

	is_partial_equal (a_other: DTSA_REQUEST): BOOLEAN
			-- <Precursor>
		do
			Result := attached {like Current} a_other as l_other and then
				(same_type (l_other) and path ~ l_other.path and uris ~ l_other.uris)
		end

feature -- Processing

	acept (a_visitor: DTSA_REQUEST_VISITOR)
			-- <Precursor>
		do
			a_visitor.visit_result_merging (Current)
		end

end
