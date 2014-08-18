note
	description: ""
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	DTSA_MAPPER_PROXY

inherit

	DTSA_REQUEST_VISITOR

	SHARED_EJSON

create
	make

feature {NONE} -- Initialization

	make (a_mapper_ip: like mapper_ip; a_mapper_port: like mapper_port)
			-- Create a proxy for a local mapper available on `Default_port'.
		do
			mapper_ip := a_mapper_ip
			mapper_port := a_mapper_port
			create {NETWORK_STREAM_SOCKET} mapper_socket.make_client_by_port (mapper_port, mapper_ip)

			json.add_converter (create {JSON_LABELED_VALUE_CONVERTER})
			json.add_converter (create {JSON_DTSA_ECHO_REQUEST_CONVERTER})
			json.add_converter (create {JSON_DTSA_ECHO_RESPONSE_CONVERTER})
			json.add_converter (create {JSON_DTSA_EXCEPTION_RESPONSE_CONVERTER})
			json.add_converter (create {JSON_DTSA_DISTRIBUTED_PROJECT_TESTING_REQUEST_CONVERTER})
			json.add_converter (create {JSON_DTSA_DISTRIBUTED_PROJECT_TESTING_RESPONSE_CONVERTER})
			json.add_converter (create {JSON_DTSA_PROJECT_COMPILATION_REQUEST_CONVERTER})
			json.add_converter (create {JSON_DTSA_PROJECT_COMPILATION_RESPONSE_CONVERTER})
			json.add_converter (create {JSON_DTSA_PROJECT_TESTING_REQUEST_CONVERTER})
			json.add_converter (create {JSON_DTSA_PROJECT_TESTING_RESPONSE_CONVERTER})
			json.add_converter (create {JSON_DTSA_STORE_REQUEST_CONVERTER})
			json.add_converter (create {JSON_DTSA_STORE_RESPONSE_CONVERTER})
			json.add_converter (create {JSON_DTSA_RETRIEVE_REQUEST_CONVERTER})
			json.add_converter (create {JSON_DTSA_RETRIEVE_RESPONSE_CONVERTER})

			create request_labeling
			request_labeling.put ({DTSA_ECHO_REQUEST}, "echo")
			request_labeling.put ({DTSA_DISTRIBUTED_PROJECT_TESTING_REQUEST}, "distributed_testing")
			request_labeling.put ({DTSA_RETRIEVE_REQUEST}, "retrieve")
			request_labeling.put ({DTSA_STORE_REQUEST}, "store")

			create response_labeling
			response_labeling.put ({DTSA_ECHO_RESPONSE}, "echo")
			response_labeling.put ({DTSA_EXCEPTION_RESPONSE}, "exception")
			response_labeling.put ({DTSA_DISTRIBUTED_PROJECT_TESTING_RESPONSE}, "distributed_testing")
			response_labeling.put ({DTSA_RETRIEVE_RESPONSE}, "retrieve")
			response_labeling.put ({DTSA_STORE_RESPONSE}, "store")
		ensure then
			mapper_ip_set: mapper_ip ~ a_mapper_ip
			mapper_port_set: mapper_port = a_mapper_port
		end

feature -- Status

	is_reactive: BOOLEAN = True

feature -- Communication

	launch
			-- Launch session with the mapper.
		do
			mapper_socket.connect
		rescue
			mapper_socket.cleanup
		end

	finish
			-- Finish session with the mapper.
		do
			mapper_socket.cleanup
		rescue
			mapper_socket.cleanup
		end

feature -- Basic opertaion

	visit_echo (a_visited: DTSA_ECHO_REQUEST)
			-- <Precursor>
		do
			if attached response_for (a_visited) as l_instance then
				if attached {DTSA_ECHO_RESPONSE} l_instance as l_normal then
					a_visited.set_response (l_normal)
				elseif attached {DTSA_EXCEPTION_RESPONSE} l_instance as l_exception then
					a_visited.set_exception (l_exception)
				end
			end
		end

	visit_store (a_visited: DTSA_STORE_REQUEST)
			-- <Precursor>
		do
			if attached response_for (a_visited) as l_instance then
				if attached {DTSA_STORE_RESPONSE} l_instance as l_normal then
					a_visited.set_response (l_normal)
				elseif attached {DTSA_EXCEPTION_RESPONSE} l_instance as l_exception then
					a_visited.set_exception (l_exception)
				end
			end
		end

	visit_retrieve (a_visited: DTSA_RETRIEVE_REQUEST)
			-- <Precursor>
		do
			if attached response_for (a_visited) as l_instance then
				if attached {DTSA_RETRIEVE_RESPONSE} l_instance as l_normal then
					a_visited.set_response (l_normal)
				elseif attached {DTSA_EXCEPTION_RESPONSE} l_instance as l_exception then
					a_visited.set_exception (l_exception)
				end
			end
		end

	visit_project_compilation (a_visited: DTSA_PROJECT_COMPILATION_REQUEST)
			-- <Precursor>
		do
			if attached response_for (a_visited) as l_instance then
				if attached {DTSA_PROJECT_COMPILATION_RESPONSE} l_instance as l_normal then
					a_visited.set_response (l_normal)
				elseif attached {DTSA_EXCEPTION_RESPONSE} l_instance as l_exception then
					a_visited.set_exception (l_exception)
				end
			end
		end

	visit_project_testing (a_visited: DTSA_PROJECT_TESTING_REQUEST)
			-- <Precursor>
		local
			l_request: DTSA_DISTRIBUTED_PROJECT_TESTING_REQUEST
			l_clusters: detachable ARRAYED_LIST [ARRAYED_LIST [READABLE_STRING_8]]
		do
			if attached a_visited.classes as l_classes then
				create l_clusters.make (1)
				l_clusters.extend (l_classes)
			end
			create l_request.make_with_timeout (a_visited.uri, a_visited.project, a_visited.configuration, a_visited.target, l_clusters, a_visited.timeout)
		end

	visit_distributed_project_testing (a_visited: DTSA_DISTRIBUTED_PROJECT_TESTING_REQUEST)
			-- <Precursor>
		do
			if attached response_for (a_visited) as l_instance then
				if attached {DTSA_DISTRIBUTED_PROJECT_TESTING_RESPONSE} l_instance as l_normal then
					a_visited.set_response (l_normal)
				elseif attached {DTSA_EXCEPTION_RESPONSE} l_instance as l_exception then
					a_visited.set_exception (l_exception)
				end
			end
		end

feature {NONE} -- Implementation

	process (a_socket: like mapper_socket)
		do

		end

	request_labeling: JSON_LABELING [DTSA_REQUEST]
			-- Request labeling.

	response_labeling: JSON_LABELING [DTSA_RESPONSE]
			-- Response labeling.

	mapper_ip: IMMUTABLE_STRING_8
			-- IP of the Mapper server.

	mapper_port: NATURAL_16
			-- Server port of the mapper.

	mapper_socket: SOCKET
			-- Socket of Mapper.

	response_for (a_request: DTSA_REQUEST): detachable ANY
			-- Response of `a_request'.
		do
			if attached request_labeling.to_json (a_request) as l_labeled then
				mapper_socket.put_string (l_labeled.representation)
				mapper_socket.new_line

				mapper_socket.read_line
				Result := response_labeling.from_json_value (mapper_socket.last_string)
			end
		end

invariant
	valid_mapper_port: 1024 <= mapper_port
end
