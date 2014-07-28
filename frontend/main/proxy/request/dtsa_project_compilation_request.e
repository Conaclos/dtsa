note
	description: "Summary description for {DTSA_AUTOTEST_REQUEST}."
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	DTSA_PROJECT_COMPILATION_REQUEST

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

	make (a_uri, a_project, a_configuration, a_target: READABLE_STRING_8)
		do
			uri := a_uri
			project := a_project
			configuration := a_configuration
			target := a_target
		ensure
			uri_set: uri = a_uri
			project_set: project = a_project
			configuration_set: configuration = a_configuration
			target_set: target = a_target
		end

feature -- Access

	response: detachable DTSA_PROJECT_COMPILATION_RESPONSE
			-- <Precursor>

	uri: READABLE_STRING_8
			-- Project URI.

	project: READABLE_STRING_8
			-- Project path from `uri'.

	configuration: READABLE_STRING_8
			-- Eiffel Configuration File path from `uri'.

	target: READABLE_STRING_8
			-- Project target available in `configuration'.

	partial_twin: like Current
			-- <Precursor>
		do
			create Result.make (uri, project, configuration, target)
		end

feature -- Status report

	is_partial_equal (a_other: DTSA_REQUEST): BOOLEAN
			-- <Precursor>
		do
			Result := attached {like Current} a_other as l_other and then
				(uri ~ l_other.uri and configuration ~ l_other.configuration and target ~ l_other.target)
		end

feature -- Processing

	acept (a_visitor: DTSA_REQUEST_VISITOR)
			-- <Precursor>
		do
			a_visitor.visit_project_compilation (Current)
		end

end
