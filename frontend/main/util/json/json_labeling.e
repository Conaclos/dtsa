note
	description: "JSON conversion with post labeling."
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	JSON_LABELING [EXPECTED -> ANY]

inherit
	
	SHARED_EJSON
		undefine
			default_create
		end

inherit {NONE}

	REFLECTOR
		export
			{NONE} all
		undefine
			default_create
		end

create
	default_create

feature {NONE} -- Initialization

	default_create
			-- <Precursor>
		do
			create labeled_types.make_equal (10)
			create typed_labels.make_equal (10)
		end

feature -- Status report

	is_labeled (a_type: TYPE [EXPECTED]): BOOLEAN
			-- Is class of `a_type' attached with a label?
		do
			Result := typed_labels.has (class_name_of_type (a_type.type_id))
		end

	has_label (a_label: READABLE_STRING_GENERAL): BOOLEAN
			-- Has `a_label' attached with a type?
		do
			Result := labeled_types.has (a_label)
		end

feature -- Conversion

	from_json (j: attached like to_json): detachable EXPECTED
			-- <Precursor>
		do
			if
				attached {LABELED_VALUE} json.object (j, {LABELED_VALUE}) as l_labeled and then
				attached labeled_types.item (l_labeled.label) as l_type and then
				attached {EXPECTED} json.object_from_json (l_labeled.value, l_type) as l_result
			then
				Result := l_result
			end
		end

	to_json (o: attached like from_json): detachable JSON_OBJECT
			-- <Precursor>
		do
			if
				(attached typed_labels.item (o.generator) as l_label and
				attached json.value (o) as l_value) and then
				attached {JSON_OBJECT} json.value (create {LABELED_VALUE}.make (l_label.as_string_8, l_value.representation)) as l_result
			then
				Result := l_result
			end
		end

feature -- Change

	put (a_type: TYPE [EXPECTED]; a_label: READABLE_STRING_8)
			-- Attached class of `a_type' with `a_label'
		do
			labeled_types.put (a_type, a_label)
			typed_labels.put (a_label, class_name_of_type (a_type.type_id))
		end

feature {NONE} -- Implementation

	labeled_types: STRING_TABLE [TYPE [EXPECTED]]
			-- Mapping of labels to types.

	typed_labels: STRING_TABLE [READABLE_STRING_8]
			-- Mapping of types to labels.

end
