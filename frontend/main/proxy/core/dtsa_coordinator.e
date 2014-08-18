note
	description: "Summary description for {DTSA_COORDINATOR}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	DTSA_COORDINATOR

inherit

	DTSA_REQUEST_VISITOR

inherit {NONE}

	NETWORK_CONSTANT

create
	make

feature {NONE} -- Initialization

	make
		do
			create local_mapper.make (Loopback_ipv4, Default_port)
		end

feature -- Configuration -- (Should be externalized)

	Default_port: NATURAL_16 = 2015
			-- Default server port of the mapper.

	Destination: STRING_8 = "D://work/"

feature -- Status

	is_reactive: BOOLEAN = True
			-- <Precursor>

feature -- Visit

	visit_store (a_visited: DTSA_STORE_REQUEST)
			-- Visit `a_visited'.
		do
			local_mapper.launch
			a_visited.acept (local_mapper)
			local_mapper.finish
		end

	visit_project_compilation (a_visited: DTSA_PROJECT_COMPILATION_REQUEST)
			-- Visit `a_visited'.
		do
		end

	visit_project_testing (a_visited: DTSA_PROJECT_TESTING_REQUEST)
			-- Visit `a_visited'.
		local
			l_clusters:detachable  ARRAYED_LIST [ARRAYED_LIST [READABLE_STRING_8]]
		do
			if attached a_visited.classes as l_classes then
				create l_clusters.make (1)
				l_clusters.extend (l_classes)
			end
			visit_distributed_project_testing (create {DTSA_DISTRIBUTED_PROJECT_TESTING_REQUEST}.make_with_timeout (a_visited.uri, a_visited.project, a_visited.configuration, a_visited.target, l_clusters, a_visited.timeout))
		end

	visit_distributed_project_testing (a_visited: DTSA_DISTRIBUTED_PROJECT_TESTING_REQUEST)
			-- Visit `a_visited'.
		local
			l_remote_storing: detachable STRING_8
			l_remote_request: DTSA_DISTRIBUTED_PROJECT_TESTING_REQUEST
			l_store_request: DTSA_STORE_REQUEST
			l_retrieve_request: DTSA_RETRIEVE_REQUEST
			l_dir: DIRECTORY
		do
			local_mapper.launch

			create l_dir.make (a_visited.uri)
			if l_dir.exists then
				create l_store_request.make (a_visited.uri)
				l_store_request.acept (local_mapper)
				if attached l_store_request.response as l_store_response then
					l_remote_storing := l_store_response.uri
				elseif attached l_store_request.exception as l_exception then
					a_visited.set_exception (l_exception)
				end
			else
				l_remote_storing := a_visited.uri
			end

			if attached l_remote_storing as l_uri then
				create l_remote_request.make_with_timeout (l_uri, a_visited.project, a_visited.configuration, a_visited.target, a_visited.clusters, a_visited.timeout)

				if attached remote_mapper as l_remote_mapper then
					l_remote_mapper.launch
					l_remote_request.acept (l_remote_mapper)
					l_remote_mapper.finish
				else
					l_remote_request.acept (local_mapper)
				end

				if attached l_remote_request.response as l_testing_response then
					across l_testing_response.uris as ic loop
						create l_retrieve_request.make (Destination, ic.item)
						l_retrieve_request.acept (local_mapper)
					end
					a_visited.set_response (create {DTSA_DISTRIBUTED_PROJECT_TESTING_RESPONSE}.make_singleton (Destination))
				elseif attached l_remote_request.exception as l_exception then
					a_visited.set_exception (l_exception)
				end
			end

			local_mapper.finish
		end

	visit_echo (a_visited: DTSA_ECHO_REQUEST)
			-- Visit `a_visited'.
		do
			local_mapper.launch
			a_visited.acept (local_mapper)
			local_mapper.finish
		end

	visit_retrieve (a_visited: DTSA_RETRIEVE_REQUEST)
			-- Visit `a_visited'.
		do
			local_mapper.launch
			a_visited.acept (local_mapper)
			local_mapper.finish
		end

feature {NONE} -- Implementation

	local_mapper: DTSA_MAPPER_PROXY
			-- Local mapper interface.

	remote_mapper: detachable DTSA_MAPPER_PROXY
			-- Remote mapper.
			-- Use for testing and compilation only if attached.

end
