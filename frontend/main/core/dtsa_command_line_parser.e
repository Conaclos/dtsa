note
	description: "Summary description for {DTSA_COMMAND_LINE_l_parser}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	DTSA_COMMAND_LINE_PARSER

create
	make

feature {NONE} -- Initialization

	make (a_arguments: ARRAY [STRING_8])
		local
			l_parser: AP_PARSER

			l_echo_option: AP_INTEGER_OPTION
			l_store_option: AP_STRING_OPTION
			l_retrieve_option: AP_STRING_OPTION

			l_tetsing_option: AP_FLAG
			l_project_uri_option: AP_STRING_OPTION
			l_project_directory_option: AP_STRING_OPTION
			l_project_configuration_option: AP_STRING_OPTION
			l_project_target_option: AP_STRING_OPTION
			l_project_timeout_option: AP_INTEGER_OPTION

			l_clusters: ARRAYED_LIST [ARRAYED_LIST [READABLE_STRING_8]]
			l_integer_parameter: like {AP_INTEGER_OPTION}.parameter
			l_cursor: DS_BILINEAR_CURSOR [STRING]
			l_directory: DIRECTORY
		do
			create random.make
			create l_parser.make_empty

			create l_echo_option.make_with_long_form ("echo")
			l_echo_option.set_description ("echo message to check connectivity")
			l_echo_option.set_parameter_description ("Hop")
			l_parser.options.force_last (l_echo_option)

			create l_store_option.make_with_long_form ("store")
			l_parser.options.force_last (l_store_option)

			create l_retrieve_option.make_with_long_form ("retrieve")
			l_parser.options.force_last (l_retrieve_option)

			create l_tetsing_option.make_with_long_form ("testing")
			l_parser.options.force_last (l_tetsing_option)

			create l_project_uri_option.make_with_long_form ("uri")
			l_parser.options.force_last (l_project_uri_option)

			create l_project_directory_option.make_with_long_form ("project")
			l_parser.options.force_last (l_project_directory_option)

			create l_project_configuration_option.make_with_long_form ("config")
			l_parser.options.force_last (l_project_configuration_option)

			create l_project_target_option.make_with_long_form ("target")
			l_parser.options.force_last (l_project_target_option)

			create l_project_timeout_option.make ('t', "timeout")
			l_parser.options.force_last (l_project_timeout_option)


			l_parser.parse_array (a_arguments)


			if l_echo_option.was_found then
				l_integer_parameter := l_echo_option.parameter
				if 0 <= l_integer_parameter and l_integer_parameter <= 255 then
					create {DTSA_ECHO_REQUEST} request.make_with_hop ((random.item \\ {INTEGER_8}.max_value).as_integer_8, l_integer_parameter.as_natural_8)
				end
			elseif l_store_option.was_found then
				if attached l_store_option.parameter as l_path then
					create l_directory.make (l_path)
					if l_directory.exists then
						create {DTSA_STORE_REQUEST} request.make (l_path)
					end
				end
			elseif l_tetsing_option.was_found then
				if
					l_project_uri_option.was_found and then attached l_project_uri_option.parameter as l_uri and
					l_project_directory_option.was_found and then attached l_project_directory_option.parameter as l_project and
					l_project_configuration_option.was_found and then attached l_project_configuration_option.parameter as l_config and
					l_project_target_option.was_found and then attached l_project_target_option.parameter as l_target
				then
					create l_clusters.make (l_parser.parameters.count)
					l_parser.parameters.do_all_with_index (agent (ia_classes: detachable STRING_8; ia_index: INTEGER_32; ia_clusters: ARRAYED_LIST [ARRAYED_LIST [READABLE_STRING_8]])
						do
							if attached ia_classes as l_classes then
								ia_clusters.extend (classes_from (l_classes))
							end
						end (?, ?, l_clusters))

					if l_project_timeout_option.was_found then
						l_integer_parameter := l_project_timeout_option.parameter
						if 0 <= l_integer_parameter and l_integer_parameter <= 255 then
							create {DTSA_DISTRIBUTED_PROJECT_TESTING_REQUEST} request.make_with_timeout (l_uri, l_project, l_config, l_target, l_clusters, l_integer_parameter)
						end
					else
						create {DTSA_DISTRIBUTED_PROJECT_TESTING_REQUEST} request.make (l_uri, l_project, l_config, l_target, l_clusters)
					end
				end
			end
		end

feature -- Access

	request: detachable DTSA_REQUEST
			-- User request.

feature {NONE} -- Implementation

	random: RANDOM
			-- Random.

	classes_from (a_cluster: STRING_8): ARRAYED_LIST [READABLE_STRING_8]
		local

		do
			if attached {ARRAYED_LIST [STRING_8]} a_cluster.split (',') as l_list then
				Result := l_list
			else
				check is_an_arrayed_list: False then end
			end
		end

end
