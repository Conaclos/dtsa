note
	description: "Echo message for an echo request."
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	DTSA_ECHO_RESPONSE

inherit
	DTSA_RESPONSE

create
	make

feature {NONE} -- Creation

	make (a_id: like id)
			-- Create with `a_id' as `id'.
		do
			id := a_id
		ensure
			id_set: id = a_id
		end

feature -- Acceess

	id: NATURAL_8
			-- Echo id.

feature -- Processing

	acept (a_visitor: DTSA_RESPONSE_VISITOR)
			-- <Precursor>
		do
			a_visitor.visit_echo (Current)
		end

end
