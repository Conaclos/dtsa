note
	description: "Summary description for {DTSA_RETRIEVE_REQUEST}."
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	DTSA_RETRIEVE_REQUEST

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

	make (a_path: like path; a_source: like source)
			-- Create a store request with `a_path' as `path'.
		do
			path := a_path
			source := a_source
		ensure
			path_set: path = a_path
			source_set: source = a_source
			has_not_response: not has_response
		end

feature -- Access

	response: detachable DTSA_RETRIEVE_RESPONSE
			-- <Precursor>

	path: READABLE_STRING_GENERAL
			-- Path where the entity should be stored.

	source: READABLE_STRING_GENERAL
			-- Entity source.

	partial_twin: like Current
			-- <Precursor>
		do
			create Result.make (path, source)
		end

feature -- Status report

	is_partial_equal (a_other: DTSA_REQUEST): BOOLEAN
			-- <Precursor>
		do
			Result := attached {like Current} a_other as l_other and then
				(same_type (l_other) and path ~ l_other.path and source ~ l_other.source)
		end

feature -- Processing

	acept (a_visitor: DTSA_REQUEST_VISITOR)
			-- <Precursor>
		do
			a_visitor.visit_retrieve (Current)
		ensure then
			responded: has_response
		end

end
