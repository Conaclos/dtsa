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

create
	default_create

feature -- Processing

	acept (a_visitor: DTSA_RESPONSE_VISITOR)
			-- <Precursor>
		do
			a_visitor.visit_exception (Current)
		end

end
