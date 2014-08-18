note
	description: "Response denoting an issue."
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	DTSA_EXCEPTION_RESPONSE

inherit

	DTSA_RESPONSE
		undefine
			out
		end

	EXCEPTION
		rename
			message as obsolete_message,
			description as message
		end

create
	make

feature {NONE} -- Initialization

	make (a_message: like message)
			-- Create a exception response with `a_message' as `message'.
		do
			set_description (a_message)
		end

feature -- Processing

	acept (a_visitor: DTSA_RESPONSE_VISITOR)
			-- <Precursor>
		do
			a_visitor.visit_exception (Current)
		end

end
