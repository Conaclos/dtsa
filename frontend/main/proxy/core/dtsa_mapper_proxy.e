note
	description: ""
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	DTSA_MAPPER_PROXY

inherit
	NETWORK_CONSTANT
		redefine
			default_create
		end

create
	default_create

feature {NONE} -- Initialization

	default_create
			-- Create a proxy for a local mapper available on `Default_port'.
		do
			mapper_ip := Loopback_ipv4
			mapper_port := Default_port
			create {NETWORK_STREAM_SOCKET} mapper_socket.make_client_by_port (mapper_port, mapper_ip)
		ensure then
			mapper_ip_set: mapper_ip ~ Loopback_ipv4
			mapper_port_set: mapper_port = Default_port
		end

feature -- Constant

	Default_port: NATURAL_16 = 2014
			-- Default server port of the mapper.

feature -- Access

	mapper_ip: IMMUTABLE_STRING_8
			-- IP of the Mapper server.

	mapper_port: like Default_port
			-- Server port of the mapper.

	mapper_socket: SOCKET

feature -- Communication

	launch
			-- Launch session with the mapper.
		do
			mapper_socket.connect
			process (mapper_socket)
			mapper_socket.cleanup
		rescue
			mapper_socket.cleanup
		end

feature {NONE} -- Implementation

	process (a_socket: like mapper_socket)
		do
			a_socket.put_string ("TEST");
		end

invariant
	valid_mapper_port: 1024 <= mapper_port
end
