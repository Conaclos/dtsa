note
	description: "Summary description for {TEST_JSON_LABELING}."
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	TEST_JSON_LABELING

inherit

	EQA_TEST_SET

	SHARED_EJSON
		undefine
			default_create
		end

feature -- Test

	test_labeled_value
		local
			jrep_witness: STRING_8
			o_witness: LABELED_VALUE
		do
			json.add_converter (create {JSON_LABELED_VALUE_CONVERTER})

			create o_witness.make ("flag", "color")
			jrep_witness := "{%"label%":%"flag%",%"value%":%"color%"}"

			if attached {JSON_OBJECT} json.value (o_witness) as l_jvalue then
				assert ("Json representation expected.", jrep_witness ~ l_jvalue.representation)

				if attached {LABELED_VALUE} json.object (l_jvalue, {LABELED_VALUE}) as l_object then
					assert ("Object expected", o_witness.label ~ l_object.label and o_witness.value ~ l_object.value)
				else
					assert ("Json representation is convertible to JSON_LABELED.", False)
				end
			else
				assert ("JSON_LABELED objecct is convertible to json.", False)
			end
		end

	test_sample_value
		local
			jrep_witness: STRING_8
			o_witness: SAMPLE
		do
			json.add_converter (create {JSON_SAMPLE_CONVERTER})

			create o_witness.make ("flag", 6)
			jrep_witness := "{%"str%":%"flag%",%"int%":6}"

			if attached {JSON_OBJECT} json.value (o_witness) as l_jvalue then
				assert ("Json representation expected.", jrep_witness ~ l_jvalue.representation)

				if attached {SAMPLE} json.object (l_jvalue, {SAMPLE}) as l_object then
					assert ("Object expected ", o_witness.str.same_string (l_object.str) and o_witness.int = l_object.int)
				else
					assert ("Json representation is convertible to SAMPLE.", False)
				end
			else
				assert ("SAMPLE objecct is convertible to json.", False)
			end
		end

	test_is_labeled
		note
			testing: "covers/{SAMPLE}.is_labeled"
		local
			l_manager: JSON_LABELING [SAMPLE]
		do
			create l_manager

			assert ("SAMPLE is not labeled", not l_manager.is_labeled ({SAMPLE}))

			l_manager.put ({SAMPLE}, "sample")

			assert ("SAMPLE is labeled", l_manager.is_labeled ({SAMPLE}))
		end

	test_has_label
		note
			testing: "covers/{SAMPLE}.has_label"
		local
			l_manager: JSON_LABELING [SAMPLE]
		do
			create l_manager

			assert ("has not label 'sample'", not l_manager.has_label ("sample"))

			l_manager.put ({SAMPLE}, "sample")

			assert ("has label 'sample'", l_manager.has_label ("sample"))
		end

	test_sample_labeling
		local
			l_manager: JSON_LABELING [SAMPLE]
			jrep_witness: STRING_8
			o_witness: SAMPLE
			l_label, l_str: STRING
			l_int: INTEGER_8
		do
			json.add_converter (create {JSON_LABELED_VALUE_CONVERTER})
			json.add_converter (create {JSON_SAMPLE_CONVERTER})

			l_label := "sample"
			l_str := "flag"
			l_int := 6

			create l_manager
			l_manager.put ({SAMPLE}, l_label)

			create o_witness.make (l_str, l_int)
			jrep_witness := "{%"label%":%"" + l_label + "%",%"value%":%"{\%"str\%":\%"" + l_str + "\%",\%"int\%":" + l_int.out + "}%"}"

			if attached {JSON_OBJECT} l_manager.to_json (o_witness) as l_jvalue then
				assert ("Json representation expected.", jrep_witness ~ l_jvalue.representation)

				if attached {SAMPLE} l_manager.from_json (l_jvalue) as l_object then
					assert ("Object expected ", o_witness.str.same_string (l_object.str) and o_witness.int = l_object.int)
				else
					assert ("Labeled Json representation is convertible to SAMPLE.", False)
				end
			else
				assert ("SAMPLE objecct is convertible to labeled json.", False)
			end
		end

end
