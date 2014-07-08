note
	description: "Value attached with a label."
	author: "Victorien Elvinger"
	date: "$Date$"
	revision: "$Revision$"

class
	LABELED_VALUE

create
	make

feature {NONE} -- Implementation

	make (a_label: like label; a_value: like value)
		require
			a_label_not_empty: not a_label.is_empty
		do
			label := a_label
			value := a_value
		ensure
			label_set: label = a_label
			value_set: value = a_value
		end

feature -- Access

	label: READABLE_STRING_GENERAL
			-- Label attached to `value'.

	value: READABLE_STRING_GENERAL
			-- Main content.

end
