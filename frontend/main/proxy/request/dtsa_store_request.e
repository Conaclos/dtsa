note
	description: "REquest for storing a file or a directory in a cloud storage facility."
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	DTSA_STORE_REQUEST

inherit
	DTSA_REQUEST

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

	path: PATH
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

feature -- Change

	set_response (a_response: attached like response)
			-- Set `response' with `a_response'.
		require
			unspecified_response: response = Void
		do
			response := a_response
		ensure
			response_set: response = a_response
		end

feature -- Processing

	process (a_visitor: DTSA_REQUEST_PROCESSOR)
			-- <Precursor>
		do
			a_visitor.process_store (Current)
		ensure then
			responded: has_response
		end

end
