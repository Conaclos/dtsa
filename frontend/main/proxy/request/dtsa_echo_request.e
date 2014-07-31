note
	description: "Request for an echo message. Allow to test layout connection."
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	DTSA_ECHO_REQUEST

inherit
	DTSA_REQUEST
		export
			{ANY} set_response, set_exception
		redefine
			response
		end

create
	make, make_with_hop

feature {NONE} -- creation

	make (a_id: like id)
			-- Create with `a_id' as `id' and `hop' as `Minimal_hop'.
		do
			id := a_id
			hop := Minimal_hop
		ensure
			id_set: id = a_id
			hop_set: hop = Minimal_hop
		end

	make_with_hop (a_id: like id; a_hop: like hop)
			-- Create with `a_id' as `id' and `hop' as `a_hop'.
		do
			id := a_id
			if a_hop < Minimal_hop then
				hop := Minimal_hop
			else
				hop := a_hop
			end
		ensure
			id_set: id = a_id
			hop_set: (a_hop >= Minimal_hop implies hop = a_hop) and (a_hop < Minimal_hop implies hop = Minimal_hop)
		end

feature -- Access

	response: detachable DTSA_ECHO_RESPONSE
			-- <Precursor>

	id: INTEGER_8
			-- Echo ID.

	Minimal_hop: like hop = 1
			-- Minimal value for `hop'.

	hop: NATURAL_8
			-- Deph of the echo.

	partial_twin: like Current
			-- <Precursor>
		do
			create Result.make_with_hop (id, hop)
		end

feature -- Status report

	is_partial_equal (a_other: DTSA_REQUEST): BOOLEAN
			-- <Precursor>
		do
			Result := attached {like Current} a_other as l_other and then
				(l_other.id = id and l_other.hop = hop)
		end

feature -- Processing

	acept (a_visitor: DTSA_REQUEST_VISITOR)
			-- <Precursor>
		do
			a_visitor.visit_echo (Current)
		end

invariant
	minimal_hop_value: hop >= Minimal_hop

end
