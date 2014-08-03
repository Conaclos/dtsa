note
	description: "Summary description for {DTSA_PROJECT_TESTING_REQUEST}."
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	DTSA_DISTRIBUTED_PROJECT_TESTING_REQUEST

inherit

	DTSA_REQUEST
		export
			{ANY} set_response, set_exception
		redefine
			response
		end

create
	make, make_with_timeout

feature {NONE} -- cReation

	make_with_timeout (a_uri, a_project, a_configuration, a_target: READABLE_STRING_8; a_clusters: like clusters; a_timeout: like timeout)
		do
			uri := a_uri
			project := a_project
			configuration := a_configuration
			target := a_target
			clusters := a_clusters
			timeout := a_timeout
		ensure
			uri_set: uri = a_uri
			project_set: project = a_project
			configuration_set: configuration = a_configuration
			clusters_set: clusters = a_clusters
			target_set: target = a_target
			timeout_set: timeout = a_timeout
		end

	make (a_uri, a_configuration, a_project, a_target: READABLE_STRING_8; a_clusters: like clusters)
			-- Create a project testing request with a default timeout.
		do
			make_with_timeout (a_uri, a_project, a_configuration, a_target, clusters, Default_timeout)
		ensure
			uri_set: uri = a_uri
			project_set: project = a_project
			configuration_set: configuration = a_configuration
			timeout_set_default: timeout = Default_timeout
			clusters_set: clusters = a_clusters
			target_set: target = a_target
		end

feature -- Access

	response: detachable DTSA_DISTRIBUTED_PROJECT_TESTING_RESPONSE
			-- <Precursor>

	uri: READABLE_STRING_8
			-- Project URI.

	project: READABLE_STRING_8
			-- Project path from `uri'.

	configuration: READABLE_STRING_8
			-- Eiffel Configuration File path from `uri'.

	target: READABLE_STRING_8
			-- Project target available in `configuration'.

	Default_timeout: like timeout = 20
			-- Default timeout.

	timeout: INTEGER_32
			-- Time for testing in minutes.

	clusters: detachable ARRAYED_LIST [ARRAYED_LIST [STRING]]
			-- Class clusters.
			-- Void means no clusters.

	partial_twin: like Current
			-- <Precursor>
		do
			create Result.make_with_timeout (uri, project, configuration, target, clusters, timeout)
		end

feature -- Status report

	is_partial_equal (a_other: DTSA_REQUEST): BOOLEAN
			-- <Precursor>
		do
			Result := attached {like Current} a_other as l_other and then
				(uri ~ l_other.uri and project ~ l_other.project and configuration ~ l_other.configuration and target ~ l_other.target and timeout = l_other.timeout)
		end

feature -- Processing

	acept (a_visitor: DTSA_REQUEST_VISITOR)
			-- <Precursor>
		do
			a_visitor.visit_distributed_project_testing (Current)
		end

end
