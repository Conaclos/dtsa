note
	description: "[
			Root ancestor of all server response.
			Response are simple objects for reception.
		]"
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

deferred class
	DTSA_RESPONSE

feature -- Processing

	acept (a_visitor: DTSA_RESPONSE_VISITOR)
			-- Visit current response with `a_visitor'.
		note
			design_pattern: "visitor"
		deferred end

end
