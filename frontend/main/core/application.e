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
			l_parser: DTSA_COMMAND_LINE_PARSER
			l_arguments: like argument_array

			l_coordinator: DTSA_COORDINATOR
		do
			l_arguments := argument_array
			l_arguments := l_arguments.subarray (l_arguments.lower + 1, l_arguments.upper) -- Forget exceutable name
			create l_parser.make (l_arguments)

			if attached l_parser.request as l_request then
				 create l_coordinator.make
				 l_request.acept (l_coordinator)
			end
--			(create {DTSA_MAPPER_TEST}).launch
		end

end
