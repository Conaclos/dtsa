note
	description: "Frontend application root class."
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	APPLICATION

inherit
	ARGUMENTS
		redefine
			default_create
		end

create
	default_create

feature {NONE} -- Initialization

	default_create
			-- Socket test
		local
			mapper: DTSA_MAPPER_PROXY
		do
			create mapper
			mapper.launch
		end

end
