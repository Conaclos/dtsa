note
	description: "REquest for storing a file or a directory in a cloud storage facility."
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	DTSA_STORE_REQUEST

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

	make (a_path: like path)
			-- Create a store request with `a_path' as `path'.
		do
			path := a_path
		ensure
			path_set: path = a_path
			has_not_response: not has_response
		end

feature -- Access

	response: detachable DTSA_STORE_RESPONSE
			-- <Precursor>

	path: READABLE_STRING_GENERAL
			-- Path of the file or directory to store.

	partial_twin: like Current
			-- <Precursor>
		do
			create Result.make (path)
		end

feature -- Status report

	is_partial_equal (a_other: DTSA_REQUEST): BOOLEAN
			-- <Precursor>
		do
			Result := attached {like Current} a_other as l_other and then
				(same_type (l_other) and path ~ l_other.path)
		end

feature -- Processing

	acept (a_visitor: DTSA_REQUEST_VISITOR)
			-- <Precursor>
		do
			a_visitor.visit_store (Current)
		ensure then
			responded: has_response
		end

end
