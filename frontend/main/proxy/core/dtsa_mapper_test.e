note
	description: ""
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	DTSA_MAPPER_TEST

inherit

	ANY
		redefine
			default_create
		end

	SHARED_EJSON
		redefine
			default_create
		end

inherit {NONE}

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
			mapper_ip := loopback_ipv4
			mapper_ip := "54.77.141.7"
			mapper_port := Default_port
			create {NETWORK_STREAM_SOCKET} mapper_socket.make_client_by_port (mapper_port, mapper_ip)
		ensure then
			-- mapper_ip_set: mapper_ip ~ Loopback_ipv4
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
			process_5 (a_socket)
		end

	process_4 (a_socket: like mapper_socket)
		local
			l_request: DTSA_ECHO_REQUEST
			labeling: JSON_LABELING [DTSA_REQUEST]
		do
			create l_request.make_with_hop (5, 2)

			json.add_converter (create {JSON_LABELED_VALUE_CONVERTER})
			json.add_converter (create {JSON_DTSA_ECHO_REQUEST_CONVERTER})

			create labeling
			labeling.put ({DTSA_ECHO_REQUEST}, "echo")

			if attached labeling.to_json (l_request) as l_labeled then
				a_socket.put_string (l_labeled.representation)
				a_socket.new_line
			end

			a_socket.read_line
			print (a_socket.last_string)

			a_socket.cleanup
		end

	process_6 (a_socket: like mapper_socket)
		local
			l_request: DTSA_PROJECT_TESTING_REQUEST
			labeling: JSON_LABELING [DTSA_REQUEST]
			s: STRING
			classes: ARRAYED_LIST [READABLE_STRING_8]
		do
			s := "https://github.com/Conaclos/eiffel-sudoku.git"

			create classes.make (1)
			classes.extend ("ARRAYED_LIST")
			create l_request.make_with_timeout (s, "eiffel-sudoku", "eiffel-sudoku/eiffel_sudoku.ecf", "eiffel_sudoku", classes, 2)

			json.add_converter (create {JSON_LABELED_VALUE_CONVERTER})
			json.add_converter (create {JSON_DTSA_PROJECT_TESTING_REQUEST_CONVERTER})

			create labeling
			labeling.put ({DTSA_PROJECT_TESTING_REQUEST}, "testing")

			if attached labeling.to_json (l_request) as l_labeled then
				print (l_labeled.representation)
				a_socket.put_string (l_labeled.representation)
				a_socket.new_line
			end

			a_socket.read_line
			print (a_socket.last_string)

			a_socket.cleanup
		end

	process_5 (a_socket: like mapper_socket)
		local
			l_request: DTSA_PROJECT_TESTING_REQUEST
			labeling: JSON_LABELING [DTSA_REQUEST]
			s: STRING
			classes: ARRAYED_LIST [READABLE_STRING_8]
		do
			-- s := "https://dtsa.s3.amazonaws.com/toy_2.zip?AWSAccessKeyId=AKIAIGBWGJHHM7O2BM6Q&Expires=1406041093&Signature=hgZiJMu6tE8Lm%%2ByEmbN%%2BAJecMEM%%3D"
			s := "s3://eu-west-1/dtsa#1408056751230"

			create classes.make (1)
			classes.extend ("ARRAYED_LIST")
			create l_request.make_with_timeout (s, "toy_2", "toy_2/toy_2.ecf", "toy_2", classes, 2)

			json.add_converter (create {JSON_LABELED_VALUE_CONVERTER})
			json.add_converter (create {JSON_DTSA_PROJECT_TESTING_REQUEST_CONVERTER})

			create labeling
			labeling.put ({DTSA_PROJECT_TESTING_REQUEST}, "testing")

			if attached labeling.to_json (l_request) as l_labeled then
				print (l_labeled.representation)
				a_socket.put_string (l_labeled.representation)
				a_socket.new_line
			end

			a_socket.read_line
			print (a_socket.last_string)

			a_socket.cleanup
		end

	process_3 (a_socket: like mapper_socket)
		local
			l_request: DTSA_PROJECT_COMPILATION_REQUEST
			labeling: JSON_LABELING [DTSA_REQUEST]
			s: STRING
		do
			s := "file:///D:/work/"

			create l_request.make (s, "toy_2", "toy_2/toy_2.ecf", "toy_2")

			json.add_converter (create {JSON_LABELED_VALUE_CONVERTER})
			json.add_converter (create {JSON_DTSA_PROJECT_COMPILATION_REQUEST_CONVERTER})

			create labeling
			labeling.put ({DTSA_PROJECT_COMPILATION_REQUEST}, "compilation")

			if attached labeling.to_json (l_request) as l_labeled then
				a_socket.put_string (l_labeled.representation)
				a_socket.new_line
			end

			-- a_socket.put_string ("{%"label%":%"compilation%",%"value%":%"{\%"uri\%":\%"" + s + "\%",\%"project\%":\%"eioc/library/\%",\%"target\%":\%"eioc%",\%"configuration\%":\%"eioc/library/eioc.ecf\%"}%"}")
			-- a_socket.new_line

			a_socket.read_line
			print (a_socket.last_string)

			a_socket.cleanup


			--	a_socket.put_string ("%"C:\Program Files\Eiffel Software\EiffelStudio 14.05 GPL\studio\spec\win64\bin\estudio.exe%"")
		end

	process_2 (a_socket: like mapper_socket)
		local
			l_request: DTSA_STORE_REQUEST
			labeling: JSON_LABELING [DTSA_REQUEST]
			s: STRING
		do
			s := "D:/at/toy_2"
			create l_request.make (s)

			json.add_converter (create {JSON_DTSA_STORE_REQUEST_CONVERTER})
			json.add_converter (create {JSON_LABELED_VALUE_CONVERTER})

			create labeling
			labeling.put ({DTSA_STORE_REQUEST}, "store")

			if attached labeling.to_json (l_request) as l_labeled then
				a_socket.put_string (l_labeled.representation)
				a_socket.new_line
			end

			-- a_socket.put_string ("{%"label%":%"store%",%"value%":%"{\%"path\%":\%"" + s + "\%"}%"}")
			-- a_socket.new_line

			a_socket.read_line
			print (a_socket.last_string)

			a_socket.cleanup
		end

	process_1 (a_socket: like mapper_socket)
		do
			a_socket.put_string ("{%"label%":%"client%",%"value%":%"{\%"att\%":\%"mypath\%"}%"}")
			a_socket.new_line

			a_socket.read_line
			print (a_socket.last_string)

			a_socket.put_string ("{%"label%":%"client%",%"value%":%"{\%"att\%":\%"hello world\%"}%"}")
			a_socket.new_line

			a_socket.read_line
			print (a_socket.last_string)


			a_socket.cleanup
		end

invariant
	valid_mapper_port: 1024 <= mapper_port
end
